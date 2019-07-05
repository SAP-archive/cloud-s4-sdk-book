package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllBusinessPartnersCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleBusinessPartnerByIdCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.MarkBusinessPartnerAsCheckedCommand;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.cloudplatform.security.user.UserAccessor;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
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
            if (!validateInput(id)) {
                logger.warn("Invalid request to retrieve a business partner, id: {}.", id);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        String.format("Invalid business partner ID. " +
                                        "Business partner ID must not be empty or longer than 10 characters."));
                return;
            }
            logger.info("Retrieving business partner with id {}", id);
            final BusinessPartner result = new GetSingleBusinessPartnerByIdCommand(service, id).execute();
            jsonResult = new Gson().toJson(result);
        }

        response.setContentType("application/json");
        response.getWriter().write(jsonResult);
    }

    static boolean validateInput(String id) {
        return !Strings.isNullOrEmpty(id) && id.length() <= 10;
    }

    @Override
    protected void doPatch(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {
        final String id = request.getParameter("id");

        if (!getInputFromBody(request).addressesChecked) {
            logger.warn("Received request for business partner {} with addresses not marked checked", id);
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        logger.info("Received request to mark addresses of business partner {} as checked", id);
        // requires authentication
        new MarkBusinessPartnerAsCheckedCommand(service,
                id, UserAccessor.getCurrentUser().getName(), LocalDateTime.now())
                .execute();

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private MarkAddressesCheckedInput getInputFromBody(final HttpServletRequest req)
            throws IOException {
        final String body = CharStreams.toString(new InputStreamReader(req.getInputStream(), Charsets.UTF_8));
        logger.info("Converting body {} to input", body);
        return new Gson().fromJson(body, MarkAddressesCheckedInput.class);
    }

    private static class MarkAddressesCheckedInput {
        @SerializedName("AddressesChecked")
        private boolean addressesChecked = false;
    }
}
