package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.sap.cloud.s4hana.examples.addressmgr.commands.CreateAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.DeleteAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.UpdateAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/api/addresses")
public class AddressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(AddressServlet.class);

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final BusinessPartnerAddress address = getAddressFromBody(request);

        logger.info("Received post request to create address {}", address);
        final BusinessPartnerAddress addressCreated = new CreateAddressCommand(address).execute();

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(addressCreated));
    }

    private BusinessPartnerAddress getAddressFromBody(final HttpServletRequest req) throws IOException {
        final String body = CharStreams.toString(new InputStreamReader(req.getInputStream(), Charsets.UTF_8));
        logger.info("Converting body {} to address", body);
        return new Gson().fromJson(body, BusinessPartnerAddress.class);
    }

    @Override
    protected void doPatch(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String businessPartnerId = request.getParameter("businessPartnerId");
        final String addressId = request.getParameter("addressId");

        final BusinessPartnerAddress addressFromBody = getAddressFromBody(request);
        final BusinessPartnerAddress addressToUpdate = createAddressToUpdate(businessPartnerId, addressId, addressFromBody);

        logger.info("Received patch request to update address {}", addressToUpdate);
        new UpdateAddressCommand(addressToUpdate).execute();

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private BusinessPartnerAddress createAddressToUpdate(String businessPartnerId, String addressId,
                                                         BusinessPartnerAddress addressFromBody) {
        final BusinessPartnerAddress addressToUpdate = BusinessPartnerAddress.builder()
                .businessPartner(businessPartnerId)
                .addressID(addressId)
                .build();
        // TODO: check if null
        addressToUpdate.setStreetName(addressFromBody.getStreetName());
        addressToUpdate.setHouseNumber(addressFromBody.getHouseNumber());
        addressToUpdate.setCityName(addressFromBody.getCityName());
        addressToUpdate.setPostalCode(addressFromBody.getPostalCode());
        addressToUpdate.setCountry(addressFromBody.getCountry());
        return addressToUpdate;
    }

    @Override
    protected void doDelete(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String businessPartnerId = request.getParameter("businessPartnerId");
        final String addressId = request.getParameter("addressId");

        if (Strings.isNullOrEmpty(businessPartnerId) || Strings.isNullOrEmpty(addressId)) {
            logger.warn("Invalid request to delete - at least one mandatory parameter missing, query was: {}",
                    request.getQueryString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter()
                    .write("Bad request: please provide query parameters businessPartnerId and addressId");
            return;
        }

        logger.info("Received delete request to delete address {},{}", businessPartnerId, addressId);
        new DeleteAddressCommand(businessPartnerId, addressId).execute();

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
