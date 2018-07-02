package com.sap.cloud.s4hana.examples.addressmgr.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.cloudplatform.tenant.TenantAccessor;
import com.sap.cloud.sdk.cloudplatform.tenant.exception.TenantNotAvailableException;

import static com.sap.cloud.s4hana.examples.addressmgr.util.TenantUtil.DEFAULT_TENANT;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver
{
    private static final Logger logger = CloudLoggerFactory.getLogger(TenantIdentifierResolver.class);

    @Override
    public String resolveCurrentTenantIdentifier() {
        try {
            return TenantAccessor.getCurrentTenant().getTenantId();
        } catch (TenantNotAvailableException e) {
            logger.warn("Tenant not found", e);
            return DEFAULT_TENANT;
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
