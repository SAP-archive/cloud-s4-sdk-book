package com.sap.cloud.s4hana.examples.addressmgr.models;

import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerField;

import java.util.Calendar;

public class BusinessPartnerCustomFields {
    public static final BusinessPartnerField<String> ADDRESSES_LAST_CHECKED_BY =
            new BusinessPartnerField<>("YY1_AddrLastCheckedBy_bus");
}
