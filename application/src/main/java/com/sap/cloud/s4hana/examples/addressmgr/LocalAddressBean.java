package com.sap.cloud.s4hana.examples.addressmgr;

import com.sap.cloud.s4hana.examples.addressmgr.models.Address;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class LocalAddressBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Address> getAllAddresses() {
        return entityManager.createNamedQuery("Address.findAll", Address.class).getResultList();
    }

    public void storeNewAddress(final Address address) {
        entityManager.persist(address);
    }

    public void updateExistingAddress(final Address address) {
        entityManager.merge(address);
    }

}
