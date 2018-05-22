package com.sap.cloud.s4hana.examples.addressmgr.controllers;

import com.google.common.collect.Lists;
import com.sap.cloud.s4hana.examples.addressmgr.commands.CreateAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.models.Address;
import com.sap.cloud.s4hana.examples.addressmgr.models.AddressRepository;
import com.sap.cloud.s4hana.examples.addressmgr.models.Status;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/addresses-local", produces = "application/json")
public class LocalAddressController {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(LocalAddressController.class);

    private final AddressRepository addressRepository;
    private final BusinessPartnerService businessPartnerService;

    @Autowired
    private LocalAddressController(
            final AddressRepository addressRepository,
            final BusinessPartnerService businessPartnerService) {
        this.addressRepository = addressRepository;
        this.businessPartnerService = businessPartnerService;
    }

    @GetMapping
    protected ResponseEntity<List<Address>> doGet(@RequestParam(required = false) final String status) {
        // Retrieve all addresses
        if (status == null) {
            final List<Address> addresses = Lists.newLinkedList(addressRepository.findAll());
            return ResponseEntity.ok(addresses);
        }
        // Retrieve addresses by status
        else if (EnumUtils.isValidEnum(Status.class, status)) {
            final List<Address> addresses = addressRepository.findByStatus(status);
            return ResponseEntity.ok(addresses);
        }
        // Fail: wrong status in HTTP request
        else {
            logger.error("Wrong status value. Possible values: new, approved, rejected.");
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(consumes = "application/json")
    protected ResponseEntity<List<Address>> doPost(@RequestBody final Address address) {
        address.setStatus(Status.NEW.toString());

        logger.info("Received post request to create local address {}", address);

        // Persist the address
        addressRepository.save(address);

        // Retrieve all current local addresses
        final List<Address> addresses = Lists.newLinkedList(addressRepository.findAll());

        return ResponseEntity.ok(addresses);
    }

    @PatchMapping
    protected ResponseEntity<String> doPatch(@RequestParam final Long addressId, @RequestParam final Status status) {
        try {
            final Address address = addressRepository.findById(addressId).orElse(null);
            if (address == null) {
                throw new IllegalArgumentException(String.format("Address with the id %d does not exist", addressId));
            } else {
                switch (status) {
                    case APPROVED:
                        approveAddress(address);
                        break;
                    case REJECTED:
                        rejectAddress(address);
                        break;
                }
            }
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Wrong parameters in the HTTP request");
            return ResponseEntity.badRequest().build();
        }
    }

    private void approveAddress(final Address address) {
        logger.info("Address approved - changing the status and creating in S/4HANA {}", address);

        address.setStatus(Status.APPROVED.toString());
        addressRepository.save(address);

        BusinessPartnerAddress businessPartnerAddress = convertAddressToVdmObject(address);
        new CreateAddressCommand(businessPartnerService, businessPartnerAddress).execute();
    }

    private void rejectAddress(final Address address) {
        logger.info("Address rejected - changing the status {}", address);

        address.setStatus(Status.REJECTED.toString());
        addressRepository.save(address);
    }

    private BusinessPartnerAddress convertAddressToVdmObject(final Address address) {
        return BusinessPartnerAddress
                .builder()
                .businessPartner(address.getBusinessPartner())
                .cityName(address.getCityName())
                .country(address.getCountry())
                .houseNumber(address.getHouseNumber())
                .postalCode(address.getPostalCode())
                .streetName(address.getStreetName())
                .build();
    }
}
