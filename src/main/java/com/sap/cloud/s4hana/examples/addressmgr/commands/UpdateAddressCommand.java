package com.sap.cloud.s4hana.examples.addressmgr.commands;

import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;

public class UpdateAddressCommand {
    private static final Logger logger = CloudLoggerFactory.getLogger(UpdateAddressCommand.class);

    private final BusinessPartnerService service;
    private final BusinessPartnerAddress addressToUpdate;

    public UpdateAddressCommand(final BusinessPartnerService service, final BusinessPartnerAddress addressToUpdate) {
        this.service = service;
        this.addressToUpdate = addressToUpdate;
    }

    public Integer execute() throws Exception {
        // TODO: Replace with Virtual Data Model query
        return null;
    }
}
