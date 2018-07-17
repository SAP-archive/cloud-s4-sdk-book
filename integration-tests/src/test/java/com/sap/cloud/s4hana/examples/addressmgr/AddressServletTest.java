package com.sap.cloud.s4hana.examples.addressmgr;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import com.sap.cloud.sdk.cloudplatform.connectivity.ProxyConfiguration;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.testutil.MockUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static com.sap.cloud.s4hana.examples.addressmgr.BusinessPartnerServletTest.BUPA_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith( Arquillian.class )
public class AddressServletTest
{
    private static final MockUtil mockUtil = new MockUtil();
    private static final String CREATE_BODY_TEMPLATE =
            "{\n" +
            "  \"BusinessPartner\": \"{bupaId}\",\n" +
            "  \"CityName\": \"Potsdam\",\n" +
            "  \"Country\": \"DE\",\n" +
            "  \"HouseNumber\": \"{houseNumber}\",\n" +
            "  \"PostalCode\": \"14469\",\n" +
            "  \"StreetName\": \"Konrad-Zuse-Ring\"\n" +
            "}";
    private static final String UPDATE_BODY_TEMPLATE =
            "{\n" +
            "  \"CityName\": \"Potsdam\",\n" +
            "  \"Country\": \"DE\",\n" +
            "  \"HouseNumber\": \"{houseNumber}\",\n" +
            "  \"PostalCode\": \"14469\",\n" +
            "  \"StreetName\": \"Konrad-Zuse-Ring\"\n" +
            "}";

    @ArquillianResource
    private URL baseUrl;

    @Deployment
    public static WebArchive createDeployment()
    {
        return TestUtil.createDeployment(AddressServlet.class);
    }

    @BeforeClass
    public static void beforeClass()
    {
        mockUtil.mockDefaults();
    }

    @Before
    public void before()
    {
        RestAssured.baseURI = baseUrl.toExternalForm();
    }

    @Test
    public void testCreate() throws ODataException {
        final String body = given().get("/api/addresses").body().asString();
        assertThat(body).isNotEmpty();
    }

    @Test
    public void testDelete() {
        // Create address to delete afterwards
        final String addressId = "10";

        // Delete the address
        when()
                .delete("/api/addresses?businessPartnerId={bupaId}&addressId={addressId}",
                        BUPA_ID,
                        addressId)
                .then().body(not(isEmptyOrNullString()));
    }

    @Test
    public void testUpdate() throws ODataException {
        // Create address to update
        final String addressId = "10";

        given()
                .body(UPDATE_BODY_TEMPLATE.replace("{houseNumber}", "100"))
        .when()
                .patch("/api/addresses?businessPartnerId={bupaId}&addressId={addressId}",
                        BUPA_ID,
                        addressId)
                .then().body(not(isEmptyOrNullString()));
    }

}
