"use strict";

sap.ui.define([
], function () {
    var api = {
        getBusinessPartnerUrl: function (businessPartnerId) {
            var businessPartnerUrl = "/api/business-partners";
            if (businessPartnerId) {
                return businessPartnerUrl + "/" + businessPartnerId;
            }
            return businessPartnerUrl;
        },

        getBusinessPartner: function (businessPartnerId) {
            return jQuery.get(this.getBusinessPartnerUrl(businessPartnerId));
        },

        getAddressUrl: function (businessPartnerId, addressId) {
            var addressUrl = this.getBusinessPartnerUrl(businessPartnerId) + "/addresses";
            if (addressId) {
                addressUrl += "/" + addressId;
            }
            return addressUrl;
        },

        updateAddress: function (businessPartnerId, addressId, newAddress) {
            delete newAddress.isNew;
            return jQuery.ajax({
                url: this.getAddressUrl(businessPartnerId, addressId),
                method: "PUT",
                contentType: "application/json",
                data: JSON.stringify(newAddress)
            });
        },

        deleteAddress: function (businessPartnerId, addressId) {
            return jQuery.ajax({
                url: this.getAddressUrl(businessPartnerId, addressId),
                method: "DELETE"
            });
        },

        createAddress: function (address) {
            delete address.isNew;
            return jQuery.ajax({
                url: this.getAddressUrl(address.BusinessPartner),
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(address)
            });
        },

        fetchCSRFToken: function () {
            return jQuery.get({
                url: "/api/",
                headers: { "X-CSRF-Token": "Fetch" }
            }).always(function (response) {
                var csrfToken = response.getResponseHeader("x-csrf-token");
                jQuery.ajaxPrefilter(function (options, originalOptions, _jqXHR) {
                    if (["POST", "PUT", "DELETE", "PATCH"].indexOf(options.type) !== -1) {
                        _jqXHR.setRequestHeader("X-CSRF-Token", csrfToken);
                    }
                });
            });
        }
    };
    api.fetchCSRFToken();
    return api;
}, true);
