package com.sap.cloud.s4hana.examples.addressmgr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.s4hana.examples.addressmgr.controllers.AddressController;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.testutil.MockUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AddressController.class, secure = false)
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressControllerTest {
    private static final MockUtil mockUtil = new MockUtil();

    private static final String CREATE_BODY_TEMPLATE = "{\n" +
            "  \"BusinessPartner\": \"" + BusinessPartnerControllerTest.BUPA_ID + "\",\n" +
            "  \"CityName\": \"Potsdam\",\n" +
            "  \"Country\": \"DE\",\n" +
            "  \"HouseNumber\": \"%s\",\n" +
            "  \"PostalCode\": \"14469\",\n" +
            "  \"StreetName\": \"Konrad-Zuse-Ring\"\n" +
            "}";
    private static final String UPDATE_BODY_TEMPLATE = "{\n" +
            "  \"CityName\": \"Potsdam\",\n" +
            "  \"Country\": \"DE\",\n" +
            "  \"HouseNumber\": \"%s\",\n" +
            "  \"PostalCode\": \"14469\",\n" +
            "  \"StreetName\": \"Konrad-Zuse-Ring\"\n" +
            "}";

    @Autowired
    private MockMvc mvc;

    @BeforeClass
    public static void beforeClass() {
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    private BusinessPartnerAddress getAddress(
            final String bupaId, final String addressId) {
        return new GetAddressCommand(bupaId, addressId).execute();
    }

    private BusinessPartnerAddress createAddress() throws Exception {
        final String houseNumber =
                String.valueOf(new Random().nextInt(1000));
        final String createRequestBody =
                String.format(CREATE_BODY_TEMPLATE, houseNumber);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/api/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.BusinessPartner")
                        .value(BusinessPartnerControllerTest.BUPA_ID))
                .andExpect(jsonPath("$.HouseNumber")
                        .value(houseNumber))
                .andExpect(jsonPath("$.AddressID").isNotEmpty())
                .andReturn();

        ObjectMapper deserializer = new ObjectMapper();
        return deserializer.readValue(
                result.getResponse().getContentAsString(),
                BusinessPartnerAddress.class);
    }

    @Test
    public void testCreate() throws Exception {
        final BusinessPartnerAddress createdAddress = createAddress();
        final BusinessPartnerAddress loadedAddress =
                getAddress(BusinessPartnerControllerTest.BUPA_ID, createdAddress.getAddressID());

        assertEquals(
                createdAddress.getCityName(),
                loadedAddress.getCityName());
        assertEquals(
                createdAddress.getCountry(),
                loadedAddress.getCountry());
        assertEquals(
                createdAddress.getHouseNumber(),
                loadedAddress.getHouseNumber());
        assertEquals(
                createdAddress.getPostalCode(),
                loadedAddress.getPostalCode());
        assertEquals(
                createdAddress.getStreetName(),
                loadedAddress.getStreetName());
    }

    @Test
    public void testDelete() throws Exception {
        final BusinessPartnerAddress addressToDelete = createAddress();

        mvc.perform(MockMvcRequestBuilders
                .delete("/api/addresses" +
                                "?businessPartnerId={bupaId}" +
                                "&addressId={addressId}",
                        BusinessPartnerControllerTest.BUPA_ID, addressToDelete.getAddressID()))
                .andExpect(status().isNoContent());

        final BusinessPartnerAddress loadedAddress =
                getAddress(BusinessPartnerControllerTest.BUPA_ID, addressToDelete.getAddressID());

        assertNull(loadedAddress);
    }

    @Test
    public void testUpdate() throws Exception {
        // Create address to update
        final BusinessPartnerAddress addressToUpdate = createAddress();

        final String updateRequestBody =
                String.format(UPDATE_BODY_TEMPLATE, "10");

        mvc.perform(MockMvcRequestBuilders
                .patch("/api/addresses" +
                                "?businessPartnerId={bupaId}" +
                                "&addressId={addressId}",
                        BusinessPartnerControllerTest.BUPA_ID, addressToUpdate.getAddressID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequestBody))
                .andExpect(status().isNoContent());

        final BusinessPartnerAddress updatedAddress =
                getAddress(BusinessPartnerControllerTest.BUPA_ID, addressToUpdate.getAddressID());

        assertEquals("10", updatedAddress.getHouseNumber());
    }
}
