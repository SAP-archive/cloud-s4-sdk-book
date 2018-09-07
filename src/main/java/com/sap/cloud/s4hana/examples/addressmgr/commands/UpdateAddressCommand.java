package com.sap.cloud.s4hana.examples.addressmgr.commands;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.odatav2.connectivity.ODataUpdateResult;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import org.slf4j.Logger;

public class UpdateAddressCommand extends ErpCommand<Integer> {
    private static final Logger logger = CloudLoggerFactory.getLogger(UpdateAddressCommand.class);

    private final BusinessPartnerService service;
    private final BusinessPartnerAddress addressToUpdate;

    public UpdateAddressCommand(final BusinessPartnerService service, final BusinessPartnerAddress addressToUpdate) {
        super(HystrixUtil.getDefaultErpCommandSetter(
                UpdateAddressCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.addressToUpdate = addressToUpdate;
    }

    @Override
    protected Integer run() throws Exception {
        final ODataUpdateResult oDataUpdateResult = service
                .updateBusinessPartnerAddress(addressToUpdate)
                .execute();

        return oDataUpdateResult.getHttpStatusCode();
    }
}
