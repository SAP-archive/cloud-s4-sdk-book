package com.sap.cloud.s4hana.examples.addressmgr.commands;

import org.slf4j.Logger;

import com.sap.cloud.s4hana.examples.addressmgr.vdm.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.s4hana.examples.addressmgr.vdm.services.BusinessPartnerService;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.odatav2.connectivity.ODataUpdateResult;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;

public class UpdateAddressCommand extends ErpCommand<Integer>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(UpdateAddressCommand.class);

    private final BusinessPartnerService service;
    private final BusinessPartnerAddress addressToUpdate;

    public UpdateAddressCommand( final BusinessPartnerService service, final BusinessPartnerAddress addressToUpdate )
    {
        super(
            HystrixUtil.getDefaultErpCommandSetter(
                UpdateAddressCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.addressToUpdate = addressToUpdate;
    }

    @Override
    protected Integer run()
        throws Exception
    {
        final ODataUpdateResult updateResult = service.updateBusinessPartnerAddress(addressToUpdate).execute();
        return updateResult.getHttpStatusCode();
    }
}
