package com.sap.cloud.s4hana.examples.addressmgr.blockchain;

import com.google.gson.*;
import com.sap.cloud.s4hana.examples.addressmgr.util.CloudPlatformService;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpEntityUtil;
import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.cloudplatform.security.BasicAuthHeaderEncoder;
import com.sap.cloud.sdk.cloudplatform.security.BasicCredentials;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;

import java.net.URI;
import java.util.Arrays;

public class BlockchainService {
    private static final Logger logger = CloudLoggerFactory.getLogger(BlockchainService.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String BASIC_PREFIX = "Basic ";

    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private static final String ACCESS_TOKEN = "access_token";

    private String bearerToken;

    private String serviceUrl;

    public BlockchainService(String bearerToken, String serviceUrl) {
        this.bearerToken = bearerToken;
        this.serviceUrl = serviceUrl;
    }

    public static BlockchainService createByGettingTokenViaCfServicesConfig() throws ShouldNotHappenException {
        return getBlockchainService("hyperledger-fabric");
    }

    public String invokeOrQuery(BlockchainInvocationType invocationType, String chaincodeId,
                                String function, String... args) throws Exception {
        logger.trace("{} {}:{}({})", invocationType, chaincodeId, function, Arrays.asList(args));

        final HttpPost chaincodeEnpointRequest = new HttpPost(
                new URI(getServiceUrl() + "/chaincodes/" + chaincodeId
                        + "/latest/" + invocationType.getPathParamValue()).normalize());

        chaincodeEnpointRequest.setHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + getBearerToken());
        chaincodeEnpointRequest.setHeader("Accept", "application/json;charset=UTF-8");
        chaincodeEnpointRequest.setHeader("Content-Type", "application/json");

        final JsonObject requestBody = new JsonObject();
        requestBody.addProperty("function", function);
        requestBody.add("arguments", new Gson().toJsonTree(args));
        requestBody.addProperty("async", false);

        final String json = new Gson().toJson(requestBody);
        logger.trace("Request: {}", json);
        chaincodeEnpointRequest.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        final HttpResponse response = HttpClientAccessor.getHttpClient().execute(chaincodeEnpointRequest);
        final StatusLine statusLine = response.getStatusLine();

        final String responsePayload = HttpEntityUtil.getResponseBody(response);
        logger.trace("Response status: {}, content: {}", statusLine, responsePayload);

        switch (statusLine.getStatusCode()) {
            case HttpStatus.SC_OK:
                return responsePayload;

            case HttpStatus.SC_UNAUTHORIZED:
            case HttpStatus.SC_FORBIDDEN:
                throw new AuthenticationException("Access token already expired or simply wrong: "
                        + "Service request denied with status: " + statusLine);
            default:
                throw new Exception("Failed to execute request to function " + function + ": "
                        + "Service request failed with status: " + statusLine);
        }
    }

