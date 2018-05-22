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
        mockUtil.mockErpDestination();
    }

    @Before
    public void before()
    {
        RestAssured.baseURI = baseUrl.toExternalForm();
    }

    @Test
    public void testCreate() throws ODataException {
        final String houseNumber = String.valueOf(new Random().nextInt(100));

        final String newAddressId = createAddress(houseNumber);

        // Verify newly created address can be retrieved
        final BusinessPartnerAddress newBusinessPartnerAddress = getAddress(BUPA_ID, newAddressId);
        assertThat(newBusinessPartnerAddress.getHouseNumber()).isEqualTo(houseNumber);
    }

    /**
     * Creates a new address via the servlet, validates response with RestAssured, and
     * returns ID of new address.
     * @param houseNumber Value to set for property HouseNumber
     * @return Value of property AddressID of newly created instance
     */
    private String createAddress(final String houseNumber) {
        return given()
                .body(CREATE_BODY_TEMPLATE
                        .replace("{bupaId}", BUPA_ID)
                        .replace("{houseNumber}", houseNumber)
                )
        .when()
                .post("/api/addresses")
        .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("BusinessPartner", equalTo(BUPA_ID))
                .body("AddressID", not(isEmptyOrNullString()))
        .extract()
                .path("AddressID");
    }

    @Test
    public void testDelete() {
        // Create address to delete afterwards
        final String addressId = createAddress("10");

        // Delete the address
        when()
                .delete("/api/addresses?businessPartnerId={bupaId}&addressId={addressId}",
                        BUPA_ID,
                        addressId)
        .then()
                .statusCode(204);

        // Verify just deleted address cannot be found anymore
        assertThat(getAddress(BUPA_ID, addressId)).isNull();
    }

    @Test
    public void testUpdate() throws ODataException {
        // Create address to update
        final String addressId = createAddress("10");

        given()
                .body(UPDATE_BODY_TEMPLATE.replace("{houseNumber}", "100"))
        .when()
                .patch("/api/addresses?businessPartnerId={bupaId}&addressId={addressId}",
                        BUPA_ID,
                        addressId)
        .then()
                .statusCode(204);

        // Verify that address contains new value also when retrieved again
        final BusinessPartnerAddress addressUpdated = getAddress(BUPA_ID, addressId);
        assertThat(addressUpdated.getHouseNumber()).isEqualTo("100");
    }

    private BusinessPartnerAddress getAddress(final String bupaId, final String addressId) {
        return new GetAddressCommand(bupaId, addressId).execute();
    }
}
