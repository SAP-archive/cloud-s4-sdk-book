package com.sap.cloud.s4hana.book.odatatestservice;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import com.sap.cloud.s4hana.examples.addressmgr.custom.namespaces.bupasocialmedia.BusinessPartnerSocialMedia;
import com.sap.cloud.s4hana.examples.addressmgr.custom.namespaces.bupasocialmedia.SocialMediaAccount;
import com.sap.cloud.s4hana.examples.addressmgr.custom.services.DefaultBusinessPartnerSocialMediaService;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.testutil.MockUtil;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class ReadSocialMediaTest {

    private MockUtil mockUtil;

    @Before
    public void setup() {
        mockUtil = new MockUtil();
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    @Test
    public void testGetAll() throws ODataException {
        final List<BusinessPartnerSocialMedia> socialMediaList = new DefaultBusinessPartnerSocialMediaService()
                .getAllBusinessPartnerSocialMedia()
                .select(BusinessPartnerSocialMedia.BUSINESS_PARTNER,
                        BusinessPartnerSocialMedia.TO_SOCIAL_MEDIA_ACCOUNT.select(
                                SocialMediaAccount.SERVICE,
                                SocialMediaAccount.ACCOUNT
                        ))
                .execute();

        assertThat(socialMediaList, allOf(notNullValue(), not(empty())));
        assertThat(socialMediaList, not(empty()));
        final BusinessPartnerSocialMedia socialMedia = socialMediaList.get(0);
        assert_BuPaNotNull_AccountNotEmpty(socialMedia);
    }

    private static void assert_BuPaNotNull_AccountNotEmpty(final BusinessPartnerSocialMedia socialMedia) {
        assertThat(socialMedia.getBusinessPartner(), not(isEmptyOrNullString()));
        assertThat(socialMedia.getSocialMediaAccountIfPresent().get(), allOf(notNullValue(), not(empty())));
    }

    @Test
    public void testGetSingle() throws ODataException {
        final UUID socialMediaKey = new DefaultBusinessPartnerSocialMediaService()
                .getAllBusinessPartnerSocialMedia()
                .select(BusinessPartnerSocialMedia.UUID)
                .top(1)
                .execute()
                .get(0)
                .getUUID();

        final BusinessPartnerSocialMedia socialMedia = new DefaultBusinessPartnerSocialMediaService()
                .getBusinessPartnerSocialMediaByKey(socialMediaKey)
                .select(BusinessPartnerSocialMedia.BUSINESS_PARTNER,
                        BusinessPartnerSocialMedia.TO_SOCIAL_MEDIA_ACCOUNT.select(
                                SocialMediaAccount.SERVICE,
                                SocialMediaAccount.ACCOUNT
                        ))
                .execute();
        assert_BuPaNotNull_AccountNotEmpty(socialMedia);
    }
}
