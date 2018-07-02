package com.sap.cloud.s4hana.examples.addressmgr.views;

import com.google.common.collect.Lists;

import java.util.List;

import com.sap.cloud.s4hana.examples.addressmgr.models.Address;

public class ViewModelConverter {
    public static AddressView convertAddressToAddressView (Address address) {
        return new AddressView(address.getId(), address.getBusinessPartner(), address.getCityName(),
                address.getCountry(), address.getHouseNumber(), address.getPostalCode(), address.getStreetName(), address.getStatus());
    }

    public static List<AddressView> convertAddressesToAddressViews (List<Address> addresses) {
        final List<AddressView> addressViews = Lists.newArrayList();
        for (Address address : addresses) {
            addressViews.add(convertAddressToAddressView(address));
        }
        return addressViews;
    }

    public static Address convertAddressViewToAddress (AddressView addressView) {
        return new Address(addressView.getId(), addressView.getBusinessPartner(), addressView.getCityName(),
                addressView.getCountry(), addressView.getHouseNumber(), addressView.getPostalCode(), addressView.getStreetName(), addressView.getStatus());
    }
}
