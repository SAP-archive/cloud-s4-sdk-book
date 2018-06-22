"use strict";

sap.ui.define([
], function () {
    var api = {
        getSocialMediaAccountsUrl: function (businessPartnerId) {
            var accountsUrl = "/api/social-media-accounts";
            if (businessPartnerId) {
                return accountsUrl + "?id=" + businessPartnerId;
            }
            return accountsUrl;
        },

        getSocialMediaAccounts: function (businessPartnerId) {
            return jQuery.get(this.getSocialMediaAccountsUrl(businessPartnerId));
        }
    };
    return api;
}, true);
