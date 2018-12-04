package com.sap.cloud.s4hana.examples.addressmgr.machinelearning.commands;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpEntityUtil;
import com.sap.cloud.sdk.cloudplatform.connectivity.ScpCfService;
import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.Command;

public class MlTranslationCommand extends Command<List<String>> {
    private static final Logger logger = CloudLoggerFactory.getLogger(MlTranslationCommand.class);

    private static final String SERVICE_TYPE = "ml-foundation-trial-beta";
    private static final String AUTH_URL_JSON_PATH = "credentials/url";
    private static final String CLIENT_ID_JSON_PATH = "credentials/clientid";
    private static final String CLIENT_SECRET_JSON_PATH = "credentials/clientsecret";
    private static final String SERVICE_LOCATION_JSON_PATH = "credentials/serviceurls/TRANSLATION_URL";


    private final String sourceLang;
    private final String targetLang;
    private final List<String> texts;

    public MlTranslationCommand(final String sourceLang,
                                final String targetLang, final List<String> texts) {
        super(HystrixCommandGroupKey.Factory.asKey("LeonardoMlFoundation-translate"), 10000);

        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
        this.texts = texts;
    }

    @Override
    protected List<String> run() throws Exception {
        if (texts == null || texts.isEmpty()) {
            return Collections.emptyList();
        }

        return translate(sourceLang, targetLang, texts);
    }

    @Override
    protected List<String> getFallback() {
        logger.warn("Fallback called because of exception", getExecutionException());
        final String[] emptyStringsResult = new String[texts.size()];
        Arrays.fill(emptyStringsResult, "");
        return Arrays.asList(emptyStringsResult);
    }

    private List<String> translate(String sourceLang, String targetLang, List<String> texts) throws Exception {
        final String requestJson = createRequestJson(sourceLang, targetLang, texts);

        final String responsePayload = executeRequest(requestJson);

        Map<String, String> inputToTranslations = parseResponse(responsePayload);

        if (inputToTranslations.size() != texts.size()) {
            logger.warn("Translation requests returned too little results, {} requested, {} returned.",
                    texts.size(), inputToTranslations.size());
        }

        List<String> translationsInCorrectOrder = new ArrayList<>(texts.size());
        for (String text : texts) {
            String currentTranslation = ""; // result will be empty string if no translation was provided
            currentTranslation = inputToTranslations.get(text);
            translationsInCorrectOrder.add(currentTranslation);
        }
        return translationsInCorrectOrder;
    }

    private String executeRequest(String requestJson) throws Exception {

        // Instantiate ScpCfService
        final ScpCfService mlService = ScpCfService.of(SERVICE_TYPE, null, AUTH_URL_JSON_PATH,
          CLIENT_ID_JSON_PATH, CLIENT_SECRET_JSON_PATH, SERVICE_LOCATION_JSON_PATH);

        // Get service URL
        URI serviceUrl = new URI(mlService.getServiceLocationInfo());

        // Create request
        HttpPost request = new HttpPost(serviceUrl);

        // Add bearer
        mlService.addBearerTokenHeader(request);

        // Add more HTTP headers to the request
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json;charset=UTF-8");

        HttpEntity body = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
        request.setEntity(body);

        // Execute request
        final HttpResponse response = HttpClientAccessor.getHttpClient().execute(request);

        // retrieve entity content (requested json with Accept header, so should be text) and close request
        final String responsePayload = HttpEntityUtil.getResponseBody(response);

        final StatusLine statusLine = response.getStatusLine();
        logger.trace("Response status: {}, content: {}", statusLine, responsePayload);

        switch (statusLine.getStatusCode()) {
            case HttpStatus.SC_OK:
                break;
            case HttpStatus.SC_REQUEST_TOO_LONG:
            case HttpStatus.SC_BAD_REQUEST:
                logger.debug("Received request input problem response: {}. Content: {}", statusLine, responsePayload);
                throw new HystrixBadRequestException("Malformed request: " + statusLine.getStatusCode());
            default:
                logger.error("Service seems unavailable: {}. Content: {}", statusLine, responsePayload);
                throw new ShouldNotHappenException("Service seems unavailable: Received response: " + statusLine);
        }
        return responsePayload;
    }

    static Map<String, String> parseResponse(String response) {
        Gson gson = new Gson();
        TranslationResult translationResult = gson.fromJson(response, TranslationResult.class);

        Map<String, String> inputToTranslations = new HashMap<>();

        for (TranslationUnit translationUnit : translationResult.getUnits()) {
            String translation = "";
            if (!translationUnit.getTranslations().isEmpty()) {
                // Taking the first translation as a particular use case for the address manager
                translation = translationUnit.getTranslations().get(0).getValue();
            }
            inputToTranslations.put(translationUnit.getValue(), translation);
        }
        return inputToTranslations;
    }

    @Data
    private class TranslationResult {
        List<TranslationUnit> units = Lists.newArrayList();
    }
    @Data
    private class TranslationUnit {
        String value;
        List<Translation> translations = Lists.newArrayList();
    }
    @Data
    private class Translation {
        String language;
        String value;
    }

    static String createRequestJson(String sourceLang, String targetLang, List<String> texts) {
        final JsonObject requestContent = new JsonObject();
        requestContent.addProperty("sourceLanguage", sourceLang);
        requestContent.add("targetLanguages", new Gson().toJsonTree(new String[]{targetLang}));
        JsonArray sourcesList = new JsonArray();
        requestContent.add("units", sourcesList);

        for (String text : texts) {
            JsonObject inputSource = new JsonObject();
            inputSource.addProperty("value", text);
            sourcesList.add(inputSource);
        }

        return new Gson().toJson(requestContent);
    }

}
