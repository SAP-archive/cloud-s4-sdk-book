package com.sap.cloud.s4hana.examples.addressmgr.service;

public interface TenantProvisioningService {
    void subscribeTenant(String tenantId);

    void unsubscribeTenant(String tenantId);
}
