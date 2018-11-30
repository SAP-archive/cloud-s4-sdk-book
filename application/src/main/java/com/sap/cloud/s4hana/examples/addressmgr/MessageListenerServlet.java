package com.sap.cloud.s4hana.examples.addressmgr;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.cloud.s4hana.examples.addressmgr.commands.StartMessageListenerCommand;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;

/**
 * This endpoint starts the contained {@link com.sap.cloud.s4hana.examples.addressmgr.messaging.MessageListener
 * MessageListener}, wrapped in {@link com.sap.cloud.sdk.s4hana.connectivity.ErpCommand ErpCommand} to make it
 * resilient.
 */
@WebServlet( "/startMessaging" )
public class MessageListenerServlet extends HttpServlet
{
    @Override
    protected void doGet( final HttpServletRequest req, final HttpServletResponse resp )
    {
        new StartMessageListenerCommand().execute();
    }
}
