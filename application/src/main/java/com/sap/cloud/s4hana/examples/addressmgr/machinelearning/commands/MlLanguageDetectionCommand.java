package com.sap.cloud.s4hana.examples.addressmgr.machinelearning.commands;


import com.google.gson.Gson;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Collections;

import com.sap.cloud.s4hana.examples.addressmgr.machinelearning.Detection;
import com.sap.cloud.s4hana.examples.addressmgr.machinelearning.MlLanguageDetectionResult;
import com.sap.cloud.s4hana.examples.addressmgr.machinelearning.MlService;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpEntityUtil;
import com.sap.cloud.sdk.cloudplatform.connectivity.ScpCfService;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.Command;

public class MlLanguageDetectionCommand extends Command<Detection> {
    private static final Logger logger = CloudLoggerFactory.getLogger(MlLanguageDetectionCommand.class);
    public static final String LANGUAGE_DETECTION_SUFFIX = "/api/v2alpha1/text/lang-detect";

    private final MlService mlService;
    private final String input;

    private static final String SERVICE_TYPE = "ml-foundation-trial-beta";
    private static final String AUTH_URL_JSON_PATH = "credentials/url";
    private static final String CLIENT_ID_JSON_PATH = "credentials/clientid";
    private static final String CLIENT_SECRET_JSON_PATH = "credentials/clientsecret";
    private static final String SERVICE_LOCATION_JSON_PATH = "credentials/serviceurls/LANG_DETECTION_API_URL";

    public MlLanguageDetectionCommand(final MlService mlService, String input) {
        super(HystrixCommandGroupKey.Factory.asKey("LeonardoMlFoundation-langdetect"), 10000);
        this.mlService = mlService;
        this.input = input;
    }

    @Override
    protected Detection run() throws Exception {
        // Instantiate ScpCfService
        final ScpCfService mlService = ScpCfService.of(SERVICE_TYPE, null, AUTH_URL_JSON_PATH,
                CLIENT_ID_JSON_PATH, CLIENT_SECRET_JSON_PATH, SERVICE_LOCATION_JSON_PATH);

        // Get service URL
        String languageDetectionUrl = mlService.getServiceLocationInfo() + LANGUAGE_DETECTION_SUFFIX;

        URI serviceUrl = new URI(languageDetectionUrl);

        // Create request
        HttpPost request = new HttpPost(serviceUrl);

        // Add bearer
        mlService.addBearerTokenHeader(request);

        // Add more HTTP headers to the request
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json;charset=UTF-8");

        String requestJson = new Gson().toJson(Collections.singletonMap("message", input));
        HttpEntity body = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
        request.setEntity(body);

        // Execute request
        final HttpResponse response = HttpClientAccessor.getHttpClient().execute(request);

        // retrieve entity content (requested json with Accept header, so should be text) and close request
        final String responsePayload = HttpEntityUtil.getResponseBody(response);

        // parsing response with custom-built POJO
        final MlLanguageDetectionResult mlLanguageDetectionResult =
                new Gson().fromJson(responsePayload, MlLanguageDetectionResult.class);

        // For the address manager - get the first detected language
        if (!mlLanguageDetectionResult.getDetections().isEmpty()) {
            Detection detection = mlLanguageDetectionResult.getDetections().get(0);
            return detection;
        } else {
            throw new RuntimeException("Language is not found!");
        }
    }

    @Override
    protected Detection getFallback() {
        logger.error("Language detection fallback called because of exception", getExecutionException());
        final Detection enResult = new Detection();
        enResult.setConfidence(0.);
        enResult.setLangCode("de");
        return enResult;
    }
}
