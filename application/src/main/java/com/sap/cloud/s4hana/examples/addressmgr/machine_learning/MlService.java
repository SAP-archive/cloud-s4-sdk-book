package com.sap.cloud.s4hana.examples.addressmgr.machine_learning;

import com.sap.cloud.sdk.cloudplatform.CloudPlatform;
import com.sap.cloud.sdk.cloudplatform.CloudPlatformAccessor;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;
import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;
import com.sap.cloud.sdk.services.scp.machinelearning.CloudFoundryLeonardoMlServiceType;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlFoundation;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlService;
import com.sap.cloud.sdk.services.scp.machinelearning.LeonardoMlServiceType;

import java.util.Optional;

public final class MlService {
    private MlService() {}

    private static final String ML_SERVICE_TYPE_ENVVAR = "mlServiceType";
    private static final CloudFoundryLeonardoMlServiceType DEFAULT_ML_SERVICE_TYPE =
            CloudFoundryLeonardoMlServiceType.TRIAL_BETA;

    public static LeonardoMlService createFromCfServicesConfig(final LeonardoMlServiceType mlServiceType)
            throws ShouldNotHappenException {
        try {
            final LeonardoMlService mlService = LeonardoMlFoundation.create(
                    getMlServiceType(),
                    mlServiceType);
            return mlService;
        } catch (ShouldNotHappenException e) {
            throw e;
        } catch (final Exception e) {
            throw new ShouldNotHappenException("Failed to setup Machine Learning service: " + e.getMessage(), e);
        }
    }

    private static CloudFoundryLeonardoMlServiceType getMlServiceType() {
        final CloudPlatform cloudPlatform = CloudPlatformAccessor.getCloudPlatform();
        if (!(cloudPlatform instanceof ScpCfCloudPlatform)) {
            throw new ShouldNotHappenException("Please run this service only on SAP Cloud Platform, Cloud Foundry.");
        }
        final ScpCfCloudPlatform scpCfCloudPlatform = (ScpCfCloudPlatform) cloudPlatform;
        final Optional<String> mlServiceType = scpCfCloudPlatform.getEnvironmentVariable(ML_SERVICE_TYPE_ENVVAR);
        return mlServiceType.map(val -> CloudFoundryLeonardoMlServiceType.valueOf(val.toUpperCase()))
                .orElse(DEFAULT_ML_SERVICE_TYPE);
    }
}
