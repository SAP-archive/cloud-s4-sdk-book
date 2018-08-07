package com.sap.cloud.s4hana.examples.addressmgr.machinelearning;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import rx.Observable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import com.sap.cloud.s4hana.examples.addressmgr.machinelearning.commands.MlLanguageDetectionCommand;
import com.sap.cloud.s4hana.examples.addressmgr.machinelearning.commands.MlTranslationCommand;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;

@WebServlet("/api/translate")
public class TranslateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(TranslateServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        String input = request.getParameter("input");

        response.setContentType("text/utf-8");
        final PrintWriter responseWriter = response.getWriter();

        try {
            String output = translate(new MlService(), input, "en");
            responseWriter.write("Translation: " + output);
        } catch (Exception e) {
            logger.error("Problem in translation service access", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private String translate(MlService mlService, final String input, final String targetLang) throws Exception {
        Observable<String> result = new MlLanguageDetectionCommand(mlService, input).observe().flatMap(
                mlLanguageDetectionResult -> {
                    final String sourceLang = mlLanguageDetectionResult.getLangCode();
                    if (StringUtils.isBlank(sourceLang)) {
                        logger.debug("Could not translate because language could not be determined: '{}'", input);
                        return Observable.just("");
                    }

                    return new MlTranslationCommand(sourceLang, targetLang, Collections.singletonList(input))
                            .observe()
                            .map(strings -> {
                                if (strings.isEmpty()) {
                                    logger.error("No results from translation 1's input - should not happen");
                                    return "";
                                }
                                return strings.get(0);
                            });
                });
        return result.toBlocking().single();
    }
}
