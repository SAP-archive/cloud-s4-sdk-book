"use strict";

sap.ui.define([
], function () {
    return {
        /**
			 * Rounds the currency value to 2 digits
			 *
			 * @public
			 * @param {string} sValue value to be formatted
			 * @returns {string} formatted currency value with 2 digits
			 */
        currencyValue: function (sValue) {
            if (!sValue) {
                return "";
            }

            return parseFloat(sValue).toFixed(2);
        },

        creationDateValue: function (sTimestamp) {
            var i18n = this.getResourceBundle();
            return i18n.getText("createdOn", new Date(sTimestamp).toLocaleString("en-US", {
                weekday: "long",
                year: "numeric",
                month: "long",
                day: "numeric"
            }));
        },

        salutation: function (bIsMale, bIsFemale, sFirstName, sLastName) {
            var i18n = this.getResourceBundle();
            var salutation = "salutationNeutral";
            if (bIsMale) {
                salutation = "salutationMale";
            } else if (bIsFemale) {
                salutation = "salutationFemale";
            }

            return i18n.getText(salutation, [sFirstName, sLastName]);
        },

        masterPageTitle: function (aElements) {
            return this.getResourceBundle().getText("masterPageTitle", [aElements.length ? aElements.length : 0]);
        },

        addressListTitle: function (aAdresses) {
            return this.getResourceBundle().getText("addresses", [aAdresses && aAdresses.length ? aAdresses.length : 0]);
        },

        accountsListTitle: function (aAccounts) {
            return this.getResourceBundle().getText("socialMediaAccounts", [aAccounts && aAccounts.length ? aAccounts.length : 0]);
        },

        businessPartnerId: function (sBusinessPartnerId) {
            return this.getResourceBundle().getText("bupaId", [sBusinessPartnerId]);
        },

        lastCheckedBy: function(sLastCheckedByName) {
            if(!sLastCheckedByName) {
                return "";
            }
            return this.getResourceBundle().getText("lastCheckedBy", [sLastCheckedByName]);
        },

        lastCheckedDateValue: function (sTimestamp) {
            if(!sTimestamp) {
                return "";
            }
            var i18n = this.getResourceBundle();
            return i18n.getText("lastCheckedOn", new Date(sTimestamp.year, sTimestamp.month, sTimestamp.dayOfMonth).toLocaleString("en-US", {
                weekday: "long",
                year: "numeric",
                month: "long",
                day: "numeric"
            }));
        }
    };
});
