package com.sap.cloud.s4hana.examples.addressmgr.machinelearning;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;

import java.net.URI;
import java.util.Map;

public class MlService {
    public static final String ML_API_KEY = "mlApiKey";
    public static final String ML_DESTINATION_NAME = "mlApi";

    public String getMlApiKeyOrThrow() {
        Map<String, String> destinationProperties = getMlDestination().getPropertiesByName();
        if (destinationProperties.containsKey(ML_API_KEY)) {
            return destinationProperties.get(ML_API_KEY);
        }
        throw new IllegalStateException(ML_API_KEY + " destination property not present, but required. " +
                "Get yours at the api.sap.com website.");
    }

    public Destination getMlDestination() {
        return DestinationAccessor.getDestination(ML_DESTINATION_NAME);
    }

    public URI getLeonardoMlSandboxUrlOrThrow() {
        final URI sandboxUrl = getMlDestination().getUri();
        return sandboxUrl;
    }
}
