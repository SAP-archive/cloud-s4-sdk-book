package com.sap.cloud.s4hana.examples.addressmgr;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllBusinessPartnersCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleBusinessPartnerByIdCommand;
import com.sap.cloud.s4hana.examples.addressmgr.vdm.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.s4hana.examples.addressmgr.vdm.services.BusinessPartnerService;
import com.sap.cloud.s4hana.examples.addressmgr.vdm.services.DefaultBusinessPartnerService;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;

@WebServlet( "/api/business-partners" )
public class BusinessPartnerServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(BusinessPartnerServlet.class);

    private final BusinessPartnerService service = new DefaultBusinessPartnerService();

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        final String id = request.getParameter("id");

        final String jsonResult;
        if( id == null ) {
            logger.info("Retrieving all business partners");
            final List<BusinessPartner> result = new GetAllBusinessPartnersCommand(service).execute();
            jsonResult = new Gson().toJson(result);
        } else {
            if( !validateInput(id) ) {
                logger.warn("Invalid request to retrieve a business partner, id: {}.", id);
                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    String.format(
                        "Invalid business partner ID '%s'. "
                            + "Business partner ID must not be empty or longer than 10 characters.",
                        id));
                return;
            }
            logger.info("Retrieving business partner with id {}.", id);
            final BusinessPartner result = new GetSingleBusinessPartnerByIdCommand(service, id).execute();
            jsonResult = new Gson().toJson(result);
        }

        response.setContentType("application/json");
        response.getWriter().write(jsonResult);
    }

    private boolean validateInput( final String id )
    {
        return !Strings.isNullOrEmpty(id) && id.length() <= 10;
    }
}
