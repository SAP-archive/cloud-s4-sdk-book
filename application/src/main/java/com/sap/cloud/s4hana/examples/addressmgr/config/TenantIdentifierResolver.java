package com.sap.cloud.s4hana.examples.addressmgr.config;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.cloudplatform.tenant.TenantAccessor;
import com.sap.cloud.sdk.cloudplatform.tenant.exception.TenantAccessException;
import com.sap.cloud.sdk.cloudplatform.tenant.exception.TenantNotAvailableException;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private static final Logger logger = CloudLoggerFactory.getLogger(TenantIdentifierResolver.class);

    @Value("${multitenant.defaultTenant}")
    String defaultTenant;

    @Override
    public String resolveCurrentTenantIdentifier() {
        try {
            return TenantAccessor.getCurrentTenant().getTenantId();
        } catch (TenantNotAvailableException | TenantAccessException e) {
            logger.warn("Tenant not found", e);
            return defaultTenant;
        }
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
