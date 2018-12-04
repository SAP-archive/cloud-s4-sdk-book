package com.sap.cloud.s4hana.examples.addressmgr.machinelearning;

import lombok.Data;

import java.util.List;

@Data
public class MlLanguageDetectionResult {
    private List<Detection> detections;

//    @Override
//    public String toString() {
//        return String.format(Locale.ENGLISH, "MlLanguageDetectionResult{confidence=%.2f, langCode='%s', langStr='%s'}",
//                confidence, langCode, langStr);
//    }
}
