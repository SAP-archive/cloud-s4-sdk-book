package com.sap.cloud.s4hana.examples.addressmgr.machinelearning.commands;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
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
import com.sap.cloud.sdk.services.scp.machinelearning.CloudFoundryLeonardoMlServiceType;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlFoundation;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlService;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlServiceType;

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

        // TODO: Task 2 - Integrate the translation service using the S/4HANA CLoud SDK LeonardoMlService class and prepare the request
        LeonardoMlService mlService = null;
        HttpPost request = null;

        return mlService.invoke(request, response -> {
            return retrieveResponsePayload(response);
        });
    }

    private String retrieveResponsePayload(final HttpResponse response) {
        final String responsePayload;
        try {
            responsePayload = HttpEntityUtil.getResponseBody(response);

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
        } catch (IOException e) {
            logger.error("Error while retrieving response body", e);
            throw new RuntimeException(e);
        }
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

    private class TranslationResult {
        List<TranslationUnit> units = Lists.newArrayList();

        private List<TranslationUnit> getUnits() {
            return units;
        }

        private void setUnits(final List<TranslationUnit> units) {
            this.units = units;
        }
    }

    private class TranslationUnit {
        String value;
        List<Translation> translations = Lists.newArrayList();

        private String getValue() {
            return value;
        }

        private void setValue(final String value) {
            this.value = value;
        }

        private List<Translation> getTranslations() {
            return translations;
        }

        private void setTranslations(final List<Translation> translations) {
            this.translations = translations;
        }
    }

    private class Translation {
        String language;
        String value;

        private String getLanguage() {
            return language;
        }

        private void setLanguage(final String language) {
            this.language = language;
        }

        private String getValue() {
            return value;
        }

        private void setValue(final String value) {
            this.value = value;
        }
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
