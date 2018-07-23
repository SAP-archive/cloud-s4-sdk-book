package com.sap.cloud.s4hana.book.odatatestservice;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataUpdateResult;
import com.sap.cloud.sdk.testutil.MockUtil;

import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ModifyBusinessPartnerTest {
    private MockUtil mockUtil;

    @Before
    public void setup() {
        mockUtil = new MockUtil();
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    @Test
    public void testCreate() throws ODataException {
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final BusinessPartner businessPartnerCreated = createBusinessPartner(firstName);

        final BusinessPartner businessPartnerRetrieved = new DefaultBusinessPartnerService()
                .getBusinessPartnerByKey(businessPartnerCreated.getBusinessPartner())
                .execute();

        assertThat(businessPartnerRetrieved, notNullValue());
        assertThat(businessPartnerRetrieved.getFirstName(), allOf(not(isEmptyOrNullString()), equalTo(firstName)));
    }

    private BusinessPartner createBusinessPartner(final String firstName) throws ODataException {
        final BusinessPartner businessPartnerToCreate = BusinessPartner.builder()
                .firstName(firstName)
                .lastName("Doe")
                .businessPartnerFullName(firstName + " Doe")
                .businessPartnerCategory("1")
                .isFemale(true)
                .build();
        return new DefaultBusinessPartnerService()
                .createBusinessPartner(businessPartnerToCreate)
                .execute();
    }

    @Test
    public void testUpdate() throws ODataException {
        final BusinessPartner freshBusinessPartner = createBusinessPartner("Update Me");
        final String updatedFirstName = "Updated First Name";
        final BusinessPartner businessPartnerToUpdate = BusinessPartner.builder()
                .businessPartner(freshBusinessPartner.getBusinessPartner())
                .build();
        businessPartnerToUpdate.setFirstName(updatedFirstName);
        final ODataUpdateResult updateResult = new DefaultBusinessPartnerService()
                .updateBusinessPartner(businessPartnerToUpdate)
                .execute();
        assertThat(updateResult.getHttpStatusCode(), is(204));

        final BusinessPartner businessPartnerUpdated = new DefaultBusinessPartnerService()
                .getBusinessPartnerByKey(freshBusinessPartner.getBusinessPartner())
                .execute();

        final String businessPartnerUpdatedFirstName = businessPartnerUpdated.getFirstName();
        assertThat(businessPartnerUpdatedFirstName, not(isEmptyOrNullString()));
        assertThat(businessPartnerUpdatedFirstName, equalTo(updatedFirstName));
    }

    @Test
    public void testUpdateWithWorkaround() throws ODataException {
        final BusinessPartner freshBusinessPartner = createBusinessPartner("Update Me");
        final String updatedFirstName = "Updated First Name";

        // Reuse complete entity to work around bug in SAP S/4HANA Cloud SDK
        freshBusinessPartner.setFirstName(updatedFirstName);
        final ODataUpdateResult updateResult = new DefaultBusinessPartnerService()
                .updateBusinessPartner(freshBusinessPartner)
                .execute();
        assertThat(updateResult.getHttpStatusCode(), is(204));

        final BusinessPartner businessPartnerUpdated = new DefaultBusinessPartnerService()
                .getBusinessPartnerByKey(freshBusinessPartner.getBusinessPartner())
                .execute();

        final String businessPartnerUpdatedFirstName = businessPartnerUpdated.getFirstName();
        assertThat(businessPartnerUpdatedFirstName, not(isEmptyOrNullString()));
        assertThat(businessPartnerUpdatedFirstName, equalTo(updatedFirstName));
    }

    @Test(expected = ODataException.class)
    public void testUpdateNonExisting() throws ODataException {
        new DefaultBusinessPartnerService()
                .updateBusinessPartner(BusinessPartner.builder()
                        .businessPartner("1")
                        .firstName("Test")
                        .build())
                .execute();
    }
}
