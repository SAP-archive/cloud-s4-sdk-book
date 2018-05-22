package com.sap.cloud.s4hana.examples.addressmgr.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
    // You may be wondering where is the query implementation?
    // Spring Data parses the method names here and derives query implementations from them.
    // This means you have to carefully follow method naming conventions to achieve desired results.

    List<Address> findByStatus(final String status);
}
