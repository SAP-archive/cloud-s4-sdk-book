package com.sap.cloud.s4hana.book.odatatestservice;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.testutil.MockUtil;

import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class ReadBusinessPartnerAddressTest {
    private static final String BUPA_ID = "1003764";
    private static final String ADDRESS_ID = "28238";

    private MockUtil mockUtil;

    @Before
    public void setup() {
        mockUtil = new MockUtil();
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    @Test
    public void testGetAllPlain() throws ODataException {
        final List<BusinessPartnerAddress> addresses = new DefaultBusinessPartnerService()
                .getAllBusinessPartnerAddress()
                .execute();

        verifyListMatchesSize(addresses, greaterThan(1));

        assertForAll_BupaNonNull_IdNonNull(addresses);
    }

    private void assertForAll_BupaNonNull_IdNonNull(final List<BusinessPartnerAddress> addresses) {
        for(final BusinessPartnerAddress address : addresses) {
            assertThat(address.getBusinessPartner(), not(isEmptyOrNullString()));
            assertThat(address.getAddressID(), not(isEmptyOrNullString()));
        }
    }

    private void verifyListMatchesSize(List<?> businessPartners, Matcher<Integer> sizeMatcher) {
        assertThat(businessPartners, allOf(notNullValue(), not(empty())));
        assertThat(businessPartners, hasSize(sizeMatcher));
    }

    private int sizeOfUnfilteredResult() throws ODataException {
        return new DefaultBusinessPartnerService()
                .getAllBusinessPartnerAddress()
                .execute().size();
    }

    @Test
    public void testGetAllWithSelect() throws ODataException {
        final List<BusinessPartnerAddress> addresses = new DefaultBusinessPartnerService()
                .getAllBusinessPartnerAddress()
                .select(BusinessPartnerAddress.STREET_NAME, BusinessPartnerAddress.CITY_NAME)
                .execute();

        verifyListMatchesSize(addresses, greaterThan(1));

        assertForAll_IdNull_StreetNonNull_CityNonNull(addresses);
    }

    private void assertForAll_IdNull_StreetNonNull_CityNonNull(List<BusinessPartnerAddress> addresses) {
        for(final BusinessPartnerAddress address : addresses) {
            assert_IdNull_StreetNonNull_CityNonNull(address);
        }
    }

    private void assert_IdNull_StreetNonNull_CityNonNull(BusinessPartnerAddress address) {
        assertThat(address.getBusinessPartner(), nullValue());
        assertThat(address.getAddressID(), nullValue());
        assertThat(address.getStreetName(), not(isEmptyOrNullString()));
        assertThat(address.getCityName(), not(isEmptyOrNullString()));
    }

    @Test
    public void testGetAllWithFilter() throws ODataException {
        final List<BusinessPartnerAddress> addresses = new DefaultBusinessPartnerService()
                .getAllBusinessPartnerAddress()
                .filter(BusinessPartnerAddress.BUSINESS_PARTNER.eq(BUPA_ID))
                .execute();

        verifyListMatchesSize(addresses, lessThan(sizeOfUnfilteredResult()));

        assertForAll_BupaIdAsExpected(addresses, BUPA_ID);
    }

    private void assertForAll_BupaIdAsExpected(List<BusinessPartnerAddress> addresses, final String expectedBupaId) {
        assertThat(addresses, everyItem(new CustomTypeSafeMatcher<BusinessPartnerAddress>("an address for business partner "+ expectedBupaId) {
            @Override
            protected boolean matchesSafely(BusinessPartnerAddress item) {
                final String firstName = item.getBusinessPartner();
                return firstName != null && firstName.equals(expectedBupaId);
            }
        }));
    }

    @Test
    public void testGetByKeyPlain() throws ODataException {
        final BusinessPartnerAddress address = new DefaultBusinessPartnerService()
                .getBusinessPartnerAddressByKey(BUPA_ID, ADDRESS_ID)
                .execute();

        assertThat(address, notNullValue());
        assertThat(address.getBusinessPartner(), equalTo(BUPA_ID));
        assertThat(address.getAddressID(), equalTo(ADDRESS_ID));
        assertThat(address.getCityName(), not(isEmptyOrNullString()));
    }

    @Test
    public void testGetByKeyWithSelect() throws ODataException {
        final BusinessPartnerAddress address = new DefaultBusinessPartnerService()
                .getBusinessPartnerAddressByKey(BUPA_ID, ADDRESS_ID)
                .select(BusinessPartnerAddress.CITY_NAME, BusinessPartnerAddress.STREET_NAME)
                .execute();

        assertThat(address, notNullValue());
        assert_IdNull_StreetNonNull_CityNonNull(address);
    }
}
