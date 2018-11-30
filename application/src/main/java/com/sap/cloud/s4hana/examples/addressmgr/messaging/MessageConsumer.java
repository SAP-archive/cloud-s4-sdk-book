package com.sap.cloud.s4hana.examples.addressmgr.messaging;

import java.util.function.Consumer;
import java.util.stream.Stream;

import org.slf4j.Logger;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.servicesdk.xbem.api.Message;

public class MessageConsumer implements Consumer<Stream<Message<byte[]>>>
{
    private static final Logger logger = CloudLoggerFactory.getLogger(MessageConsumer.class);

    @Override
    public void accept( final Stream<Message<byte[]>> messageStream )
    {
        // replace with your custom message/event handling logic
        messageStream.map(Message::getContent).map(String::new).forEach(logger::info);
    }
}