    static BlockchainService getBlockchainService(final String serviceName)
            throws ShouldNotHappenException {
        final ScpCfCloudPlatform cloudPlatform = new CloudPlatformService().getCloudFoundryOrThrow();

        try {
            final JsonArray serviceInstances = cloudPlatform.getVcapServices().get(serviceName);
            if (serviceInstances == null || serviceInstances.size() < 1) {
                throw new ShouldNotHappenException("Service " + serviceName + " not bound. " +
                        "The fabric channel needs to be created, an instance for it needs to be created " +
                        "and the instance needs to be bound to this application");
            }
            final JsonElement serviceInfo = serviceInstances.get(0);
            checkFabricServicePropertiesPresent("Service info ", serviceInfo, "credentials");
            JsonElement serviceCredentials = serviceInfo.getAsJsonObject().get("credentials");

            checkFabricServicePropertiesPresent("Service credentials ", serviceCredentials, "oAuth", "serviceUrl");
            final JsonElement oAuthCredentials = serviceCredentials.getAsJsonObject().get("oAuth");
            final String serviceUrl = serviceCredentials.getAsJsonObject().get("serviceUrl").getAsString();

            checkFabricServicePropertiesPresent("OAuth credentials ", oAuthCredentials, "clientId", "clientSecret",
                    "url");
            final BasicCredentials clientCredentials = new BasicCredentials(
                    oAuthCredentials.getAsJsonObject().get("clientId").getAsString(),
                    oAuthCredentials.getAsJsonObject().get("clientSecret").getAsString()
            );
            final String authUrlPrefix = oAuthCredentials.getAsJsonObject().get("url").getAsString();
            final String authUrl;
            if (!authUrlPrefix.endsWith("token")) {
                authUrl = authUrlPrefix + "/oauth/token"; // ensure it ends with oAuth token endpoint
            } else {
                authUrl = authUrlPrefix;
            }

            final HttpPost tokenRequest = new HttpPost(new URI(authUrl).normalize());

            tokenRequest.setHeader(AUTHORIZATION_HEADER,BASIC_PREFIX + BasicAuthHeaderEncoder.encodeUserPasswordBase64(clientCredentials));

            tokenRequest.setEntity(new StringEntity("client_id="
                    + clientCredentials.getUsername()
                    + "&grant_type="
                    + CLIENT_CREDENTIALS, ContentType.APPLICATION_FORM_URLENCODED));

            final String responsePayload;
            final HttpResponse response = HttpClientAccessor.getHttpClient().execute(tokenRequest);
            final StatusLine statusLine = response.getStatusLine();
            final int statusCode = statusLine.getStatusCode();

            switch (statusCode) {
                case HttpStatus.SC_OK:
                    responsePayload = HttpEntityUtil.getResponsePayload(response);
                    break;
                case HttpStatus.SC_UNAUTHORIZED:
                case HttpStatus.SC_FORBIDDEN:
                    throw new AuthenticationException("Failed to get access token: "
                            + "Auth service denied token request with HTTP status "
                            + statusCode
                            + " ("
                            + statusLine.getReasonPhrase()
                            + ").");
                default:
                    throw new Exception("Failed to get access token: "
                            + "Auth service returned HTTP status "
                            + statusCode
                            + " ("
                            + statusLine.getReasonPhrase()
                            + ").");
            }

            final JsonElement tokenPayload;
            try {
                tokenPayload = new JsonParser().parse(responsePayload);
                if (
                        !(tokenPayload.isJsonObject() && tokenPayload.getAsJsonObject().has(ACCESS_TOKEN))
                        ) {
                    throw new Exception("Not containing json object or not containing " + ACCESS_TOKEN + " property");
                }

            } catch (Exception e) {
                logger.error("Error during authentication at blockchain, unexpected paylod", e);
                throw new ShouldNotHappenException("Blockchain auth service returned unexpected payload: " + e
                        .getMessage());
            }
            final String token = tokenPayload.getAsJsonObject().get(ACCESS_TOKEN).getAsString();

            return new BlockchainService(token, serviceUrl);

        } catch (ShouldNotHappenException e) {
            throw e;
        } catch (final Exception e) {
            throw new ShouldNotHappenException("Failed to setup Blockchain service: " + e.getMessage(), e);
        }
    }

    private static void checkFabricServicePropertiesPresent(String objectDescription, JsonElement jsonElement,
                                                            String... properties) {
        if (!jsonElement.isJsonObject()) {
            throw new ShouldNotHappenException(objectDescription + " should be a json object. Structure changed?");
        }
        final JsonObject jsonObject = jsonElement.getAsJsonObject();

        for (String property : properties) {
            if (!(jsonObject.has(property) && !jsonObject.get(property).isJsonNull())) {
                throw new ShouldNotHappenException(objectDescription
                        + " miss(es) " + property + " property - structure changed? Not the fabric service? " +
                        "Check cf env -application name- to see the current structure of VCAP_SERVICES");
            }
        }
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    @Override
    public String toString() {
        return "BlockchainService{" +
                "bearerTokenNotNull='" + (bearerToken != null) + '\'' +
                ", serviceUrl='" + serviceUrl + '\'' +
                '}';
    }

}
