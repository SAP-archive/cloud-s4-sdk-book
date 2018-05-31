package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.common.base.Strings;
import com.sap.cloud.s4hana.examples.addressmgr.machine_learning.LanguageDetectServlet;
import com.sap.cloud.s4hana.examples.addressmgr.machine_learning.TranslateServlet;
import com.sap.cloud.sdk.testutil.MockUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Arquillian.class)
public class MachineLearningServletsTest {
    private static final MockUtil mockUtil = new MockUtil();
    public static final String ML_API_KEY = "ML_API_KEY";

    @ArquillianResource
    private URL baseUrl;

    @Deployment
    public static WebArchive createDeployment() {
        return TestUtil.createDeployment(LanguageDetectServlet.class, TranslateServlet.class);
    }

    @BeforeClass
    public static void beforeClass() {
        mockUtil.mockDefaults();

        Map<String, String> properties = new HashMap<>();
        properties.put("mlApiKey", getMlApiKeyFromEnv());
        mockUtil.mockDestination("mlApi", URI.create("https://sandbox.api.sap.com/ml"),
                null, null, null, null, null, null, true, null, null,
                properties);
    }

    private static String getMlApiKeyFromEnv()
    {
        final String ml_api_key = System.getenv(ML_API_KEY);
        if (Strings.isNullOrEmpty(ml_api_key)) {
            throw new IllegalStateException("Please set " + ML_API_KEY + " environment variable");
        }
        return ml_api_key;
    }

    @Before
    public void before() {
        RestAssured.baseURI = baseUrl.toExternalForm();
    }

    @Test
    public void testGetLanguageDetection() {
        when()
                .get("/api/langdetect?input=Apfelbaum")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("langStr", allOf(not(isEmptyOrNullString()), equalTo("German")));
    }

    @Test
    public void testGetTranslation() {
        String body = when()
                .get("/api/translate?input=Apfelbaum")
                .then()
                .statusCode(200)
                .extract().body().asString();

        assertThat(body).containsIgnoringCase("apple tree");
    }

}
