package com.sap.cloud.s4hana.examples.addressmgr.api;

import com.sap.cloud.s4hana.examples.addressmgr.commands.CreateAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.DeleteAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.ReadAddressCommand;
import com.sap.cloud.s4hana.examples.addressmgr.commands.UpdateAddressCommand;
import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.BusinessPartnerService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;
import io.swagger.api.BusinessPartnerAddressServiceApi;
import io.swagger.model.Address;
import io.swagger.model.AddressResponse;
import org.apache.http.HttpStatus;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class AddressRestService implements BusinessPartnerAddressServiceApi {

    @Context
    UriInfo uriInfo;

    private final BusinessPartnerService service = new DefaultBusinessPartnerService();

    @Override
    public AddressResponse deleteAddress(final String businessPartnerId, final String addressId) {
        int returnCode = new DeleteAddressCommand(service, businessPartnerId, addressId).execute();
        if (returnCode == HttpStatus.SC_NOT_FOUND) {
            throw new NotFoundException();
        }
        if (returnCode == HttpStatus.SC_NO_CONTENT) {
            return new AddressResponse();
        }

        throw new ShouldNotHappenException("Return code of Odata:" + returnCode);
    }

    @Override
    public AddressResponse createAddress(final String businessPartnerId, final Address address, String location) {
        BusinessPartnerAddress addressToCreate = new BusinessPartnerAddress();
        addressToCreate.setStreetName(address.getStreetName());
        addressToCreate.setHouseNumber(address.getHouseNumber());
        addressToCreate.setPostalCode(address.getPostalCode());
        addressToCreate.setCityName(address.getCityName());
        addressToCreate.setCountry(address.getCountry());

        BusinessPartnerAddress businessPartnerAddress = new CreateAddressCommand(service, addressToCreate).execute();

        address.setId(businessPartnerAddress.getAddressID());
        AddressResponse result = new AddressResponse();
        location = uriInfo + "/" + businessPartnerId + "/addresses/" + businessPartnerAddress.getAddressID();
        result.setValue(address);
        return result;

    }

    @Override
    public AddressResponse updateAddress(final Address address, final String businessPartnerId, final String
            addressId) {
        BusinessPartnerAddress partnerAddress = new ReadAddressCommand(service, businessPartnerId, addressId).execute();
        if (partnerAddress == null) {
            throw new NotFoundException();
        }

        int returnCode = new UpdateAddressCommand(service, partnerAddress).execute();
        if (returnCode != HttpStatus.SC_OK) {
            throw new ShouldNotHappenException();
        }
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setValue(address);
        return addressResponse;
    }

}
