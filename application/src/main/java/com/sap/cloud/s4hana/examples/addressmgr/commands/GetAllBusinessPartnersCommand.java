package com.sap.cloud.s4hana.examples.addressmgr.commands;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GetAllBusinessPartnersCommand extends CachingErpCommand<List<BusinessPartner>> {
    private static final Logger logger = CloudLoggerFactory.getLogger(GetAllBusinessPartnersCommand.class);

    private static final String CATEGORY_PERSON = "1";

    private static final Cache<CacheKey, List<BusinessPartner>> cache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    private final BusinessPartnerService service;

    public GetAllBusinessPartnersCommand(final BusinessPartnerService service) {
        super(HystrixUtil.getDefaultErpCommandSetter(
                GetAllBusinessPartnersCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
    }

    @Override
    protected Cache<CacheKey, List<BusinessPartner>> getCache() {
        return cache;
    }

    @Override
    protected List<BusinessPartner> runCacheable() throws Exception {
        // TODO: Task 1 - Retrieve a list of business partners
        throw new RuntimeException("TODO: Implement");
    }

    @Override
    protected List<BusinessPartner> getFallback() {
        logger.warn("Fallback called because of exception:", getExecutionException());
        return Collections.emptyList();
    }
}
