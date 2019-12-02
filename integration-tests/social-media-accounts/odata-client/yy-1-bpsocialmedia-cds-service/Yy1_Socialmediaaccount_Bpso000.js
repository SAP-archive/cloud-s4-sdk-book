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
var Yy1_Socialmediaaccount_Bpso000RequestBuilder_1 = require("./Yy1_Socialmediaaccount_Bpso000RequestBuilder");
var cloud_sdk_core_1 = require("@sap/cloud-sdk-core");
/**
 * This class represents the entity "YY1_SOCIALMEDIAACCOUNT_BPSO000" of service "YY1_BPSOCIALMEDIA_CDS".
 */
var Yy1_Socialmediaaccount_Bpso000 = /** @class */ (function (_super) {
    __extends(Yy1_Socialmediaaccount_Bpso000, _super);
    function Yy1_Socialmediaaccount_Bpso000() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    /**
     * Returns an entity builder to construct instances `Yy1_Socialmediaaccount_Bpso000`.
     * @returns A builder that constructs instances of entity type `Yy1_Socialmediaaccount_Bpso000`.
     */
    Yy1_Socialmediaaccount_Bpso000.builder = function () {
        return cloud_sdk_core_1.Entity.entityBuilder(Yy1_Socialmediaaccount_Bpso000);
    };
    /**
     * Returns a request builder to construct requests for operations on the `Yy1_Socialmediaaccount_Bpso000` entity type.
     * @returns A `Yy1_Socialmediaaccount_Bpso000` request builder.
     */
    Yy1_Socialmediaaccount_Bpso000.requestBuilder = function () {
        return new Yy1_Socialmediaaccount_Bpso000RequestBuilder_1.Yy1_Socialmediaaccount_Bpso000RequestBuilder();
    };
    /**
     * Returns a selectable object that allows the selection of custom field in a get request for the entity `Yy1_Socialmediaaccount_Bpso000`.
     * @param fieldName Name of the custom field to select
     * @returns A builder that constructs instances of entity type `Yy1_Socialmediaaccount_Bpso000`.
     */
    Yy1_Socialmediaaccount_Bpso000.customField = function (fieldName) {
        return cloud_sdk_core_1.Entity.customFieldSelector(fieldName, Yy1_Socialmediaaccount_Bpso000);
    };
    /**
     * Overwrites the default toJSON method so that all instance variables as well as all custom fields of the entity are returned.
     * @returns An object containing all instance variables + custom fields.
     */
    Yy1_Socialmediaaccount_Bpso000.prototype.toJSON = function () {
        return __assign(__assign({}, this), this._customFields);
    };
    /**
     * Technical entity name for Yy1_Socialmediaaccount_Bpso000.
     */
    Yy1_Socialmediaaccount_Bpso000._entityName = 'YY1_SOCIALMEDIAACCOUNT_BPSO000';
    /**
     * @deprecated Since v1.0.1 Use [[_defaultServicePath]] instead.
     * Technical service name for Yy1_Socialmediaaccount_Bpso000.
     */
    Yy1_Socialmediaaccount_Bpso000._serviceName = 'YY1_BPSOCIALMEDIA_CDS';
    /**
     * Default url path for the according service.
     */
    Yy1_Socialmediaaccount_Bpso000._defaultServicePath = '/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS';
    return Yy1_Socialmediaaccount_Bpso000;
}(cloud_sdk_core_1.Entity));
exports.Yy1_Socialmediaaccount_Bpso000 = Yy1_Socialmediaaccount_Bpso000;
var Yy1_Bpsocialmedia_1 = require("./Yy1_Bpsocialmedia");
(function (Yy1_Socialmediaaccount_Bpso000) {
    /**
     * Static representation of the [[sapUuid]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    Yy1_Socialmediaaccount_Bpso000.SAP_UUID = new cloud_sdk_core_1.StringField('SAP_UUID', Yy1_Socialmediaaccount_Bpso000, 'Edm.Guid');
    /**
     * Static representation of the [[sapParentUuid]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    Yy1_Socialmediaaccount_Bpso000.SAP_PARENT_UUID = new cloud_sdk_core_1.StringField('SAP_PARENT_UUID', Yy1_Socialmediaaccount_Bpso000, 'Edm.Guid');
    /**
     * Static representation of the [[service]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    Yy1_Socialmediaaccount_Bpso000.SERVICE = new cloud_sdk_core_1.StringField('Service', Yy1_Socialmediaaccount_Bpso000, 'Edm.String');
    /**
     * Static representation of the [[account]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    Yy1_Socialmediaaccount_Bpso000.ACCOUNT = new cloud_sdk_core_1.StringField('Account', Yy1_Socialmediaaccount_Bpso000, 'Edm.String');
    /**
     * Static representation of the one-to-one navigation property [[toBpsocialmedia]] for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    Yy1_Socialmediaaccount_Bpso000.TO_BPSOCIALMEDIA = new cloud_sdk_core_1.OneToOneLink('to_BPSOCIALMEDIA', Yy1_Socialmediaaccount_Bpso000, Yy1_Bpsocialmedia_1.Yy1_Bpsocialmedia);
    /**
     * All fields of the Yy1_Socialmediaaccount_Bpso000 entity.
     */
    Yy1_Socialmediaaccount_Bpso000._allFields = [
        Yy1_Socialmediaaccount_Bpso000.SAP_UUID,
        Yy1_Socialmediaaccount_Bpso000.SAP_PARENT_UUID,
        Yy1_Socialmediaaccount_Bpso000.SERVICE,
        Yy1_Socialmediaaccount_Bpso000.ACCOUNT,
        Yy1_Socialmediaaccount_Bpso000.TO_BPSOCIALMEDIA
    ];
    /**
     * All fields selector.
     */
    Yy1_Socialmediaaccount_Bpso000.ALL_FIELDS = new cloud_sdk_core_1.AllFields('*', Yy1_Socialmediaaccount_Bpso000);
    /**
     * All key fields of the Yy1_Socialmediaaccount_Bpso000 entity.
     */
    Yy1_Socialmediaaccount_Bpso000._keyFields = [Yy1_Socialmediaaccount_Bpso000.SAP_UUID];
    /**
     * Mapping of all key field names to the respective static field property Yy1_Socialmediaaccount_Bpso000.
     */
    Yy1_Socialmediaaccount_Bpso000._keys = Yy1_Socialmediaaccount_Bpso000._keyFields.reduce(function (acc, field) {
        acc[field.fieldName] = field;
        return acc;
    }, {});
})(Yy1_Socialmediaaccount_Bpso000 = exports.Yy1_Socialmediaaccount_Bpso000 || (exports.Yy1_Socialmediaaccount_Bpso000 = {}));
exports.Yy1_Socialmediaaccount_Bpso000 = Yy1_Socialmediaaccount_Bpso000;
//# sourceMappingURL=Yy1_Socialmediaaccount_Bpso000.js.map