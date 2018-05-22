package com.sap.cloud.s4hana.examples.addressmgr.commands;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import org.slf4j.Logger;

public class ReadAddressCommand extends ErpCommand<BusinessPartnerAddress> {
    private static final Logger logger = CloudLoggerFactory.getLogger(ReadAddressCommand.class);

    private final BusinessPartnerService service;
    private final String businessPartnerId;
    private final String addressId;

    public ReadAddressCommand(final BusinessPartnerService service,
                              final String businessPartnerId, final String addressId) {
        super(HystrixUtil.getDefaultErpCommandSetter(
                ReadAddressCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.businessPartnerId = businessPartnerId;
        this.addressId = addressId;
    }

    @Override
    protected BusinessPartnerAddress run()
            throws Exception {
        return service
                .getBusinessPartnerAddressByKey(businessPartnerId, addressId)
                .execute();
    }

    @Override
    protected BusinessPartnerAddress getFallback() {
        logger.warn("Fallback called because of exception:", getExecutionException());
        return null;
    }
}
