package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sap.cloud.s4hana.examples.addressmgr.commands.CreateAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.DeleteAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.UpdateAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

@WebServlet("/api/addresses")
public class AddressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(AddressServlet.class);

    private final BusinessPartnerService service = new DefaultBusinessPartnerService();

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final BusinessPartnerAddress address;
        try {
            address = getAddressFromBody(request);
        } catch (JsonParseException e) {
            logger.warn("Error while trying to parse address from body.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    String.format("Request body is not a valid address object in JSON format: %s.", e.getMessage()));
            return;
        }

        if(!validateInputForCreate(address)) {
            logger.warn("Invalid request to create an address: {}.", address);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Bad request: business partner of address needs to be specified.");
            return;
        }

        logger.info("Received post request to create address {}", address);
        try {
            final BusinessPartnerAddress addressCreated = new CreateAddressCommand(service, address).execute();
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(addressCreated));
        } catch (Exception e) {
            logger.error("Error while creating address {} in SAP S/4HANA.", address, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Could not create address due to error while calling SAP S/4HANA.");
        }
    }

    private BusinessPartnerAddress getAddressFromBody(final HttpServletRequest req) throws IOException {
        final String body = CharStreams.toString(new InputStreamReader(req.getInputStream(), Charsets.UTF_8));
        logger.info("Converting body {} to address", body);
        return new Gson().fromJson(body, BusinessPartnerAddress.class);
    }

    private boolean validateInputForCreate(BusinessPartnerAddress addressToCreate) {
        return !Strings.isNullOrEmpty(addressToCreate.getBusinessPartner());
    }

    @Override
    protected void doPatch(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String businessPartnerId = request.getParameter("businessPartnerId");
        final String addressId = request.getParameter("addressId");
        if(!validateIds(businessPartnerId, addressId)) {
            logger.warn("Invalid request to update: at least one mandatory parameter was invalid, query was: {}",
                    request.getQueryString());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Please provide valid values for query parameters businessPartnerId and addressId");
            return;
        }

        final BusinessPartnerAddress addressFromBody;
        try {
            addressFromBody = getAddressFromBody(request);
        } catch (JsonParseException e) {
            logger.warn("Error while trying to parse address from body.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    String.format("Request body is not a valid address object in JSON format: %s.", e.getMessage()));
            return;
        }

        if(!validateInputForUpdate(addressFromBody, businessPartnerId, addressId)) {
            logger.warn("Invalid request to update: at least one mismatch between body and query, query was: {}" +
                            "; and parsed body was: {}.", request.getQueryString(), addressFromBody);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Address in body must have none or matching identifiers.");
            return;
        }

        final BusinessPartnerAddress addressToUpdate = createAddressToUpdate(businessPartnerId, addressId, addressFromBody);

        logger.info("Received patch request to update address {}", addressToUpdate);
        try {
            new UpdateAddressCommand(service, addressToUpdate).execute();

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error while updating address {} in SAP S/4HANA.", addressToUpdate, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    String.format("Could not update address %s of business partner %s" +
                            " due to error while calling SAP S/4HANA.", addressId, businessPartnerId));
        }
    }

    private BusinessPartnerAddress createAddressToUpdate(String businessPartnerId, String addressId,
                                                         BusinessPartnerAddress addressFromBody) {
        final BusinessPartnerAddress addressToUpdate = BusinessPartnerAddress.builder()
                .businessPartner(businessPartnerId)
                .addressID(addressId)
                .build();
        // Only change properties for which non-null values have been provided
        if(addressFromBody.getStreetName() != null)
            addressToUpdate.setStreetName(addressFromBody.getStreetName());
        if(addressFromBody.getHouseNumber() != null)
            addressToUpdate.setHouseNumber(addressFromBody.getHouseNumber());
        if(addressFromBody.getCityName() != null)
            addressToUpdate.setCityName(addressFromBody.getCityName());
        if(addressFromBody.getPostalCode() != null)
            addressToUpdate.setPostalCode(addressFromBody.getPostalCode());
        if(addressFromBody.getCountry() != null)
            addressToUpdate.setCountry(addressFromBody.getCountry());
        return addressToUpdate;
    }

    private boolean validateInputForUpdate(BusinessPartnerAddress addressFromBody, String businessPartnerId,
                                           String addressId) {
        return (addressFromBody.getBusinessPartner() == null
                    || addressFromBody.getBusinessPartner().equals(businessPartnerId)) &&
                (addressFromBody.getAddressID() == null || addressFromBody.getAddressID().equals(addressId));
    }

    @Override
    protected void doDelete(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String businessPartnerId = request.getParameter("businessPartnerId");
        final String addressId = request.getParameter("addressId");

        if(!validateIds(businessPartnerId, addressId)) {
            logger.warn("Invalid request to delete: at least one mandatory parameter was invalid, query was: {}",
                    request.getQueryString());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Please provide valid values for query parameters businessPartnerId and addressId");
            return;
        }

        logger.info("Received delete request to delete address {},{}", businessPartnerId, addressId);
        try {
            new DeleteAddressCommand(service, businessPartnerId, addressId).execute();

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error while deleting address {} of business partner {} in SAP S/4HANA.",
                    addressId, businessPartnerId, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    String.format("Could not delete address %s of business partner %s" +
                            " due to error while calling SAP S/4HANA.", addressId, businessPartnerId));
        }
    }

    private boolean validateIds(String businessPartnerId, String addressId) {
        return (!Strings.isNullOrEmpty(businessPartnerId) && businessPartnerId.length() <= 10) &&
                (!Strings.isNullOrEmpty(addressId) && addressId.length() <= 10);
    }
}
