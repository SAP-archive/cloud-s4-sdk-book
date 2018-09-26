package com.sap.cloud.s4hana.examples.addressmgr.machine_learning;

import com.sap.cloud.sdk.cloudplatform.CloudPlatform;
import com.sap.cloud.sdk.cloudplatform.CloudPlatformAccessor;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;
import com.sap.cloud.sdk.cloudplatform.connectivity.ScpCfService;
import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;
import org.apache.http.client.methods.HttpPost;

import java.util.Optional;

public class MlService {
    private static final String ML_SERVICE_TYPE_ENVVAR = "mlServiceType";
    private static final String DEFAULT_ML_SERVICE_TYPE = "ml-foundation-trial-beta";

    private final ScpCfService cfService;

    public MlService(final ScpCfService cfService) {
        this.cfService = cfService;
    }

    public static MlService createFromCfServicesConfig(final MlServiceType mlServiceType) throws ShouldNotHappenException {
        try {
            final ScpCfService cfService = ScpCfService.of(getMlServiceType(), null,
                    "credentials/url", "credentials/clientid",
                    "credentials/clientsecret",
                    "credentials/serviceurls/" + mlServiceType.getServiceUrlPathSuffix());

            return new MlService(cfService);
        } catch (ShouldNotHappenException e) {
            throw e;
        } catch (final Exception e) {
            throw new ShouldNotHappenException("Failed to setup Machine Learning service: " + e.getMessage(), e);
        }
    }

    private static String getMlServiceType() {
        final CloudPlatform cloudPlatform = CloudPlatformAccessor.getCloudPlatform();
        if (!(cloudPlatform instanceof ScpCfCloudPlatform)) {
            throw new ShouldNotHappenException("Please run this service only on SAP Cloud Platform, Cloud Foundry.");
        }
        final ScpCfCloudPlatform scpCfCloudPlatform = (ScpCfCloudPlatform) cloudPlatform;
        final Optional<String> mlServiceType = scpCfCloudPlatform.getEnvironmentVariable(ML_SERVICE_TYPE_ENVVAR);
        return mlServiceType.orElse(DEFAULT_ML_SERVICE_TYPE);
    }

    public HttpPost createPostRequest(final String apiPathEnding) {
        final HttpPost httpPost = new HttpPost(cfService.getServiceLocationInfo() + apiPathEnding);
        cfService.addBearerTokenHeader(httpPost);
        return httpPost;
    }

    public HttpPost createPostRequest() {
        return createPostRequest("");
    }

    @Override
    public String toString() {
        return "MlService(" +
                "cfService=" + cfService +
                "}";
    }
}
