package com.sap.cloud.s4hana.examples.addressmgr.util;

import com.sap.cloud.sdk.cloudplatform.CloudPlatform;
import com.sap.cloud.sdk.cloudplatform.CloudPlatformAccessor;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;
import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;

public class CloudPlatformService {

    public ScpCfCloudPlatform getCloudFoundryOrThrow() {
        final CloudPlatform cloudPlatform = CloudPlatformAccessor.getCloudPlatform();
        if (!(cloudPlatform instanceof ScpCfCloudPlatform)) {
            throw new ShouldNotHappenException("Please run this service only on SAP Cloud Platform, Cloud Foundry.");
        }
        return (ScpCfCloudPlatform) cloudPlatform;
    }
}
