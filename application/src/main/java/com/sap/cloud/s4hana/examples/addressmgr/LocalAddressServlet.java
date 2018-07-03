package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.sap.cloud.s4hana.examples.addressmgr.commands.CreateAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.datasource.MultitenantEntityManagerFacade;
import com.sap.cloud.s4hana.examples.addressmgr.models.Address;
import com.sap.cloud.s4hana.examples.addressmgr.models.Status;
import com.sap.cloud.s4hana.examples.addressmgr.util.HttpServlet;
import com.sap.cloud.s4hana.examples.addressmgr.views.AddressView;
import com.sap.cloud.s4hana.examples.addressmgr.views.ViewModelConverter;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

@WebServlet("/api/addresses-local")
public class LocalAddressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(LocalAddressServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String status = request.getParameter("status");

        final EntityManager entityManager = MultitenantEntityManagerFacade.getInstance().getEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Retrieve all addresses
        if (status == null) {
            final List<Address> addresses = entityManager.createNamedQuery("Address.findAll", Address.class).getResultList();

            final List<AddressView> addressViews = ViewModelConverter.convertAddressesToAddressViews(addresses);
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(addressViews));

            // Retrieve addresses by status
        } else if (EnumUtils.isValidEnum(Status.class, status)) {
            final List<Address> addresses = entityManager.createNamedQuery("Address.findByStatus", Address.class).setParameter("status", status).getResultList();

            final List<AddressView> addressViews = ViewModelConverter.convertAddressesToAddressViews(addresses);
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(addressViews));

            // Fail: wrong status in HTTP request
        } else {
            logger.error("Wrong status value. Possible values: new, approved, rejected.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        transaction.commit();
        entityManager.close();
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse resp) throws IOException {
        final AddressView addressView = getAddressFromBody(request);
        final Address address = ViewModelConverter.convertAddressViewToAddress(addressView);

        address.setStatus(Status.NEW.toString());
        logger.info("Received post request to create local address {}", address);

        final EntityManager entityManager = MultitenantEntityManagerFacade.getInstance().getEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        // Persist the address
        transaction.begin();
        entityManager.persist(address);
        transaction.commit();

        // Retrieve all current local addresses
        transaction.begin();
        final List<Address> addresses = entityManager.createNamedQuery("Address.findAll", Address.class).getResultList();
        transaction.commit();
        entityManager.close();

        final List<AddressView> addressViews = ViewModelConverter.convertAddressesToAddressViews(addresses);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        resp.getWriter().write(new Gson().toJson(addressViews));
    }

    private AddressView getAddressFromBody(final HttpServletRequest request)
            throws IOException {
        final String body = IOUtils.toString(request.getInputStream(), "utf-8");
        return new Gson().fromJson(body, AddressView.class);
    }

    @Override
    protected void doPatch(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        try {
            final Long addressId = Long.parseLong(request.getParameter("addressId"));
            final Status status = Status.valueOf(request.getParameter("status"));

            final EntityManager entityManager = MultitenantEntityManagerFacade.getInstance().getEntityManager();
            final EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            final Address address = entityManager.find(Address.class, addressId);
            transaction.commit();

            if (address == null) {
                throw new IllegalArgumentException(String.format("Address with the id %d does not exist", addressId));
            } else {
                switch (status) {
                    case APPROVED:
                        approveAddress(address, entityManager);
                        break;
                    case REJECTED:
                        rejectAddress(address, entityManager);
                        break;
                }
            }
            entityManager.close();
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (IllegalArgumentException e) {
            logger.error("Wrong parameters in the HTTP request");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void approveAddress(final Address address, final EntityManager entityManager) {
        logger.info("Address approved - changing the status and creating in S/4HANA {}", address);
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        address.setStatus(Status.APPROVED.toString());
        transaction.commit();

        BusinessPartnerAddress businessPartnerAddress = convertAddress(address);
        final DefaultBusinessPartnerService businessPartnerService = new DefaultBusinessPartnerService();

        new CreateAddressCommand(businessPartnerService, businessPartnerAddress).execute();
    }

    private BusinessPartnerAddress convertAddress(final Address address) {
        final BusinessPartnerAddress businessPartnerAddress = new BusinessPartnerAddress();
        businessPartnerAddress.setBusinessPartner(address.getBusinessPartner());
        businessPartnerAddress.setCityName(address.getCityName());
        businessPartnerAddress.setCountry(address.getCountry());
        businessPartnerAddress.setHouseNumber(address.getHouseNumber());
        businessPartnerAddress.setPostalCode(address.getPostalCode());
        businessPartnerAddress.setStreetName(address.getStreetName());
        return businessPartnerAddress;
    }

    private void rejectAddress(final Address address, final EntityManager entityManager) {
        logger.info("Address rejected - changing the status {}", address);
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        address.setStatus(Status.REJECTED.toString());
        transaction.commit();
    }
}
