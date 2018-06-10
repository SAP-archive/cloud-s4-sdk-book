package com.sap.cloud.s4hana.examples.addressmgr;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.Nullable;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

@WebServlet( "/api/addresses" )
public class AddressServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(AddressServlet.class);

    private final BusinessPartnerService service = new DefaultBusinessPartnerService();

    @Override
    protected void doPost( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        final BusinessPartnerAddress addressToCreate = getAddressFromBody(request);

        if( addressToCreate == null ) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Request body is not a valid address object in JSON format.");
            return;
        }

        if( !validateInputForCreate(addressToCreate) ) {
            logger.warn("Invalid request to create an address: {}.", addressToCreate);
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Bad request: business partner of address needs to be specified.");
            return;
        }

        logger.info("Received post request to create address {}.", addressToCreate);
        try {
            final BusinessPartnerAddress createdAddress =
                service.createBusinessPartnerAddress(addressToCreate).execute();

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(createdAddress));
        }
        catch( final Exception e ) {
            logger.error("Error while creating address " + addressToCreate + " in SAP S/4HANA.", e);

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Could not create address due to error while calling SAP S/4HANA.");
        }
    }

    @Nullable
    private BusinessPartnerAddress getAddressFromBody( final HttpServletRequest request )
        throws IOException
    {
        final String body = CharStreams.toString(new InputStreamReader(request.getInputStream(), Charsets.UTF_8));
        logger.info("Converting body {} to address.", body);

        try {
            return new Gson().fromJson(body, BusinessPartnerAddress.class);
        }
        catch( final JsonParseException e ) {
            logger.warn("Error while trying to parse address from body.", e);
            return null;
        }
    }

    private boolean validateInputForCreate( BusinessPartnerAddress addressToCreate )
    {
        return !Strings.isNullOrEmpty(addressToCreate.getBusinessPartner());
    }

    @Override
    protected void doDelete( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        final String businessPartnerId = request.getParameter("businessPartnerId");
        final String addressId = request.getParameter("addressId");

        if( !validateIds(businessPartnerId, addressId) ) {
            logger.warn(
                "Invalid request to delete: at least one mandatory parameter was invalid, query was: {}",
                request.getQueryString());
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Please provide valid values for query parameters businessPartnerId and addressId");
            return;
        }

        logger.info("Received delete request to delete address {},{}", businessPartnerId, addressId);
        try {
            final BusinessPartnerAddress addressToDelete =
                BusinessPartnerAddress.builder().businessPartner(businessPartnerId).addressID(addressId).build();

            service.deleteBusinessPartnerAddress(addressToDelete).execute();
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        catch( final Exception e ) {
            logger.error(
                "Error while deleting address "
                    + addressId
                    + " of business partner "
                    + businessPartnerId
                    + " in SAP S/4HANA.",
                e);

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                String.format(
                    "Could not delete address %s of business partner %s" + " due to error while calling SAP S/4HANA.",
                    addressId,
                    businessPartnerId));
        }
    }

    private boolean validateIds( final String businessPartnerId, final String addressId )
    {
        return (!Strings.isNullOrEmpty(businessPartnerId) && businessPartnerId.length() <= 10)
            && (!Strings.isNullOrEmpty(addressId) && addressId.length() <= 10);
    }

    @Override
    protected void doPatch( final HttpServletRequest request, final HttpServletResponse response )
        throws IOException
    {
        final String businessPartnerId = request.getParameter("businessPartnerId");
        final String addressId = request.getParameter("addressId");
        if( !validateIds(businessPartnerId, addressId) ) {
            logger.warn(
                "Invalid request to update: at least one mandatory parameter was invalid, query was: {}.",
                request.getQueryString());
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Please provide valid values for query parameters businessPartnerId and addressId.");
            return;
        }

        final BusinessPartnerAddress address = getAddressFromBody(request);

        if( address == null ) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Request body is not a valid address object in JSON format.");
            return;
        }

        if( !validateInputForUpdate(address, businessPartnerId, addressId) ) {
            logger.warn(
                "Invalid request to update: at least one mismatch between body and query, query was: {}"
                    + "; and parsed body was: {}.",
                request.getQueryString(),
                address);
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Address in body must have none or matching identifiers.");
            return;
        }

        final BusinessPartnerAddress addressToUpdate = createAddressToUpdate(businessPartnerId, addressId, address);

        logger.info("Received patch request to update address {}.", addressToUpdate);
        try {
            service.updateBusinessPartnerAddress(addressToUpdate).execute();
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        catch( final Exception e ) {
            logger.error("Error while updating address " + addressToUpdate + " in SAP S/4HANA.", e);
            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                String.format(
                    "Could not update address %s of business partner %s" + " due to error while calling SAP S/4HANA.",
                    addressId,
                    businessPartnerId));
        }
    }

    private boolean validateInputForUpdate(
        final BusinessPartnerAddress addressFromBody,
        final String businessPartnerId,
        final String addressId )
    {
        return (addressFromBody.getBusinessPartner() == null
            || addressFromBody.getBusinessPartner().equals(businessPartnerId))
            && (addressFromBody.getAddressID() == null || addressFromBody.getAddressID().equals(addressId));
    }

    private BusinessPartnerAddress createAddressToUpdate(
        final String businessPartnerId,
        final String addressId,
        final BusinessPartnerAddress addressFromBody )
    {
        final BusinessPartnerAddress addressToUpdate =
            BusinessPartnerAddress.builder().businessPartner(businessPartnerId).addressID(addressId).build();

        // only change properties for which non-null values have been provided
        if( addressFromBody.getStreetName() != null ) {
            addressToUpdate.setStreetName(addressFromBody.getStreetName());
        }
        if( addressFromBody.getHouseNumber() != null ) {
            addressToUpdate.setHouseNumber(addressFromBody.getHouseNumber());
        }
        if( addressFromBody.getCityName() != null ) {
            addressToUpdate.setCityName(addressFromBody.getCityName());
        }
        if( addressFromBody.getPostalCode() != null ) {
            addressToUpdate.setPostalCode(addressFromBody.getPostalCode());
        }
        if( addressFromBody.getCountry() != null ) {
            addressToUpdate.setCountry(addressFromBody.getCountry());
        }

        return addressToUpdate;
    }
}
