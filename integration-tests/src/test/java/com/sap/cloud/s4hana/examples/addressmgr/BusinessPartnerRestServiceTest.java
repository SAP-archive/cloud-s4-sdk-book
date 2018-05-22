package com.sap.cloud.s4hana.examples.addressmgr;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.swagger.model.Address;
import io.swagger.model.AddressResponse;
import io.swagger.model.BusinessPartnerResponse;
import io.swagger.model.BusinessPartnersResponse;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import com.sap.cloud.s4hana.examples.addressmgr.api.AddressRestService;
import com.sap.cloud.s4hana.examples.addressmgr.api.BusinessPartnerRestService;
import com.sap.cloud.s4hana.examples.addressmgr.api.ExceptionMapper;
import com.sap.cloud.sdk.testutil.MockUtil;

import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(Arquillian.class)
public class BusinessPartnerRestServiceTest {

    @ArquillianResource
    private URL baseUrl;
    private static final String basePath = "/api/v1/rest";

    private static final MockUtil mockUtil = new MockUtil();
    public static final String BUPA_ID = "1003764";

    @Deployment
    public static WebArchive createDeployment() {
        return TestUtil.createDeployment(
                BusinessPartnerRestService.class,
                BusinessPartnersResponse.class,
                BusinessPartnerResponse.class,
                ExceptionMapper.class);
    }

    @BeforeClass
    public static void beforeClass() {
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    @Before
    public void before() {
        RestAssured.baseURI = baseUrl.toExternalForm();
    }

    @Test
    public void testGetAll() {

        final String result =
                when()
                        .get(basePath + "/business-partners")
                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .jsonPath()
                        .getJsonObject("value[0].id");
        assertThat(result).isNotNull().isNotEmpty();
    }

    @Test
    public void testNotFound() {
        when().get(basePath + "/business-partners/noBP").then().statusCode(404).contentType(ContentType.JSON);
    }

    @Test
    public void testGetSingle() {
        final String result =
                when()
                        .get(basePath + "/business-partners/" + BUPA_ID)
                        .then()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getString("value.id");
        assertThat(result).isEqualTo(BUPA_ID);
    }
}
