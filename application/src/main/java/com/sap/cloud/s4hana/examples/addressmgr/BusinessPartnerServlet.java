package com.sap.cloud.s4hana.examples.addressmgr;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

@WebServlet( "/api/business-partners" )
public class BusinessPartnerServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(BusinessPartnerServlet.class);

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}
