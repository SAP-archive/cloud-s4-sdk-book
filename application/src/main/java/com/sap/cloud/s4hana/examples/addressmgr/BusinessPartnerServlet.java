package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.gson.Gson;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllBusinessPartnersCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleBusinessPartnerByIdCommand;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/business-partners")
public class BusinessPartnerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(BusinessPartnerServlet.class);

    private final BusinessPartnerService service = new DefaultBusinessPartnerService();

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String id = request.getParameter("id");

        final String jsonResult;
        if (id == null) {
            logger.info("Retrieving all business partners");
            final List<BusinessPartner> result = new GetAllBusinessPartnersCommand(service).execute();
            jsonResult = new Gson().toJson(result);
        } else {
            logger.info("Retrieving business partner with id {}", id);
            final BusinessPartner result = new GetSingleBusinessPartnerByIdCommand(service, id).execute();
            jsonResult = new Gson().toJson(result);
        }

        response.setContentType("application/json");
        response.getWriter().write(jsonResult);
    }
}
