package com.sap.cloud.s4hana.examples.addressmgr;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/business-partners")
public class BusinessPartnerRestService {
    private static final Logger logger =
            CloudLoggerFactory.getLogger(BusinessPartnerRestService.class);

    @Inject
    private BusinessPartnerLogic businessPartnerLogic;

    @GET
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessPartner> doGet() {
        logger.info("Retrieving all business partners");
        return businessPartnerLogic.getAll();
    }

    @GET
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public BusinessPartner doGet(@PathParam("id") String id) {
        logger.info("Retrieving business partner with id {}", id);
        return businessPartnerLogic.getById(id);
    }
}
