package com.sap.cloud.s4hana.examples.addressmgr.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@NamedQueries({
        @NamedQuery(name="Address.findAll",
                query="SELECT a FROM Address a"),
        @NamedQuery(name="Address.findByStatus",
                query="SELECT a FROM Address a WHERE a.status = :status")
})
@Accessors(chain = true)
public class Address
{
    @Getter
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="address_id_seq")
    @SequenceGenerator(name="address_id_seq", sequenceName="address_id_seq", allocationSize=1)
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
