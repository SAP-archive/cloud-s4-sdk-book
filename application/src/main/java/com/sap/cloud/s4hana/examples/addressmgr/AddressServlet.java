package com.sap.cloud.s4hana.examples.addressmgr;

import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

@WebServlet( "/api/addresses" )
public class AddressServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(AddressServlet.class);

    @Override
    protected void doPost( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

    @Override
    protected void doDelete( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

    @Override
    protected void doPatch( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}
