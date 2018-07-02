package com.sap.cloud.s4hana.examples.addressmgr.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class AddressView {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String businessPartner;

    @Getter
    @Setter
    private String cityName;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String houseNumber;

    @Getter
    @Setter
    private String postalCode;

    @Getter
    @Setter
    private String streetName;

    @Getter
    @Setter
    private String status;
}
