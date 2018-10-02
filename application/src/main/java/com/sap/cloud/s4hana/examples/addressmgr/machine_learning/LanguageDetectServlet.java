package com.sap.cloud.s4hana.examples.addressmgr.machine_learning;

import com.google.gson.Gson;
import com.sap.cloud.s4hana.examples.addressmgr.machine_learning.commands.MlLanguageDetectionCommand;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/langdetect")
public class LanguageDetectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(LanguageDetectServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final PrintWriter responseWriter = response.getWriter();

        String input = request.getParameter("input");
        if (StringUtils.isBlank(input)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameter 'input'");
            return;
        }

        try {
            MlLanguageDetectionResult output = new MlLanguageDetectionCommand(
                    MlService.createFromCfServicesConfig(MlServiceType.LANGUAGE_DETECTION), input).execute();
            response.setContentType("application/json");
            responseWriter.write(new Gson().toJson(output));

        } catch (Exception e) {
            logger.error("Problem in language detection service access", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
