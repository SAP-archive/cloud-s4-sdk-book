package com.sap.cloud.s4hana.examples.addressmgr.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class Address {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
