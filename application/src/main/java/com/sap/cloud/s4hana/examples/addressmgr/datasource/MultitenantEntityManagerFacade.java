package com.sap.cloud.s4hana.examples.addressmgr.datasource;

import org.springframework.cloud.service.common.PostgresqlServiceInfo;

import java.util.Map;

import com.sap.cloud.s4hana.examples.addressmgr.config.SchemaPerTenantConnectionProvider;
import com.sap.cloud.s4hana.examples.addressmgr.config.TenantIdentifierResolver;

public class MultitenantEntityManagerFacade extends AbstractEntityManagerFacade {
    private static MultitenantEntityManagerFacade facade;
    private MultitenantEntityManagerFacade() {}

    @Override
    protected Map<String, String> buildProperties(final PostgresqlServiceInfo postgreInfo) {
        final Map<String, String> properties = super.buildProperties(postgreInfo);

        properties.put("hibernate.multiTenancy", "SCHEMA");
        properties.put("hibernate.tenant_identifier_resolver", TenantIdentifierResolver.class.getCanonicalName());
        properties.put("hibernate.multi_tenant_connection_provider", SchemaPerTenantConnectionProvider.class.getCanonicalName());
        return properties;
    }
    public static MultitenantEntityManagerFacade getInstance() {
        if(facade==null) {
            facade = new MultitenantEntityManagerFacade();
        }
        return facade;
    }
}
