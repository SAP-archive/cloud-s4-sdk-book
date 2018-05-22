package com.sap.cloud.s4hana.examples.addressmgr;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.sap.cloud.s4hana.examples.addressmgr.commands.CreateAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.DeleteAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.UpdateAddressCommand;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;

@RequestScoped
public class AddressLogic {

    @Inject
    private BusinessPartnerService service;

    public BusinessPartnerAddress create(BusinessPartnerAddress address) {
        return new CreateAddressCommand(service, address).execute();
    }

    public void update(BusinessPartnerAddress address) {
        new UpdateAddressCommand(service, address).execute();
    }

    public void delete(final String businessPartnerId, final String addressId) {
        new DeleteAddressCommand(service, businessPartnerId, addressId).execute();
    }
}
