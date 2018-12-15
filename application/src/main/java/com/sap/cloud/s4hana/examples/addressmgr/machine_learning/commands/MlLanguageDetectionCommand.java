package com.sap.cloud.s4hana.examples.addressmgr.machine_learning.commands;

import com.google.gson.Gson;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.sap.cloud.s4hana.examples.addressmgr.machine_learning.MlLanguageDetectionResult;
import com.sap.cloud.s4hana.examples.addressmgr.machine_learning.MlService;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpEntityUtil;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.Command;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

public class MlLanguageDetectionCommand extends Command<MlLanguageDetectionResult> {
    private static final Logger logger = CloudLoggerFactory.getLogger(MlLanguageDetectionCommand.class);
    private static final String LANG_DETECT_PATH = "/";

    private final MlService mlService;
    private final String input;

    public MlLanguageDetectionCommand(final MlService mlService, final String input) {
        super(HystrixCommandGroupKey.Factory.asKey("LeonardoMlFoundation-langdetect"), 10000);
        this.mlService = mlService;
        this.input = input;
    }

    @Override
    protected MlLanguageDetectionResult run() throws Exception {
        final String response = executeRequest(createRequestJson(input));
        return parseResponse(response);
    }

    private String executeRequest(final String requestJson) throws Exception {
        final HttpPost request = mlService.createPostRequest(LANG_DETECT_PATH);

        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json;charset=UTF-8");

        request.setEntity(new StringEntity(requestJson, ContentType.APPLICATION_JSON));

        try {
            final HttpResponse response = HttpClientAccessor.getHttpClient().execute(request);

            return HttpEntityUtil.getResponseBody(response);
        } finally {
            request.releaseConnection();
        }
    }

    private static String createRequestJson(final String input) {
        return new Gson().toJson(Collections.singletonMap("message", input));
    }

    private static MlLanguageDetectionResult parseResponse(final String response) {
        final ResponseWrapper wrapper = new Gson().fromJson(response, ResponseWrapper.class);
        if(wrapper.getDetections() == null || wrapper.getDetections().isEmpty()) {
            logger.warn("No language detections in response {}", response);
            return new MlLanguageDetectionResult();
        }
        return wrapper.getDetections().get(0);
    }

    @Override
    protected MlLanguageDetectionResult getFallback() {
        logger.warn("Fallback called because of exception", getExecutionException());
        final MlLanguageDetectionResult enResult = new MlLanguageDetectionResult();
        enResult.setConfidence(0.);
        enResult.setLangStr("en");
        return enResult;
    }

    private static class ResponseWrapper {
        private List<MlLanguageDetectionResult> detections;

        public List<MlLanguageDetectionResult> getDetections() {
            return detections;
        }

        public void setDetections(List<MlLanguageDetectionResult> detections) {
            this.detections = detections;
        }

        @Override
        public String toString() {
            return "ResponseWrapper{" +
                    "detections=" + detections +
                    '}';
        }
    }
}
