package com.sap.cloud.s4hana.examples.addressmgr.commands;

import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;

public class CreateAddressCommand {
    private static final Logger logger = CloudLoggerFactory.getLogger(CreateAddressCommand.class);

    private final BusinessPartnerService service;
    private final BusinessPartnerAddress addressToCreate;

    public CreateAddressCommand(final BusinessPartnerService service, final BusinessPartnerAddress addressToCreate) {
        this.service = service;
        this.addressToCreate = addressToCreate;
    }

    public BusinessPartnerAddress execute() throws Exception {
        // TODO: Replace with Virtual Data Model query
        return null;
    }
}
