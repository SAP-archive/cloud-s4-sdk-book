package com.sap.cloud.s4hana.book.odatatestservice;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sap.cloud.sdk.odatav2.connectivity.ODataDeleteResult;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataUpdateResult;
import com.sap.cloud.sdk.testutil.MockUtil;

import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class ModifyBusinessPartnerAddressTest {
    private static final String BUPA_ID = "1003764";

    private MockUtil mockUtil;

    @Before
    public void setup() {
        mockUtil = new MockUtil();
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    @Test
    public void testCreate() throws ODataException {
        final String streetName = RandomStringUtils.randomAlphabetic(15);
        final BusinessPartnerAddress addressCreated = createBusinessPartnerAddress(streetName);

        final BusinessPartnerAddress addressRetrieved = new DefaultBusinessPartnerService()
                .getBusinessPartnerAddressByKey(addressCreated.getBusinessPartner(), addressCreated.getAddressID())
                .execute();

        assertThat(addressRetrieved, notNullValue());
        assertThat(addressRetrieved.getStreetName(), allOf(not(isEmptyOrNullString()), equalTo(streetName)));
    }

    private BusinessPartnerAddress createBusinessPartnerAddress(final String streetName) throws ODataException {
        final BusinessPartnerAddress addressToCreate = BusinessPartnerAddress.builder()
                .businessPartner(BUPA_ID)
                .streetName(streetName)
                .cityName("Potsdam")
                .build();
        return new DefaultBusinessPartnerService()
                .createBusinessPartnerAddress(addressToCreate)
                .execute();
    }

    @Test(expected = ODataException.class)
    public void testCreateForInvalidBusinessPartner() throws ODataException {
        final BusinessPartnerAddress addressToCreate = BusinessPartnerAddress.builder()
                .businessPartner("1")
                .streetName("Konrad-Zuse-Ring")
                .cityName("Potsdam")
                .build();
        new DefaultBusinessPartnerService().createBusinessPartnerAddress(addressToCreate).execute();
    }

    @Test
    public void testDelete() throws ODataException {
        final BusinessPartnerAddress freshAddress = createBusinessPartnerAddress("Delete Me");
        final BusinessPartnerAddress addressToDelete = BusinessPartnerAddress.builder()
                .businessPartner(freshAddress.getBusinessPartner())
                .addressID(freshAddress.getAddressID())
                .build();

        final ODataDeleteResult deleteResult = new DefaultBusinessPartnerService()
                .deleteBusinessPartnerAddress(addressToDelete)
                .execute();

        assertThat(deleteResult.getHttpStatusCode(), is(204));

        try {
            new DefaultBusinessPartnerService()
                    .getBusinessPartnerAddressByKey(freshAddress.getBusinessPartner(), freshAddress.getAddressID())
                    .execute();
            fail("Deleted address can still be retrieved.");
        } catch (final ODataException e) {
            assertThat(e.getCode(), is("404"));
        }
    }

    @Test(expected = ODataException.class)
    public void testDeleteNonExisting() throws ODataException {
        new DefaultBusinessPartnerService()
                .deleteBusinessPartnerAddress(BusinessPartnerAddress.builder()
                        .businessPartner(BUPA_ID)
                        .addressID("1")
                        .build())
                .execute();
    }

    @Test
    public void testUpdate() throws ODataException {
        final BusinessPartnerAddress freshAddress = createBusinessPartnerAddress("Update Me");
        final String updatedStreetName = "Updated Street Name";
        final BusinessPartnerAddress addressToUpdate = BusinessPartnerAddress.builder()
                .businessPartner(freshAddress.getBusinessPartner())
                .addressID(freshAddress.getAddressID())
                .build();
        addressToUpdate.setStreetName(updatedStreetName);
        final ODataUpdateResult updateResult = new DefaultBusinessPartnerService()
                .updateBusinessPartnerAddress(addressToUpdate)
                .execute();
        assertThat(updateResult.getHttpStatusCode(), is(204));

        final BusinessPartnerAddress addressUpdated = new DefaultBusinessPartnerService()
                .getBusinessPartnerAddressByKey(freshAddress.getBusinessPartner(), freshAddress.getAddressID())
                .execute();

        final String addressUpdatedStreetName = addressUpdated.getStreetName();
        assertThat(addressUpdatedStreetName, not(isEmptyOrNullString()));
        assertThat(addressUpdatedStreetName, equalTo(updatedStreetName));
    }

    @Test
    public void testUpdateWithWorkaround() throws ODataException {
        final BusinessPartnerAddress freshAddress = createBusinessPartnerAddress("Update Me");
        final String updatedStreetName = "Updated Street Name";
        freshAddress.setStreetName(updatedStreetName);
        final ODataUpdateResult updateResult = new DefaultBusinessPartnerService()
                .updateBusinessPartnerAddress(freshAddress)
                .execute();
        assertThat(updateResult.getHttpStatusCode(), is(204));

        final BusinessPartnerAddress addressUpdated = new DefaultBusinessPartnerService()
                .getBusinessPartnerAddressByKey(freshAddress.getBusinessPartner(), freshAddress.getAddressID())
                .execute();

        final String addressUpdatedStreetName = addressUpdated.getStreetName();
        assertThat(addressUpdatedStreetName, not(isEmptyOrNullString()));
        assertThat(addressUpdatedStreetName, equalTo(updatedStreetName));
    }

    @Test(expected = ODataException.class)
    public void testUpdateNonExisting() throws ODataException {
        new DefaultBusinessPartnerService()
                .updateBusinessPartnerAddress(BusinessPartnerAddress.builder()
                        .businessPartner(BUPA_ID)
                        .addressID("1")
                        .build())
                .execute();
    }
}
