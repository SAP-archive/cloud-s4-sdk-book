package com.sap.cloud.s4hana.examples.addressmgr.commands;

import org.slf4j.Logger;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.cloudplatform.resilience.ResilienceConfiguration;
import com.sap.cloud.sdk.cloudplatform.resilience.ResilienceConfiguration.CacheConfiguration;
import com.sap.cloud.sdk.cloudplatform.resilience.ResilienceConfiguration.TimeLimiterConfiguration;
import com.sap.cloud.sdk.cloudplatform.resilience.ResilienceDecorator;
import com.sap.cloud.sdk.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;

import com.sap.cloud.sdk.s4hana.connectivity.ErpHttpDestination;
import com.sap.cloud.sdk.s4hana.connectivity.ErpHttpDestinationUtils;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;

public class GetAllBusinessPartnersCommand {
    private static final Logger logger = CloudLoggerFactory.getLogger(GetAllBusinessPartnersCommand.class);

    private static final String CATEGORY_PERSON = "1";
    private final ErpHttpDestination destination = ErpHttpDestinationUtils
            .getErpHttpDestination("ERP_SYSTEM");

    private final BusinessPartnerService service;
    private final TimeLimiterConfiguration timeLimit;
    private final CacheConfiguration cacheConfiguration;
    private final ResilienceConfiguration resilienceConfiguration;


    public GetAllBusinessPartnersCommand(final BusinessPartnerService service) {
        this.service = service;
        timeLimit = TimeLimiterConfiguration.of()
                .timeoutDuration(Duration.ofSeconds(10));
        cacheConfiguration = CacheConfiguration
                .of(Duration.ofMinutes(5)).withoutParameters();
        resilienceConfiguration = ResilienceConfiguration.of(GetAllBusinessPartnersCommand.class)
                .timeLimiterConfiguration(timeLimit)
                .cacheConfiguration(cacheConfiguration);
    }

    public List<BusinessPartner> execute() {
        return ResilienceDecorator.executeCallable(
                this::run,
                resilienceConfiguration,
                e -> Collections.emptyList()
        );
    }

    private List<BusinessPartner> run() throws ODataException {
        return service
                .getAllBusinessPartner()
                .select(BusinessPartner.BUSINESS_PARTNER,
                        BusinessPartner.LAST_NAME,
                        BusinessPartner.FIRST_NAME)
                .filter(BusinessPartner.BUSINESS_PARTNER_CATEGORY.eq(CATEGORY_PERSON))
                .orderBy(BusinessPartner.LAST_NAME, Order.ASC)
                .execute(destination);
    }
}
