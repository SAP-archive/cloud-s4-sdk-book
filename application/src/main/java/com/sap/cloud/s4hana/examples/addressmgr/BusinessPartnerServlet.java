package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.gson.Gson;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/business-partners")
public class BusinessPartnerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(BusinessPartnerServlet.class);

    private static final String CATEGORY_PERSON = "1";

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String id = request.getParameter("id");

        final String jsonResult;
        try {
            if (id == null) {
                final List<BusinessPartner> result = new DefaultBusinessPartnerService()
                        .getAllBusinessPartner()
                        .select(BusinessPartner.BUSINESS_PARTNER,
                                BusinessPartner.LAST_NAME,
                                BusinessPartner.FIRST_NAME)
                        .filter(BusinessPartner.BUSINESS_PARTNER_CATEGORY.eq(CATEGORY_PERSON))
                        .orderBy(BusinessPartner.LAST_NAME, Order.ASC)
                        .execute();
                jsonResult = new Gson().toJson(result);
            } else {
                final BusinessPartner result = new DefaultBusinessPartnerService()
                        .getBusinessPartnerByKey(id)
                        .select(BusinessPartner.BUSINESS_PARTNER,
                                BusinessPartner.LAST_NAME,
                                BusinessPartner.FIRST_NAME,
                                BusinessPartner.IS_MALE,
                                BusinessPartner.IS_FEMALE,
                                BusinessPartner.CREATION_DATE,
                                BusinessPartner.TO_BUSINESS_PARTNER_ADDRESS.select(
                                        BusinessPartnerAddress.BUSINESS_PARTNER,
                                        BusinessPartnerAddress.ADDRESS_ID,
                                        BusinessPartnerAddress.COUNTRY,
                                        BusinessPartnerAddress.POSTAL_CODE,
                                        BusinessPartnerAddress.CITY_NAME,
                                        BusinessPartnerAddress.STREET_NAME,
                                        BusinessPartnerAddress.HOUSE_NUMBER))
                        .execute();
                jsonResult = new Gson().toJson(result);
            }
        } catch (ODataException e) {
            logger.error("Error retrieving business partners", e);
            throw new ServletException(e);
        }

        response.setContentType("application/json");
        response.getWriter().write(jsonResult);
    }
}
