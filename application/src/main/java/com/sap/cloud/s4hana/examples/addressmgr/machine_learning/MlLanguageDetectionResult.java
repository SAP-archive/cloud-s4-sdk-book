package com.sap.cloud.s4hana.examples.addressmgr.machine_learning;

import java.util.Locale;

public class MlLanguageDetectionResult {

    private Double confidence;
    private String langCode;
    private String langStr;

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getLangStr() {
        return langStr;
    }

    public void setLangStr(String langStr) {
        this.langStr = langStr;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "MlLanguageDetectionResult{confidence=%.2f, langCode='%s', langStr='%s'}",
                confidence, langCode, langStr);
    }
}
