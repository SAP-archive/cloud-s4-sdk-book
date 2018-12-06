package com.sap.cloud.s4hana.examples.addressmgr.machinelearning;

public class Detection {
    private Double confidence;
    private String langCode;
    private String langStr;

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(final Double confidence) {
        this.confidence = confidence;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(final String langCode) {
        this.langCode = langCode;
    }

    public String getLangStr() {
        return langStr;
    }

    public void setLangStr(final String langStr) {
        this.langStr = langStr;
    }
}
