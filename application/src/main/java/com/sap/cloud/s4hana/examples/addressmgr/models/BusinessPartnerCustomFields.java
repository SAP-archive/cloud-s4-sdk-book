package com.sap.cloud.s4hana.examples.addressmgr.models;

import com.sap.cloud.sdk.s4hana.datamodel.odata.adapter.LocalDateTimeCalendarConverter;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.field.BusinessPartnerField;

import java.time.LocalDateTime;
import java.util.Calendar;

public class BusinessPartnerCustomFields {
  public static final BusinessPartnerField<String> ADDRESSES_LAST_CHECKED_BY = new BusinessPartnerField<>(
          "YY1_AddrLastCheckedBy_bus");

  public static final BusinessPartnerField<LocalDateTime> ADDRESSES_LAST_CHECKED_ON = new BusinessPartnerField<>(
          "YY1_AddrLastCheckedOn_bus", new LocalDateTimeCalendarConverter());
}
