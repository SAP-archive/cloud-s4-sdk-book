package com.sap.cloud.s4hana.examples.addressmgr.blockchain;

import com.google.gson.Gson;
import com.sap.cloud.s4hana.examples.addressmgr.blockchain.commands.GetEmailBlacklistStatusCommand;
import com.sap.cloud.s4hana.examples.addressmgr.util.CloudPlatformService;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.services.scp.blockchain.hyperledgerfabric.FabricService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/blackliststatus")
public class BlacklistGetStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(BlacklistGetStatusServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String email = request.getParameter("email");
        if (StringUtils.isBlank(email)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameter 'email'");
        } else {
            try {
                final BlacklistService blacklistService = new BlacklistService(new CloudPlatformService(),
                        FabricService.create());
                final Map<String, Integer> result = new GetEmailBlacklistStatusCommand(blacklistService, email.trim())
                        .execute();

                response.setContentType("application/json");
                new Gson().toJson(result, response.getWriter());

            } catch (Exception e) {
                logger.error("Failed to read email blacklist status", e);
                response.reset();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Failed to read business partner email blacklist status: " + e.getMessage());
            }
        }
    }
}
