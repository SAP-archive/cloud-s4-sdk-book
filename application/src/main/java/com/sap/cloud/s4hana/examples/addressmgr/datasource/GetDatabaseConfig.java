package com.sap.cloud.s4hana.examples.addressmgr.datasource;

import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceInfo;


public class GetDatabaseConfig {
    public static <T extends ServiceInfo> T retrieveDatabaseInfo(String serviceId, Class<T> serviceInfoType) {
        return (T) new CloudFactory().getCloud().getServiceInfo(serviceId);
    }
}
