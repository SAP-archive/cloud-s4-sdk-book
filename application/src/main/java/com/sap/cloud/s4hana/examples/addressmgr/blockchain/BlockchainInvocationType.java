package com.sap.cloud.s4hana.examples.addressmgr.blockchain;

public enum BlockchainInvocationType {
    INVOKE("invoke"),
    QUERY("query");

    private final String pathParamValue;

    BlockchainInvocationType(String pathParamValue) {
        this.pathParamValue = pathParamValue;
    }

    public String getPathParamValue() {
        return pathParamValue;
    }
}
