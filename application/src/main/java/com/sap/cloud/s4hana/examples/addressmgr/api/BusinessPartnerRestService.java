package com.sap.cloud.s4hana.examples.addressmgr.api;

import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllBusinessPartnersCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleBusinessPartnerByIdCommand;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import io.swagger.api.BusinessPartnerServiceApi;
import io.swagger.model.BusinessPartnerResponse;
import io.swagger.model.BusinessPartnersResponse;
import io.swagger.model.Link;
import org.slf4j.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.Collections;
import java.util.List;

public class BusinessPartnerRestService implements BusinessPartnerServiceApi {
    private static final Logger logger = CloudLoggerFactory.getLogger(BusinessPartnerRestService.class);

    @Context
    UriInfo uriInfo;

    private final BusinessPartnerService service = new DefaultBusinessPartnerService();

    @Override
    public BusinessPartnersResponse getBusinessPartners() {
        List<BusinessPartner> businessPartners = new GetAllBusinessPartnersCommand(service).execute();

        final BusinessPartnersResponse response = new BusinessPartnersResponse();
        response.setValue(new io.swagger.model.BusinessPartners());
        for (BusinessPartner businessPartner : businessPartners) {
            response.getValue().add(convert(businessPartner));
        }
        return response;
    }

    private io.swagger.model.BusinessPartner convert(BusinessPartner businessPartner) {
        io.swagger.model.BusinessPartner out = new io.swagger.model.BusinessPartner();
        out.setFirstName(businessPartner.getFirstName());
        out.setLastName(businessPartner.getLastName());
        out.setId(businessPartner.getBusinessPartner());

        List<BusinessPartnerAddress> addresses = businessPartner.getBusinessPartnerAddressIfPresent()
                .orElse(Collections.emptyList());
        for (BusinessPartnerAddress address : addresses) {
            Link addressLink = new Link();
            String url = uriInfo.getAbsolutePath().toString() + "/addresses/" + address.getAddressID();
            addressLink.setHref(url);
            out.getAddresses().add(addressLink);
        }

        return out;
    }

    @Override
    public BusinessPartnerResponse getBusinessPartnersById(final String businessPartnerId) {
        BusinessPartner businessPartner = new GetSingleBusinessPartnerByIdCommand(service, businessPartnerId).execute();
        if (businessPartner == null) {
            throw new NotFoundException();
        }
        BusinessPartnerResponse response = new BusinessPartnerResponse();
        response.setValue(convert(businessPartner));

        return response;
    }
}
