package com.sap.cloud.s4hana.examples.addressmgr.commands;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.odatav2.connectivity.ODataDeleteResult;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import org.slf4j.Logger;

public class DeleteAddressCommand extends ErpCommand<Integer> {
    private static final Logger logger = CloudLoggerFactory.getLogger(DeleteAddressCommand.class);

    private final BusinessPartnerService service;
    private final String businessPartnerId;
    private final String addressId;

    public DeleteAddressCommand(final BusinessPartnerService service,
                                final String businessPartnerId, final String addressId) {
        super(HystrixUtil.getDefaultErpCommandSetter(
                DeleteAddressCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.businessPartnerId = businessPartnerId;
        this.addressId = addressId;
    }

    @Override
    protected Integer run() throws Exception {
        final BusinessPartnerAddress addressToDelete = BusinessPartnerAddress.builder()
                .businessPartner(businessPartnerId)
                .addressID(addressId)
                .build();

        final ODataDeleteResult oDataDeleteResult = service
                .deleteBusinessPartnerAddress(addressToDelete)
                .execute();

        return oDataDeleteResult.getHttpStatusCode();
    }
}
