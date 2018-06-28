package com.sap.cloud.s4hana.examples.odata.businesspartner;

import com.sap.cloud.s4hana.examples.odata.mock.PartnerData;
import com.sap.cloud.sdk.service.prov.api.operations.Create;
import com.sap.cloud.sdk.service.prov.api.operations.Query;
import com.sap.cloud.sdk.service.prov.api.operations.Update;
import com.sap.cloud.sdk.service.prov.api.request.CreateRequest;
import com.sap.cloud.sdk.service.prov.api.request.QueryRequest;
import com.sap.cloud.sdk.service.prov.api.request.UpdateRequest;
import com.sap.cloud.sdk.service.prov.api.response.CreateResponse;
import com.sap.cloud.sdk.service.prov.api.response.ErrorResponse;
import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;
import com.sap.cloud.sdk.service.prov.api.response.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BPImplementation {
    private static final Logger logger = LoggerFactory.getLogger(BPImplementation.class);

    private static final PartnerData mockData = new PartnerData();

    @Query(serviceName = "EPMSampleService", entity = "BusinessPartners")
    public QueryResponse getAllPartners(QueryRequest partnerQuery) {

        final List<Partner> partnersList = getPartnersfromDB();
        return QueryResponse.setSuccess().setData(partnersList).response();
    }

    @Create(serviceName = "EPMSampleService", entity = "BusinessPartners")
    public CreateResponse createPartner(CreateRequest createRequest) {

        final Partner partnerData = createRequest.getDataAs(Partner.class);
        try {
            final Partner createdPartner = insertPartner(partnerData);
            return CreateResponse.setSuccess().setData(createdPartner).response();
        } catch (final Exception e) {

            return CreateResponse.setError(
                    ErrorResponse.getBuilder().setMessage("Failed to add Business Partner").setCause(e).response());
        }

    }

    private Partner insertPartner(Partner partnerData) throws Exception {
        return mockData.createNewPartner(partnerData);
    }

    @Update(serviceName = "EPMSampleService", entity = "BusinessPartners")
    public UpdateResponse updatePartner(UpdateRequest updateRequest) {

        try {
            final String partnerID = (String) updateRequest.getKeys().get("id");
            final Partner updatedPartner = updatePartner(partnerID, updateRequest.getDataAs(Partner.class));
            return UpdateResponse.setSuccess().response();
        } catch (final Exception e) {
            return UpdateResponse
                    .setError(ErrorResponse.getBuilder().setMessage(e.getMessage()).setCause(e).response());
        }
    }

    private Partner updatePartner(String partnerID, Partner partner) throws Exception {
        return mockData.updatePartner(partnerID, partner);
    }

    private List<Partner> getPartnersfromDB() {
        return mockData.getAllPartners();
    }

}
