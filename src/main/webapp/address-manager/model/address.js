"use strict";

sap.ui.define([
], function () {
    return {
        create: function (oAddressData, sBusinessPartnerId) {
            if (oAddressData) {
                oAddressData.isNew = false;
                return oAddressData;
            }
            return {
                BusinessPartner: sBusinessPartnerId,
                CityName: "",
                Country: "",
                HouseNumber: "",
                PostalCode: "",
                StreetName: "",
                isNew: true
            };
        }
    };
});
