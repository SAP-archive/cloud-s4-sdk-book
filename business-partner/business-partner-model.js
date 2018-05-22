const uuid = require('uuid/v1');
const data = require('./business-partner-data.js').data;

const today = function () {
    const time = Date.now();
    return time - (time % 24 * 60 * 60 * 1000);
}

const nextBusinessPartnerId = function(existingBusinessPartners) {
    return String(Math.max(...existingBusinessPartners.map( (item) => Number(item.BusinessPartner) )) + 1);
};

const nextAddressId = function(existingAddresses) {
    return String(Math.max(...existingAddresses.map( (item) => Number(item.AddressID) )) + 1);
};

module.exports = {
    data: data,
    newBusinessPartner: function (id) {
        return Object.seal({
            "__metadata": {
                "id": `https://{host}:{port}/sap/opu/odata/sap/API_BUSINESS_PARTNER/A_BusinessPartner(${id})`,
                "uri": `https://{host}:{port}/sap/opu/odata/sap/API_BUSINESS_PARTNER/A_BusinessPartner('${id}')`,
                "type": "API_BUSINESS_PARTNER.A_BusinessPartnerType"
            },
            "BusinessPartner": id,
            "Customer": "",
            "Supplier": "",
            "AcademicTitle": "",
            "AuthorizationGroup": "",
            "BusinessPartnerCategory": "",
            "BusinessPartnerFullName": "",
            "BusinessPartnerGrouping": "BP02",
            "BusinessPartnerName": "",
            "BusinessPartnerUUID": uuid(),
            "CorrespondenceLanguage": "",
            "CreatedByUser": "ANONYMOUS001",
            "CreationDate": `/Date(${today()})/`,
            "CreationTime": "PT17H49M05S",
            "FirstName": "",
            "FormOfAddress": "",
            "Industry": "",
            "InternationalLocationNumber1": "0",
            "InternationalLocationNumber2": "0",
            "IsFemale": false,
            "IsMale": false,
            "IsNaturalPerson": "",
            "IsSexUnknown": true,
            "Language": "",
            "LastChangeDate": null,
            "LastChangeTime": "PT00H00M00S",
            "LastChangedByUser": "",
            "LastName": "Doe",
            "LegalForm": "",
            "OrganizationBPName1": "",
            "OrganizationBPName2": "",
            "OrganizationBPName3": "",
            "OrganizationBPName4": "",
            "OrganizationFoundationDate": null,
            "OrganizationLiquidationDate": null,
            "SearchTerm1": "",
            "AdditionalLastName": "",
            "BirthDate": null,
            "BusinessPartnerIsBlocked": false,
            "BusinessPartnerType": "",
            "ETag": "",
            "GroupBusinessPartnerName1": "",
            "GroupBusinessPartnerName2": "",
            "IndependentAddressID": "",
            "InternationalLocationNumber3": "0",
            "MiddleName": "",
            "NameCountry": "",
            "NameFormat": "",
            "PersonFullName": "",
            "PersonNumber": "",
            "IsMarkedForArchiving": false,
            "BusinessPartnerIDByExtSystem": "",
            "YY1_AddrLastCheckedOn_bus": null,
            "YY1_AddrLastCheckedBy_bus": "",
            "to_BuPaIdentification": {
                "results": []
            },
            "to_BusinessPartnerAddress": {
                "results": []
            },
            "to_BusinessPartnerBank": {
                "results": []
            },
            "to_BusinessPartnerContact": {
                "results": []
            },
            "to_BusinessPartnerRole": {
                "results": []
            },
            "to_BusinessPartnerTax": {
                "results": []
            },
            "to_Customer": null,
            "to_Supplier": null
        });
    },
    getBusinessPartners: function () {
        return this.data;
    },
    findBusinessPartner: function (id) {
        return this.getBusinessPartners().find(function (element) {
            return element.BusinessPartner == id;
        });
    },
    createAndAddBusinessPartner: function(businessPartnerInput) {
        const newId = nextBusinessPartnerId(this.getBusinessPartners());
        const newBusinessPartner = this.newBusinessPartner(newId);
        Object.assign(newBusinessPartner, businessPartnerInput);
        this.getBusinessPartners().push(newBusinessPartner);
        return newBusinessPartner;
    },
    deleteBusinessPartner: function(id) {
        if(!this.findBusinessPartner(id)) {
            throw new Error(`Cannot delete business partner: business partner with ID ${id} does not exist.`);
        }
        this.data = this.getBusinessPartners()
            .filter( (bupa) => bupa.BusinessPartner != id );
    },
    modifyBusinessPartner: function(id, businessPartnerInput) {
        const businessPartnerToUpdate = this.findBusinessPartner(id);
        if(!businessPartnerToUpdate) {
            throw new Error(`Cannot modify business partner: business partner with ID ${id} does not exist.`);
        }
        if(businessPartnerInput.BusinessPartner && businessPartnerInput.BusinessPartner != id) {
            throw new Error(`Cannot modify business partner: identifier must not be changed`);
        }
        Object.assign(businessPartnerToUpdate, businessPartnerInput);
    },

    getAddresses: function() {
        return this.getBusinessPartners()
            .map( (bupa) => bupa.to_BusinessPartnerAddress.results)
            .reduce( (acc,val) => acc.concat(val) , []);
    },
    findAddress: function(businessPartnerId, addressId) {
        return this.getAddresses().find(function(element) {
            return element.BusinessPartner == businessPartnerId &&
                   element.AddressID == addressId;
        });
    },
    newAddress: function(businessPartnerId, addressId) {
        const businessPartner = this.findBusinessPartner(businessPartnerId);
        if(!businessPartner) {
            throw new Error(`Cannot create address: associated business partner with ID ${businessPartnerId} does not exist.`);
        }
        return Object.seal({
            "__metadata": {
                "id": `https://{host}:{port}/sap/opu/odata/sap/API_BUSINESS_PARTNER/A_BusinessPartnerAddress(BusinessPartner='${businessPartnerId}',AddressID='${addressId}')`,
                "uri": `https://{host}:{port}/sap/opu/odata/sap/API_BUSINESS_PARTNER/A_BusinessPartnerAddress(BusinessPartner='${businessPartnerId}',AddressID='${addressId}')`,
                "type": "API_BUSINESS_PARTNER.A_BusinessPartnerAddressType"
            },
            "BusinessPartner": businessPartnerId,
            "AddressID": addressId,
            "ValidityStartDate": "/Date(1518393600000+0000)/",
            "ValidityEndDate": "/Date(253402300799000+0000)/",
            "AuthorizationGroup": "",
            "AddressUUID": uuid(),
            "AdditionalStreetPrefixName": "",
            "AdditionalStreetSuffixName": "",
            "AddressTimeZone": "CET",
            "CareOfName": "",
            "CityCode": "",
            "CityName": "",
            "CompanyPostalCode": "",
            "Country": "",
            "County": "",
            "DeliveryServiceNumber": "",
            "DeliveryServiceTypeCode": "",
            "District": "",
            "FormOfAddress": "",
            "FullName": "",
            "HomeCityName": "",
            "HouseNumber": "",
            "HouseNumberSupplementText": "",
            "Language": "",
            "POBox": "",
            "POBoxDeviatingCityName": "",
            "POBoxDeviatingCountry": "",
            "POBoxDeviatingRegion": "",
            "POBoxIsWithoutNumber": false,
            "POBoxLobbyName": "",
            "POBoxPostalCode": "",
            "Person": businessPartner.PersonNumber,
            "PostalCode": "",
            "PrfrdCommMediumType": "",
            "Region": "",
            "StreetName": "",
            "StreetPrefixName": "",
            "StreetSuffixName": "",
            "TaxJurisdiction": "",
            "TransportZone": "",
            "AddressIDByExternalSystem": "",
            "to_AddressUsage": {
                "results": []
            },
            "to_EmailAddress": {
                "results": []
            },
            "to_FaxNumber": {
                "results": []
            },
            "to_MobilePhoneNumber": {
                "results": []
            },
            "to_PhoneNumber": {
                "results": []
            },
            "to_URLAddress": {
                "results": []
            }
        });
    },
    createAndAddAddress: function(addressInput) {
        const newAddressId = nextAddressId(this.getAddresses());
        const newAddress = this.newAddress(addressInput.BusinessPartner, newAddressId);
        Object.assign(newAddress, addressInput);
        this.findBusinessPartner(newAddress.BusinessPartner).to_BusinessPartnerAddress.results.push(newAddress);
        return newAddress;
    },
    deleteAddress: function(businessPartnerId, addressId) {
        const businessPartner = this.findBusinessPartner(businessPartnerId);
        if(!businessPartner || !this.findAddress(businessPartnerId, addressId)) {
            throw new Error(`Cannot delete address: address with key (${businessPartnerId},${addressId}) does not exist.`);
        }
        businessPartner.to_BusinessPartnerAddress.results = businessPartner.to_BusinessPartnerAddress.results
            .filter( (address) => address.AddressID != addressId);
    },
    modifyAddress: function(businessPartnerId, addressId, addressInput) {
        const addressToUpdate = this.findAddress(businessPartnerId, addressId);
        if(!addressToUpdate) {
            throw new Error(`Cannot modify address: address with key (${businessPartnerId},${addressId}) does not exist.`);
        }
        if((addressInput.BusinessPartner && addressInput.BusinessPartner != businessPartnerId) ||
                 (addressInput.AddressID && addressInput.AddressID != addressId)) {
            throw new Error(`Cannot modify address: key fields must not be changed`);
        }
        Object.assign(addressToUpdate, addressInput);
    }
};