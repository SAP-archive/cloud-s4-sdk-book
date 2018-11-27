package com.sap.cloud.s4hana.examples.addressmgr.commands;

import com.sap.cloud.s4hana.examples.addressmgr.messaging.MessageListener;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;

import com.sap.cloud.sdk.s4hana.connectivity.ErpCommand;

public class StartMessageListenerCommand extends ErpCommand<Void>
{
    public StartMessageListenerCommand()
    {
        super(
            HystrixUtil
                .getDefaultErpCommandSetter(
                    StartMessageListenerCommand.class,
                    HystrixUtil.getDefaultErpCommandProperties().withExecutionTimeoutInMilliseconds(10000)));
    }

    @Override
    protected Void run()
    {
        new MessageListener().startListening();
        return null;
    }
}
