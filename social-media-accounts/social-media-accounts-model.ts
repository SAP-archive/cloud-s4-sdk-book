import * as common from '../common-types';

const initialData = require('./social-media-accounts-data.js').data;

export interface CustomBusinessObject {
    SAP_UUID: string;
}

export interface BusinessPartnerSocialMedia extends CustomBusinessObject {
    to_SocialMediaAccount: common.ResultWrapper<SocialMediaAccount>;
}

export interface SocialMediaAccount extends CustomBusinessObject {
}

export class SocialMediaAccountStore {
    data: BusinessPartnerSocialMedia[];
    constructor(initialData: BusinessPartnerSocialMedia[]) {
        this.data = initialData;
    }

    getBusinessPartnerSocialMedia() {
        return this.data;
    }
    findBusinessPartnerSocialMedia(uuid: string) {
        return this.getBusinessPartnerSocialMedia().find(function (element) {
            return element.SAP_UUID == uuid;
        });
    }

    getSocialMediaAccounts() {
        return this.getBusinessPartnerSocialMedia()
            .map( (bupaSocMedia) => bupaSocMedia.to_SocialMediaAccount.results)
            .reduce( (acc,val) => acc.concat(val) , []);
    }
    findSocialMediaAccount(uuid: string) {
        return this.getSocialMediaAccounts().find(function(element) {
            return element.SAP_UUID == uuid;
        });
    }
};

export const socialMediaAccountStore = new SocialMediaAccountStore(initialData);