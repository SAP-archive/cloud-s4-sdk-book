package com.sap.cloud.s4hana.examples.addressmgr.machine_learning.commands;

import com.google.gson.*;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.sap.cloud.s4hana.examples.addressmgr.machine_learning.MlService;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpEntityUtil;
import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.Command;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;

import java.util.*;

public class MlTranslationCommand extends Command<List<String>> {
    private static final Logger logger = CloudLoggerFactory.getLogger(MlTranslationCommand.class);

    private final MlService mlService;

    private final String sourceLang;
    private final String targetLang;
    private final List<String> texts;

    public MlTranslationCommand(final MlService mlService,
                                final String sourceLang, final String targetLang, final List<String> texts) {
        super(HystrixCommandGroupKey.Factory.asKey("LeonardoMlFoundation-translate"), 10000);

        this.mlService = mlService;
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
        HttpPost request = mlService.createPostRequest();

        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json;charset=UTF-8");

        logger.trace("Request: {}", requestJson);

        HttpEntity body = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
        request.setEntity(body);

        try {
            // Getting cached http client for base URL, reuse of connection and other resources - and send request
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
        } finally {
            request.releaseConnection();
        }
    }

    static Map<String, String> parseResponse(String response) {
        // this method provides an example of how parsing can be fine-tuned when using the JsonParser directly:
        Map<String, String> inputToTranslations = new HashMap<>();
        final JsonElement parsedTree = new JsonParser().parse(response);
        if (parsedTree.isJsonObject() && parsedTree.getAsJsonObject().has("units")) {
            final JsonElement units = parsedTree.getAsJsonObject().get("units");
            if (units.isJsonArray() && units.getAsJsonArray().size() > 0) {
                for (JsonElement unit : units.getAsJsonArray()) {
                    if (unit.isJsonObject() && unit.getAsJsonObject().has("value")
                            && unit.getAsJsonObject().has("translations")) {
                        String currentInput = unit.getAsJsonObject().get("value").getAsString();
                        final JsonElement translations = unit.getAsJsonObject().get("translations");
                        if (translations.isJsonArray() && translations.getAsJsonArray().size() > 0) {
                            final JsonElement firstTranslation = translations.getAsJsonArray().get(0);
                            if (firstTranslation.isJsonObject() && firstTranslation.getAsJsonObject().has("value")) {
                                final JsonElement translationElement = firstTranslation.getAsJsonObject().get("value");
                                String translation = "";
                                if (!translationElement.isJsonNull()) {
                                    translation = translationElement.getAsString();
                                }
                                inputToTranslations.put(currentInput, translation);
                            }
                        }
                    }
                }
            }
        }
        return inputToTranslations;
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
