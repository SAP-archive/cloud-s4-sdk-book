package com.sap.cloud.s4hana.examples.addressmgr;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.sap.cloud.s4hana.examples.addressmgr.vdm.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.s4hana.examples.addressmgr.vdm.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.s4hana.examples.addressmgr.vdm.services.BusinessPartnerService;
import com.sap.cloud.s4hana.examples.addressmgr.vdm.services.DefaultBusinessPartnerService;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;

@WebServlet( "/api/business-partners" )
public class BusinessPartnerServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(BusinessPartnerServlet.class);

    private final BusinessPartnerService service = new DefaultBusinessPartnerService();

    private static final String CATEGORY_PERSON = "1";

    @Override
    protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        final String id = request.getParameter("id");

        try {
            final String jsonResult;
            if( id == null ) {
                logger.info("Retrieving all business partners");

                final List<BusinessPartner> result =
                    service
                        .getAllBusinessPartner()
                        .select(
                            BusinessPartner.BUSINESS_PARTNER,
                            BusinessPartner.LAST_NAME,
                            BusinessPartner.FIRST_NAME,
                            BusinessPartner.ADDR_LAST_CHECKED_BY_BUS,
                            BusinessPartner.ADDR_LAST_CHECKED_ON_BUS)
                        .filter(BusinessPartner.BUSINESS_PARTNER_CATEGORY.eq(CATEGORY_PERSON))
                        .orderBy(BusinessPartner.LAST_NAME, Order.ASC)
                        .execute();

                jsonResult = new Gson().toJson(result);
            } else {
                if( !validateInput(id) ) {
                    logger.warn("Invalid request to retrieve a business partner, id: {}.", id);
                    response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        String.format(
                            "Invalid business partner ID '%s'. "
                                + "Business partner ID must not be empty or longer than 10 characters.",
                            id));
                    return;
                }
                logger.info("Retrieving business partner with id {}.", id);

                final BusinessPartner result =
                    service
                        .getBusinessPartnerByKey(id)
                        .select(
                            BusinessPartner.BUSINESS_PARTNER,
                            BusinessPartner.LAST_NAME,
                            BusinessPartner.FIRST_NAME,
                            BusinessPartner.IS_MALE,
                            BusinessPartner.IS_FEMALE,
                            BusinessPartner.CREATION_DATE,
                            BusinessPartner.ADDR_LAST_CHECKED_BY_BUS,
                            BusinessPartner.ADDR_LAST_CHECKED_ON_BUS,
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

            response.setContentType("application/json");
            response.getWriter().write(jsonResult);
        }
        catch( final Exception e ) {
            logger.error("Error while getting business partners.", e);
            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Could not get business partners due to error while calling SAP S/4HANA.");
        }
    }

    private boolean validateInput( final String id )
    {
        return !Strings.isNullOrEmpty(id) && id.length() <= 10;
    }
}
