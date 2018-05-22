package com.sap.cloud.s4hana.examples.addressmgr.controllers;

import com.sap.cloud.s4hana.examples.addressmgr.service.TenantProvisioningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RestController
@RequestScope
@RequestMapping(path = "/api/callback/tenant")
public class TenantProvisioningController {
    private static final Logger logger = LoggerFactory.getLogger(TenantProvisioningController.class);

    @Autowired
    TenantProvisioningService tenantProvisioningService;

    @PutMapping("/{tenantId}")
    public void subscribeTenant(@PathVariable(value = "tenantId") String tenantId) {
        logger.info("Tenant callback service was called with method PUT for tenant {}.", tenantId);
        tenantProvisioningService.subscribeTenant(tenantId);
    }

    @DeleteMapping("/{tenantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribeTenant(@PathVariable(value = "tenantId") String tenantId) {
        logger.info("Tenant callback service was called with method DELETE for tenant {}.", tenantId);
        tenantProvisioningService.unsubscribeTenant(tenantId);
    }
}
