package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.sap.cloud.s4hana.examples.addressmgr.machine_learning.LanguageDetectServlet;
import com.sap.cloud.s4hana.examples.addressmgr.machine_learning.TranslateServlet;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;
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
import org.mockito.Mockito;

import java.net.URL;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Arquillian.class)
public class MachineLearningServletsTest {
    private static final MockUtil mockUtil = new MockUtil();

    @ArquillianResource
    private URL baseUrl;

    @Deployment
    public static WebArchive createDeployment() {
        return TestUtil.createDeployment(LanguageDetectServlet.class, TranslateServlet.class);
    }

    @BeforeClass
    public static void beforeClass() {
        mockUtil.mockDefaults();
        final String vcapServicesString = System.getenv("VCAP_SERVICES");
        final Map<String, JsonArray> vcapServicesJson = new Gson().fromJson(vcapServicesString,
                new TypeToken<Map<String, JsonArray>>() {
                }.getType());
        final ScpCfCloudPlatform mockedCloudPlatform = (ScpCfCloudPlatform) mockUtil.mockCurrentCloudPlatform();
        Mockito.when(mockedCloudPlatform.getVcapServices()).thenReturn(vcapServicesJson);
        Mockito.when(mockedCloudPlatform.getEnvironmentVariable("mlServiceType"))
                .thenReturn(Optional.of(System.getenv("mlServiceType")));
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
