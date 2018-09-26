package com.sap.cloud.s4hana.examples.addressmgr.machine_learning;

public enum MlServiceType {
    TRANSLATION("TRANSLATION_URL"),
    LANGUAGE_DETECTION("LANG_DETECTION_API_URL");

    private final String serviceUrlPathSuffix;

    MlServiceType(final String serviceUrlPathSuffix) {
        this.serviceUrlPathSuffix = serviceUrlPathSuffix;
    }

    public String getServiceUrlPathSuffix() {
        return serviceUrlPathSuffix;
    }
}
