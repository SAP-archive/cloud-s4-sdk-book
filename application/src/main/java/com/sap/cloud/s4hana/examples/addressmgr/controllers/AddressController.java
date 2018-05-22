package com.sap.cloud.s4hana.examples.addressmgr.controllers;

import com.google.common.base.Strings;
import com.sap.cloud.s4hana.examples.addressmgr.commands.CreateAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.DeleteAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.UpdateAddressCommand;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/addresses", produces = "application/json")
public class AddressController {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(AddressController.class);

    private final BusinessPartnerService service;

    @Autowired
    public AddressController(BusinessPartnerService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    protected ResponseEntity<BusinessPartnerAddress> createAddress(@RequestBody final BusinessPartnerAddress address) {
        logger.info("Received post request to create address {}", address);
        final BusinessPartnerAddress addressCreated = new CreateAddressCommand(service, address).execute();

        return ResponseEntity.status(HttpStatus.CREATED).body(addressCreated);
    }

    @RequestMapping(method = RequestMethod.PATCH, consumes = "application/json")
    protected ResponseEntity<String> updateAddress(
            @RequestParam final String businessPartnerId,
            @RequestParam final String addressId,
            @RequestBody final BusinessPartnerAddress address) {
        BusinessPartnerAddress addressToUpdate = createAddressToUpdate(businessPartnerId, addressId, address);

        logger.info("Received patch request to update address {}", address);

        new UpdateAddressCommand(service, addressToUpdate).execute();

        return ResponseEntity.noContent().build();
    }

    private BusinessPartnerAddress createAddressToUpdate(String businessPartnerId, String addressId,
                                                         BusinessPartnerAddress addressFromBody) {
        final BusinessPartnerAddress addressToUpdate = BusinessPartnerAddress.builder()
                .businessPartner(businessPartnerId)
                .addressID(addressId)
                .build();
        // Only change properties for which non-null values have been provided
        if (addressFromBody.getStreetName() != null)
            addressToUpdate.setStreetName(addressFromBody.getStreetName());
        if (addressFromBody.getHouseNumber() != null)
            addressToUpdate.setHouseNumber(addressFromBody.getHouseNumber());
        if (addressFromBody.getCityName() != null)
            addressToUpdate.setCityName(addressFromBody.getCityName());
        if (addressFromBody.getPostalCode() != null)
            addressToUpdate.setPostalCode(addressFromBody.getPostalCode());
        if (addressFromBody.getCountry() != null)
            addressToUpdate.setCountry(addressFromBody.getCountry());
        return addressToUpdate;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    protected ResponseEntity<String> deleteAddress(
            @RequestParam final String businessPartnerId,
            @RequestParam final String addressId,
            HttpServletRequest request) {
        if (Strings.isNullOrEmpty(businessPartnerId) || Strings.isNullOrEmpty(addressId)) {
            logger.warn(
                    "Invalid request to delete - at least one mandatory " + "parameter missing, query was: {}",
                    request.getQueryString());
            return ResponseEntity.badRequest().body(
                    "Bad request: please provide query parameters " + "businessPartnerId and addressId");
        }

        logger.info("Received delete request to delete address {},{}", businessPartnerId, addressId);

        new DeleteAddressCommand(service, businessPartnerId, addressId).execute();

        return ResponseEntity.noContent().build();
    }
}
