package com.sap.cloud.s4hana.examples.addressmgr.commands;

import com.sap.cloud.s4hana.examples.addressmgr.vdm.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.s4hana.examples.addressmgr.vdm.services.BusinessPartnerService;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;

public class GetAllBusinessPartnersCommand extends ErpCommand<List<BusinessPartner>>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(GetAllBusinessPartnersCommand.class);

    private static final String CATEGORY_PERSON = "1";

    private final BusinessPartnerService service;

    public GetAllBusinessPartnersCommand( final BusinessPartnerService service )
    {
        super(
            HystrixUtil.getDefaultErpCommandSetter(
                GetAllBusinessPartnersCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
    }

    @Override
    protected List<BusinessPartner> run()
        throws Exception
    {
        final List<BusinessPartner> businessPartners =
            service
                .getAllBusinessPartner()
                .select(
                    BusinessPartner.BUSINESS_PARTNER,
                    BusinessPartner.LAST_NAME,
                    BusinessPartner.FIRST_NAME,
                    BusinessPartner.ADDR_LAST_CHECKED_BY_BUS,
                    BusinessPartner.ADDR_LAST_CHECKED_ON_BUS)
                .filter(BusinessPartner.BUSINESS_PARTNER_CATEGORY.eq(CATEGORY_PERSON))
                .orderBy(BusinessPartner.LAST_NAME, Order.ASC)
                .execute();

        return businessPartners;
    }

    @Override
    protected List<BusinessPartner> getFallback()
    {
        logger.warn("Fallback called because of exception:", getExecutionException());
        return Collections.emptyList();
    }
}
