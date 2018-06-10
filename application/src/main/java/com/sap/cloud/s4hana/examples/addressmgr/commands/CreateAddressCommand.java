package com.sap.cloud.s4hana.examples.addressmgr.commands;

import org.slf4j.Logger;

import com.sap.cloud.s4hana.examples.addressmgr.vdm.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.s4hana.examples.addressmgr.vdm.services.BusinessPartnerService;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;

public class CreateAddressCommand extends ErpCommand<BusinessPartnerAddress>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(CreateAddressCommand.class);

    private final BusinessPartnerService service;
    private final BusinessPartnerAddress addressToCreate;

    public CreateAddressCommand( final BusinessPartnerService service, final BusinessPartnerAddress addressToCreate )
    {
        super(
            HystrixUtil.getDefaultErpCommandSetter(
                CreateAddressCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.addressToCreate = addressToCreate;
    }

    @Override
    protected BusinessPartnerAddress run()
        throws Exception
    {
        final BusinessPartnerAddress createdAddress = service.createBusinessPartnerAddress(addressToCreate).execute();
        return createdAddress;
    }
}
