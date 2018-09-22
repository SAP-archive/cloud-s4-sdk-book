package com.sap.cloud.s4hana.examples.addressmgr.commands;

import com.sap.cloud.s4hana.examples.addressmgr.models.BusinessPartnerCustomFields;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.odatav2.connectivity.ODataUpdateResult;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import org.slf4j.Logger;

import java.time.LocalDateTime;

public class MarkBusinessPartnerAsCheckedCommand extends ErpCommand<Void> {
    private static final Logger logger = CloudLoggerFactory.getLogger(MarkBusinessPartnerAsCheckedCommand.class);

    private final BusinessPartnerService service;
    private final String businessPartnerId;
    private final String checkedByUserName;
    private final LocalDateTime checkedOnDate;

    public MarkBusinessPartnerAsCheckedCommand(final BusinessPartnerService service,
                                               final String businessPartnerId, final String checkedByUserName,
                                               final LocalDateTime checkedOnDate) {
        super(HystrixUtil.getDefaultErpCommandSetter(
                MarkBusinessPartnerAsCheckedCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.businessPartnerId = businessPartnerId;
        this.checkedByUserName = checkedByUserName;
        this.checkedOnDate = checkedOnDate;
    }

    @Override
    protected Void run() throws Exception {
        final BusinessPartner businessPartnerToUpdate = BusinessPartner.builder()
                .businessPartner(businessPartnerId)
                .build();
        businessPartnerToUpdate.setCustomField(BusinessPartnerCustomFields.ADDRESSES_LAST_CHECKED_BY,
                checkedByUserName);
        businessPartnerToUpdate.setCustomField(BusinessPartnerCustomFields.ADDRESSES_LAST_CHECKED_ON,
                checkedOnDate);

        final ODataUpdateResult updateResult = service
                .updateBusinessPartner(businessPartnerToUpdate)
                .execute();

        return null;
    }
}
