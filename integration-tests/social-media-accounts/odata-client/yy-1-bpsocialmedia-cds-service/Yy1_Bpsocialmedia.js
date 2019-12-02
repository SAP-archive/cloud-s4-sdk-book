"use strict";
/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
Object.defineProperty(exports, "__esModule", { value: true });
var Yy1_BpsocialmediaRequestBuilder_1 = require("./Yy1_BpsocialmediaRequestBuilder");
var cloud_sdk_core_1 = require("@sap/cloud-sdk-core");
/**
 * This class represents the entity "YY1_BPSOCIALMEDIA" of service "YY1_BPSOCIALMEDIA_CDS".
 */
var Yy1_Bpsocialmedia = /** @class */ (function (_super) {
    __extends(Yy1_Bpsocialmedia, _super);
    function Yy1_Bpsocialmedia() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    /**
     * Returns an entity builder to construct instances `Yy1_Bpsocialmedia`.
     * @returns A builder that constructs instances of entity type `Yy1_Bpsocialmedia`.
     */
    Yy1_Bpsocialmedia.builder = function () {
        return cloud_sdk_core_1.Entity.entityBuilder(Yy1_Bpsocialmedia);
    };
    /**
     * Returns a request builder to construct requests for operations on the `Yy1_Bpsocialmedia` entity type.
     * @returns A `Yy1_Bpsocialmedia` request builder.
     */
    Yy1_Bpsocialmedia.requestBuilder = function () {
        return new Yy1_BpsocialmediaRequestBuilder_1.Yy1_BpsocialmediaRequestBuilder();
    };
    /**
     * Returns a selectable object that allows the selection of custom field in a get request for the entity `Yy1_Bpsocialmedia`.
     * @param fieldName Name of the custom field to select
     * @returns A builder that constructs instances of entity type `Yy1_Bpsocialmedia`.
     */
    Yy1_Bpsocialmedia.customField = function (fieldName) {
        return cloud_sdk_core_1.Entity.customFieldSelector(fieldName, Yy1_Bpsocialmedia);
    };
    /**
     * Overwrites the default toJSON method so that all instance variables as well as all custom fields of the entity are returned.
     * @returns An object containing all instance variables + custom fields.
     */
    Yy1_Bpsocialmedia.prototype.toJSON = function () {
        return __assign(__assign({}, this), this._customFields);
    };
    /**
     * Technical entity name for Yy1_Bpsocialmedia.
     */
    Yy1_Bpsocialmedia._entityName = 'YY1_BPSOCIALMEDIA';
    /**
     * @deprecated Since v1.0.1 Use [[_defaultServicePath]] instead.
     * Technical service name for Yy1_Bpsocialmedia.
     */
    Yy1_Bpsocialmedia._serviceName = 'YY1_BPSOCIALMEDIA_CDS';
    /**
     * Default url path for the according service.
     */
    Yy1_Bpsocialmedia._defaultServicePath = '/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS';
    return Yy1_Bpsocialmedia;
}(cloud_sdk_core_1.Entity));
exports.Yy1_Bpsocialmedia = Yy1_Bpsocialmedia;
var Yy1_Socialmediaaccount_Bpso000_1 = require("./Yy1_Socialmediaaccount_Bpso000");
(function (Yy1_Bpsocialmedia) {
    /**
     * Static representation of the [[sapUuid]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    Yy1_Bpsocialmedia.SAP_UUID = new cloud_sdk_core_1.StringField('SAP_UUID', Yy1_Bpsocialmedia, 'Edm.Guid');
    /**
     * Static representation of the [[businessPartner]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    Yy1_Bpsocialmedia.BUSINESS_PARTNER = new cloud_sdk_core_1.StringField('BusinessPartner', Yy1_Bpsocialmedia, 'Edm.String');
    /**
     * Static representation of the one-to-many navigation property [[toSocialMediaAccount]] for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    Yy1_Bpsocialmedia.TO_SOCIAL_MEDIA_ACCOUNT = new cloud_sdk_core_1.Link('to_SocialMediaAccount', Yy1_Bpsocialmedia, Yy1_Socialmediaaccount_Bpso000_1.Yy1_Socialmediaaccount_Bpso000);
    /**
     * All fields of the Yy1_Bpsocialmedia entity.
     */
    Yy1_Bpsocialmedia._allFields = [
        Yy1_Bpsocialmedia.SAP_UUID,
        Yy1_Bpsocialmedia.BUSINESS_PARTNER,
        Yy1_Bpsocialmedia.TO_SOCIAL_MEDIA_ACCOUNT
    ];
    /**
     * All fields selector.
     */
    Yy1_Bpsocialmedia.ALL_FIELDS = new cloud_sdk_core_1.AllFields('*', Yy1_Bpsocialmedia);
    /**
     * All key fields of the Yy1_Bpsocialmedia entity.
     */
    Yy1_Bpsocialmedia._keyFields = [Yy1_Bpsocialmedia.SAP_UUID];
    /**
     * Mapping of all key field names to the respective static field property Yy1_Bpsocialmedia.
     */
    Yy1_Bpsocialmedia._keys = Yy1_Bpsocialmedia._keyFields.reduce(function (acc, field) {
        acc[field.fieldName] = field;
        return acc;
    }, {});
})(Yy1_Bpsocialmedia = exports.Yy1_Bpsocialmedia || (exports.Yy1_Bpsocialmedia = {}));
exports.Yy1_Bpsocialmedia = Yy1_Bpsocialmedia;
//# sourceMappingURL=Yy1_Bpsocialmedia.js.map