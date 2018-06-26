const data = require('./social-media-accounts-data.js').data;

module.exports = {
    data: data,
    getBusinessPartnerSocialMedia: function () {
        return this.data;
    },
    findBusinessPartnerSocialMedia: function (uuid) {
        return this.getBusinessPartnerSocialMedia().find(function (element) {
            return element.SAP_UUID == uuid;
        });
    },

    getSocialMediaAccounts: function() {
        return this.getBusinessPartnerSocialMedia()
            .map( (bupaSocMedia) => bupaSocMedia.to_SocialMediaAccount.results)
            .reduce( (acc,val) => acc.concat(val) , []);
    },
    findSocialMediaAccount: function(uuid) {
        return this.getSocialMediaAccounts().find(function(element) {
            return element.SAP_UUID == uuid;
        });
    }
};