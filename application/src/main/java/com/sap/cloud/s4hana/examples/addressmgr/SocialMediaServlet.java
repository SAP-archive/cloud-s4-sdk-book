package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.gson.Gson;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllSocialMediaAccountsCommand;
import com.sap.cloud.s4hana.examples.addressmgr.custom.namespaces.bupasocialmedia.SocialMediaAccount;
import com.sap.cloud.s4hana.examples.addressmgr.custom.services.DefaultBusinessPartnerSocialMediaService;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/social-media-accounts")
public class SocialMediaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(SocialMediaServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String id = request.getParameter("id");

        if (!BusinessPartnerServlet.validateInput(id)) {
            logger.warn("Invalid request to retrieve social media accounts of a business partner, id: {}.", id);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    String.format("Invalid business partner ID '%s'. " +
                                    "Business partner ID must not be empty or longer than 10 characters.",
                            id));
            return;
        }

        logger.info("Retrieving social media accounts of business partner with id {}", id);
        final List<SocialMediaAccount> result = new GetAllSocialMediaAccountsCommand(
                new DefaultBusinessPartnerSocialMediaService(), id)
                .execute();
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(result));
    }
}
