package com.sap.cloud.s4hana.examples.addressmgr.machinelearning;

import java.util.List;

public class MlLanguageDetectionResult {
    private List<Detection> detections;

    public List<Detection> getDetections() {
        return detections;
    }

    public void setDetections(final List<Detection> detections) {
        this.detections = detections;
    }
}
