package com.sap.cloud.s4hana.examples.addressmgr.blockchain;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpEntityUtil;
import com.sap.cloud.sdk.cloudplatform.connectivity.ScpCfService;
import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
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

    private ScpCfService cfService;

    public BlockchainService(ScpCfService cfService) {
        this.cfService = cfService;
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

        cfService.addBearerTokenHeader(chaincodeEnpointRequest);
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

    static BlockchainService getBlockchainService(final String serviceType)
            throws ShouldNotHappenException {
        try {
            final ScpCfService cfService = ScpCfService.of(serviceType, null,
                    "credentials/oAuth/url", "credentials/oAuth/clientId",
                    "credentials/oAuth/clientSecret", "credentials/serviceUrl");

            return new BlockchainService(cfService);
        } catch (ShouldNotHappenException e) {
            throw e;
        } catch (final Exception e) {
            throw new ShouldNotHappenException("Failed to setup Blockchain service: " + e.getMessage(), e);
        }
    }

    public String getServiceUrl() {
        return cfService.getServiceLocationInfo();
    }

    @Override
    public String toString() {
        return "BlockchainService{" +
                "cfService=" + cfService +
                '}';
    }

}
