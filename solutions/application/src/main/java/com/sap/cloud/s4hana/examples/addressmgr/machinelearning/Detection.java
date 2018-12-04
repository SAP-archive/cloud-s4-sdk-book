package com.sap.cloud.s4hana.examples.addressmgr.machinelearning;

import lombok.Data;

@Data
public class Detection {
    private Double confidence;
    private String langCode;
    private String langStr;
}
