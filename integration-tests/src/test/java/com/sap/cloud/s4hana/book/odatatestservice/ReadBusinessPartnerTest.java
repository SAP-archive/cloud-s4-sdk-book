package com.sap.cloud.s4hana.book.odatatestservice;

import com.sap.cloud.sdk.odatav2.connectivity.ODataException;

import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

import com.sap.cloud.sdk.testutil.MockUtil;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ReadBusinessPartnerTest {
    private static final String BUPA_ID = "1003764";
    private static final String EXPECTED_FIRST_NAME = "John";

    private MockUtil mockUtil;

    @Before
    public void setup() {
        mockUtil = new MockUtil();
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    @Test
    public void testGetAllPlain() throws ODataException {
        final List<BusinessPartner> businessPartners = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .execute();

        verifyListMatchesSize(businessPartners, greaterThan(1));

        assertForAll_IdNonNull_CategoryNonNull(businessPartners);
    }

    private void assertForAll_IdNonNull_CategoryNonNull(final List<BusinessPartner> businessPartners) {
        for (final BusinessPartner businessPartner : businessPartners) {
            assertThat(businessPartner.getBusinessPartner(), not(isEmptyOrNullString()));
            assertThat(businessPartner.getBusinessPartnerCategory(), not(isEmptyOrNullString()));
        }
    }

    private void verifyListMatchesSize(List<BusinessPartner> businessPartners, Matcher<Integer> sizeMatcher) {
        assertThat(businessPartners, allOf(notNullValue(), not(empty())));
        assertThat(businessPartners, hasSize(sizeMatcher));
    }

    private int sizeOfUnfilteredResult() throws ODataException {
        return new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .execute().size();
    }

    @Test
    public void testGetAllWithSelect() throws ODataException {
        final List<BusinessPartner> businessPartners = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .select(BusinessPartner.BUSINESS_PARTNER_FULL_NAME, BusinessPartner.CREATED_BY_USER)
                .execute();

        verifyListMatchesSize(businessPartners, greaterThan(1));

        assertForAll_IdNull_FullNameNonNull_CreatedByNonNull(businessPartners);
    }

    @Test
    public void testGetAllWithSelectAllFields() throws ODataException {
        final List<BusinessPartner> businessPartners = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .select(BusinessPartner.ALL_FIELDS)
                .execute();

        verifyListMatchesSize(businessPartners, greaterThan(1));

        assertForAll_IdNonNull_CategoryNonNull(businessPartners);
    }

    private void assertForAll_IdNull_FullNameNonNull_CreatedByNonNull(List<BusinessPartner> businessPartners) {
        for (final BusinessPartner businessPartner : businessPartners) {
            assert_IdNull_FullNameNonNull_CreatedByNonNull(businessPartner);
        }
    }

    private void assert_IdNull_FullNameNonNull_CreatedByNonNull(BusinessPartner businessPartner) {
        assertThat(businessPartner.getBusinessPartner(), nullValue());
        assertThat(businessPartner.getBusinessPartnerFullName(), not(isEmptyOrNullString()));
        assertThat(businessPartner.getCreatedByUser(), not(isEmptyOrNullString()));
    }

    @Test
    public void testGetAllWithFilter() throws ODataException {
        final List<BusinessPartner> businessPartners = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .filter(BusinessPartner.FIRST_NAME.eq(EXPECTED_FIRST_NAME))
                .execute();

        verifyListMatchesSize(businessPartners, lessThan(sizeOfUnfilteredResult()));

        assertForAll_FirstNameAsExpected(businessPartners, EXPECTED_FIRST_NAME);
    }

    private void assertForAll_FirstNameAsExpected(List<BusinessPartner> businessPartners, final String expectedFirstName) {
        assertThat(businessPartners, everyItem(new CustomTypeSafeMatcher<BusinessPartner>("a business partner with first name " + expectedFirstName) {
            @Override
            protected boolean matchesSafely(BusinessPartner item) {
                final String firstName = item.getFirstName();
                return firstName != null && firstName.equals(expectedFirstName);
            }
        }));
    }

    @Test
    public void testGetAllWithSelectAndFilter() throws ODataException {
        final List<BusinessPartner> businessPartners = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .select(BusinessPartner.BUSINESS_PARTNER_FULL_NAME, BusinessPartner.FIRST_NAME, BusinessPartner.CREATED_BY_USER)
                .filter(BusinessPartner.FIRST_NAME.eq(EXPECTED_FIRST_NAME))
                .execute();

        verifyListMatchesSize(businessPartners, lessThan(sizeOfUnfilteredResult()));

        assertForAll_IdNull_FullNameNonNull_CreatedByNonNull(businessPartners);
        assertForAll_FirstNameAsExpected(businessPartners, EXPECTED_FIRST_NAME);
    }

    @Test
    public void testGetAllWithSelectAndFilterNotPartOfSelect() throws ODataException {
        final List<BusinessPartner> businessPartners = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .select(BusinessPartner.BUSINESS_PARTNER_CATEGORY, BusinessPartner.CREATED_BY_USER)
                .filter(BusinessPartner.FIRST_NAME.eq(EXPECTED_FIRST_NAME))
                .execute();

        verifyListMatchesSize(businessPartners, lessThan(sizeOfUnfilteredResult()));
    }

    @Test
    public void testGetAllWithExpand() throws ODataException {
        final List<BusinessPartner> businessPartners = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .select(BusinessPartner.BUSINESS_PARTNER, BusinessPartner.TO_BUSINESS_PARTNER_ADDRESS)
                .execute();

        verifyListMatchesSize(businessPartners, greaterThan(1));

        for (final BusinessPartner businessPartner : businessPartners) {
            assert_IdNonNull_ToAddressNonNull(businessPartner);
        }
    }

    @Test
    public void testGetAllWithExpandMultiple() throws ODataException {
        final List<BusinessPartner> businessPartners = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .select(BusinessPartner.BUSINESS_PARTNER,
                        BusinessPartner.TO_BUSINESS_PARTNER_ADDRESS.select(BusinessPartnerAddress.ADDRESS_ID,
                                BusinessPartnerAddress.TO_EMAIL_ADDRESS),
                        BusinessPartner.TO_BUSINESS_PARTNER_ROLE)
                .execute();

        verifyListMatchesSize(businessPartners, greaterThan(1));

        for (final BusinessPartner businessPartner : businessPartners) {
            assert_IdNonNull_ToAddressNonNull(businessPartner);
            assertThat(businessPartner.getBusinessPartnerRoleIfPresent().orElse(null), notNullValue());
            assertThat(businessPartner.getBusinessPartnerContactIfPresent().orElse(null), nullValue());
            for (final BusinessPartnerAddress address : businessPartner.getBusinessPartnerAddressIfPresent().orElse(Collections.emptyList())) {
                assertThat(address.getAddressID(), not(isEmptyOrNullString()));
                assertThat(address.getEmailAddressIfPresent().orElse(null), notNullValue());
            }
        }
    }

    @Test
    public void testGetAllWithExpandSpecificFields() throws ODataException {
        final List<BusinessPartner> businessPartners = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .select(BusinessPartner.BUSINESS_PARTNER, BusinessPartner.TO_BUSINESS_PARTNER_ADDRESS
                        .select(BusinessPartnerAddress.ADDRESS_ID, BusinessPartnerAddress.STREET_NAME))
                .execute();

        verifyListMatchesSize(businessPartners, greaterThan(1));

        for (final BusinessPartner businessPartner : businessPartners) {
            assert_IdNonNull_ToAddressNonNull(businessPartner);
            for (final BusinessPartnerAddress address : businessPartner.getBusinessPartnerAddressIfPresent().orElse(Collections.emptyList())) {
                assertThat(address.getAddressID(), not(isEmptyOrNullString()));
                assertThat(address.getStreetName(), not(isEmptyOrNullString()));
                assertThat(address.getHouseNumber(), isEmptyOrNullString());
            }
        }
    }

    private void assert_IdNonNull_ToAddressNonNull(final BusinessPartner businessPartner) {
        assertThat(businessPartner.getBusinessPartner(), not(isEmptyOrNullString()));
        assertThat(businessPartner.getBusinessPartnerAddressIfPresent().orElse(null), notNullValue());
    }

    @Test
    public void testGetAllWithOrderByAsc() throws ODataException {
        List<BusinessPartner> listAsc = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .select(BusinessPartner.FIRST_NAME)
                .orderBy(BusinessPartner.FIRST_NAME, Order.ASC)
                .execute();

        for(int i = 1; i < listAsc.size(); i++) {
            assertTrue("List is not sorted: " + listAsc.toString(),
                    listAsc.get(i-1).getFirstName().compareTo(listAsc.get(i).getFirstName()) <= 0);
        }
    }

    @Test
    public void testGetAllWithOrderByDesc() throws ODataException {
        List<BusinessPartner> listAsc = new DefaultBusinessPartnerService()
                .getAllBusinessPartner()
                .select(BusinessPartner.FIRST_NAME)
                .orderBy(BusinessPartner.FIRST_NAME, Order.DESC)
                .execute();

        for(int i = 1; i < listAsc.size(); i++) {
            assertTrue("List is not sorted: " + listAsc.toString(),
                    listAsc.get(i-1).getFirstName().compareTo(listAsc.get(i).getFirstName()) >= 0);
        }
    }

    @Test
    public void testGetByKeyPlain() throws ODataException {
        final BusinessPartner businessPartner = new DefaultBusinessPartnerService()
                .getBusinessPartnerByKey(BUPA_ID)
                .execute();

        assertThat(businessPartner, notNullValue());
        assertThat(businessPartner.getBusinessPartner(), equalTo(BUPA_ID));
        assertThat(businessPartner.getBusinessPartnerCategory(), not(isEmptyOrNullString()));
    }

    @Test
    public void testGetByKeyWithSelect() throws ODataException {
        final BusinessPartner businessPartner = new DefaultBusinessPartnerService()
                .getBusinessPartnerByKey(BUPA_ID)
                .select(BusinessPartner.BUSINESS_PARTNER_FULL_NAME, BusinessPartner.CREATED_BY_USER)
                .execute();

        assertThat(businessPartner, notNullValue());
        assert_IdNull_FullNameNonNull_CreatedByNonNull(businessPartner);
    }

    @Test
    public void testGetByKeyWithExpand() throws ODataException {
        final BusinessPartner businessPartner = new DefaultBusinessPartnerService()
                .getBusinessPartnerByKey(BUPA_ID)
                .select(BusinessPartner.BUSINESS_PARTNER, BusinessPartner.TO_BUSINESS_PARTNER_ADDRESS)
                .execute();

        assertThat(businessPartner, notNullValue());
        assert_IdNonNull_ToAddressNonNull(businessPartner);
    }
}
