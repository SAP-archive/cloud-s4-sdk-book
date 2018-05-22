package com.sap.cloud.s4hana.examples.addressmgr;

import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleBusinessPartnerByIdCommand;
import com.sap.cloud.s4hana.examples.addressmgr.models.BusinessPartnerCustomFields;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import com.sap.cloud.sdk.testutil.MockUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith( Arquillian.class )
public class BusinessPartnerServletTest
{
    private static final MockUtil mockUtil = new MockUtil();
    public static final String BUPA_ID = "1003764";

    @ArquillianResource
    private URL baseUrl;

    @Deployment
    public static WebArchive createDeployment()
    {
        return TestUtil.createDeployment(BusinessPartnerServlet.class);
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
    public void testGetAll()
    {
        when()
                .get("/api/business-partners")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(greaterThan(1)))
                .body("[0].BusinessPartner", not(isEmptyOrNullString()));
    }

    @Test
    public void testGetSingle() {
        when()
                .get("/api/business-partners?id={id}", BUPA_ID)
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("BusinessPartner", allOf(not(isEmptyOrNullString()),equalTo(BUPA_ID)))
                .body("YY1_AddrLastCheckedBy_bus", not(isEmptyOrNullString()))
                .body("YY1_AddrLastCheckedOn_bus", not(isEmptyOrNullString()))
                .body("to_BusinessPartnerAddress", hasSize(greaterThan(0)));
    }

    @Test
    public void testUpdateLastChecked() {
        final String userName = String.format("%s@%s.com", RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10));
        final Calendar before = new GregorianCalendar();
        before.set(Calendar.HOUR_OF_DAY, 0);
        before.set(Calendar.MINUTE, 0);
        before.set(Calendar.SECOND, 0);
        before.set(Calendar.MILLISECOND, 0);
        mockUtil.mockUser(userName);
        mockUtil.setCurrentUser(userName);

        given()
            .body("{\"AddressesChecked\": true}")
        .when()
            .patch("/api/business-partners?id={id}", BUPA_ID)
        .then()
            .statusCode(204);

        final BusinessPartner businessPartner = new GetSingleBusinessPartnerByIdCommand(new DefaultBusinessPartnerService(), BUPA_ID)
            .execute();
        assertThat(businessPartner.getCustomField(BusinessPartnerCustomFields.ADDRESSES_LAST_CHECKED_BY))
            .isEqualTo(userName);
        assertThat(businessPartner.getCustomField(BusinessPartnerCustomFields.ADDRESSES_LAST_CHECKED_ON))
            .isGreaterThanOrEqualTo(before);
    }

    @Test
    public void testUpdateNotChecked() {
        given()
            .body("{}")
        .when()
            .patch("/api/business-partners?id={id}", BUPA_ID)
        .then()
            .statusCode(304);
    }
}
