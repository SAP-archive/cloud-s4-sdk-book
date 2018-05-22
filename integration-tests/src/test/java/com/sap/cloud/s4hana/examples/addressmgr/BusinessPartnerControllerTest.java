package com.sap.cloud.s4hana.examples.addressmgr;

import com.sap.cloud.s4hana.examples.addressmgr.controllers.BusinessPartnerController;
import com.sap.cloud.sdk.testutil.MockUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BusinessPartnerController.class, secure = false)
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BusinessPartnerControllerTest {
    private static final MockUtil mockUtil = new MockUtil();

    public static final String BUPA_ID = "1003764";

    @Autowired
    private MockMvc mvc;

    @BeforeClass
    public static void beforeClass() {
        mockUtil.mockDefaults();
        mockUtil.mockErpDestination();
    }

    @Test
    public void testGetAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/business-partners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty());
    }

    @Test
    public void testGetSingle() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/business-partners?id={id}", BUPA_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.BusinessPartner").value(BUPA_ID))
                .andExpect(jsonPath("$.LastName").isNotEmpty());
    }
}
