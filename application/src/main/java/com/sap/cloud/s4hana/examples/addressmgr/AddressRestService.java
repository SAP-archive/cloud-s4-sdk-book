package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.common.base.Strings;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.cloudplatform.servlet.exception.MissingParameterException;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/business-partners/{businerPartnerId}/addresses")
public class AddressRestService {
    private static final Logger logger =
            CloudLoggerFactory.getLogger(AddressRestService.class);

    @Inject
    private BusinessPartnerService service;

    @Inject
    private AddressLogic addressLogic;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPost(final BusinessPartnerAddress address)
            throws IOException {

        logger.info("Received post request to create address {}", address);
        final BusinessPartnerAddress addressCreated = addressLogic.create(address);
        return Response.status(Response.Status.CREATED).entity(addressCreated).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{addressId}")
    public Response doPut(@PathParam("businerPartnerId") final String businessPartnerId,
                          @PathParam("addressId") final String addressId,
                          final BusinessPartnerAddress address) {
        final BusinessPartnerAddress addressToUpdate = createAddressToUpdate(businessPartnerId, addressId, address);

        logger.info("Received put request to update address {}", address);
        addressLogic.update(addressToUpdate);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private BusinessPartnerAddress createAddressToUpdate(String businessPartnerId, String addressId,
                                                         BusinessPartnerAddress addressFromBody) {
        final BusinessPartnerAddress addressToUpdate = BusinessPartnerAddress.builder()
                .businessPartner(businessPartnerId)
                .addressID(addressId)
                .build();
        // Only change properties for which non-null values have been provided
        if (addressFromBody.getStreetName() != null)
            addressToUpdate.setStreetName(addressFromBody.getStreetName());
        if (addressFromBody.getHouseNumber() != null)
            addressToUpdate.setHouseNumber(addressFromBody.getHouseNumber());
        if (addressFromBody.getCityName() != null)
            addressToUpdate.setCityName(addressFromBody.getCityName());
        if (addressFromBody.getPostalCode() != null)
            addressToUpdate.setPostalCode(addressFromBody.getPostalCode());
        if (addressFromBody.getCountry() != null)
            addressToUpdate.setCountry(addressFromBody.getCountry());
        return addressToUpdate;
    }

    @DELETE
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{addressId}")
    public Response doDelete(@PathParam("businerPartnerId") final String businessPartnerId,
                             @PathParam("addressId") final String addressId)
            throws IOException, MissingParameterException {

        if (Strings.isNullOrEmpty(businessPartnerId)) {
            throw new MissingParameterException("businessPartnerId");
        } else if (Strings.isNullOrEmpty(addressId)) {
            throw new MissingParameterException("addressId");
        }

        logger.info("Received delete request to delete address {},{}", businessPartnerId, addressId);
        addressLogic.delete(businessPartnerId, addressId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
