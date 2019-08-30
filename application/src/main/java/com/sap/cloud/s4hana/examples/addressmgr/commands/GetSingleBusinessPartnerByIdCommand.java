package com.sap.cloud.s4hana.examples.addressmgr.commands;

import org.slf4j.Logger;

import java.time.Duration;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.cloudplatform.resilience.ResilienceConfiguration;
import com.sap.cloud.sdk.cloudplatform.resilience.ResilienceConfiguration.TimeLimiterConfiguration;
import com.sap.cloud.sdk.cloudplatform.resilience.ResilienceConfiguration.CacheConfiguration;
import com.sap.cloud.sdk.cloudplatform.resilience.ResilienceDecorator;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;

import com.sap.cloud.sdk.s4hana.connectivity.ErpHttpDestination;
import com.sap.cloud.sdk.s4hana.connectivity.ErpHttpDestinationUtils;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;

public class GetSingleBusinessPartnerByIdCommand {
    private static final Logger logger = CloudLoggerFactory.getLogger(GetAllBusinessPartnersCommand.class);

    private final ErpHttpDestination destination;
    private final BusinessPartnerService service;
    private final TimeLimiterConfiguration timeLimit;
    private final CacheConfiguration cacheConfiguration;
    private final ResilienceConfiguration resilienceConfiguration;

    private final String partnerId;


    public GetSingleBusinessPartnerByIdCommand(final BusinessPartnerService service, final String partnerId) {
        this.service = service;
        this.partnerId = partnerId;

        destination = ErpHttpDestinationUtils.getErpHttpDestination("ERP_SYSTEM");
        timeLimit = TimeLimiterConfiguration.of()
                .timeoutDuration(Duration.ofSeconds(10));
        cacheConfiguration = CacheConfiguration
                .of(Duration.ofMinutes(5))
                .withParameters(partnerId);
        resilienceConfiguration = ResilienceConfiguration.of(GetSingleBusinessPartnerByIdCommand.class)
                .timeLimiterConfiguration(timeLimit)
                .cacheConfiguration(cacheConfiguration);
    }

    public BusinessPartner execute() {
        return ResilienceDecorator.executeCallable(
                this::run,
                resilienceConfiguration,
                e -> BusinessPartner.builder().businessPartner(partnerId).build()
        );
    }

    private BusinessPartner run() throws ODataException {
        return service
                .getBusinessPartnerByKey(partnerId)
                .select(BusinessPartner.BUSINESS_PARTNER,
                        BusinessPartner.LAST_NAME,
                        BusinessPartner.FIRST_NAME,
                        BusinessPartner.IS_MALE,
                        BusinessPartner.IS_FEMALE,
                        BusinessPartner.CREATION_DATE,
                        BusinessPartner.TO_BUSINESS_PARTNER_ADDRESS.select(
                                BusinessPartnerAddress.BUSINESS_PARTNER,
                                BusinessPartnerAddress.ADDRESS_ID,
                                BusinessPartnerAddress.COUNTRY,
                                BusinessPartnerAddress.POSTAL_CODE,
                                BusinessPartnerAddress.CITY_NAME,
                                BusinessPartnerAddress.STREET_NAME,
                                BusinessPartnerAddress.HOUSE_NUMBER))
                .execute(destination);
    }
}