package com.sap.cloud.s4hana.examples.addressmgr.messaging;

import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceConnectorConfig;

import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;
import com.sap.cloud.servicesdk.xbem.api.MessagingEndpoint;
import com.sap.cloud.servicesdk.xbem.api.MessagingException;
import com.sap.cloud.servicesdk.xbem.api.MessagingService;
import com.sap.cloud.servicesdk.xbem.connector.sapcp.MessagingServiceInfoProperties;

public class MessageListener
{
    // The name of the binding as specified in the SAP_XBEM_BINDINGS environment variable
    private static final String QUEUE_NAME = "AddressChangeQueue";

    public void startListening()
    {
        final Cloud cloud = new CloudFactory().getCloud();
        final ServiceConnectorConfig config = MessagingServiceInfoProperties.init().finish();
        final MessagingService messagingService = cloud.getSingletonServiceConnector(MessagingService.class, config);
        try {
            final MessagingEndpoint endpoint = messagingService.bind(QUEUE_NAME).build();
            endpoint.receive(new MessageConsumer());
        }
        catch( final MessagingException e ) {
            throw new ShouldNotHappenException(e);
        }
    }

}
