package com.sap.cloud.s4hana.examples.addressmgr.commands;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sap.cloud.s4hana.examples.addressmgr.custom.namespaces.bupasocialmedia.BusinessPartnerSocialMedia;
import com.sap.cloud.s4hana.examples.addressmgr.custom.namespaces.bupasocialmedia.SocialMediaAccount;
import com.sap.cloud.s4hana.examples.addressmgr.custom.services.BusinessPartnerSocialMediaService;
import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GetAllSocialMediaAccountsCommand extends CachingErpCommand<List<SocialMediaAccount>> {
    private static final Logger logger = CloudLoggerFactory.getLogger(GetAllSocialMediaAccountsCommand.class);

    private static final Cache<CacheKey, List<SocialMediaAccount>> cache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    private final BusinessPartnerSocialMediaService service;
    private final String businessPartnerId;

    public GetAllSocialMediaAccountsCommand(final BusinessPartnerSocialMediaService service,
                                            final String businessPartnerId) {
        super(HystrixUtil.getDefaultErpCommandSetter(
                GetAllSocialMediaAccountsCommand.class,
                HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));

        this.service = service;
        this.businessPartnerId = businessPartnerId;
    }

    @Nonnull
    @Override
    protected Cache<CacheKey, List<SocialMediaAccount>> getCache() {
        return cache;
    }

    @Nonnull
    @Override
    protected CacheKey getCommandCacheKey() {
        return super.getCommandCacheKey().append(businessPartnerId);
    }

    @Override
    protected List<SocialMediaAccount> runCacheable() throws Exception {
        final List<BusinessPartnerSocialMedia> socialMediaList = service.getAllBusinessPartnerSocialMedia()
                .filter(BusinessPartnerSocialMedia.BUSINESS_PARTNER.eq(businessPartnerId))
                .select(BusinessPartnerSocialMedia.TO_SOCIAL_MEDIA_ACCOUNT
                        .select(SocialMediaAccount.SERVICE, SocialMediaAccount.ACCOUNT))
                .execute();
        if (socialMediaList.isEmpty()) {
            logger.info("Business partner {} has no entry in social media store.");
            return Collections.emptyList();
        }
        if (socialMediaList.size() > 1) {
            logger.warn("Found {} entries for the same business partner {} in social media store, " +
                            "going to use the first one only.",
                    socialMediaList.size(), businessPartnerId);
        }
        return socialMediaList.get(0).getSocialMediaAccountIfPresent().orElse(Collections.emptyList());
    }

    @Override
    protected List<SocialMediaAccount> getFallback() {
        logger.warn("Fallback called because of exception:", getExecutionException());
        return Collections.emptyList();
    }
}
