package com.sap.cloud.s4hana.examples.addressmgr;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

import com.sap.cloud.s4hana.examples.addressmgr.commands.GetAllBusinessPartnersCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.GetSingleBusinessPartnerByIdCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;

@ApplicationScoped
public class BusinessPartnerLogic {

    @Inject
    private BusinessPartnerService service;

    public BusinessPartner getById(final String businessPartnerId) {
        return new GetSingleBusinessPartnerByIdCommand(service, businessPartnerId).execute();
    }

    public List<BusinessPartner> getAll() {
        return new GetAllBusinessPartnersCommand(service).execute();
    }
}
