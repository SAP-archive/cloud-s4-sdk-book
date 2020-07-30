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
var TimeSheetEntryRequestBuilder_1 = require("./TimeSheetEntryRequestBuilder");
var TimeSheetDataFields_1 = require("./TimeSheetDataFields");
var cloud_sdk_core_1 = require("@sap/cloud-sdk-core");
/**
 * This class represents the entity "TimeSheetEntryCollection" of service "API_MANAGE_WORKFORCE_TIMESHEET".
 */
var TimeSheetEntry = /** @class */ (function (_super) {
    __extends(TimeSheetEntry, _super);
    function TimeSheetEntry() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    /**
     * Returns an entity builder to construct instances `TimeSheetEntry`.
     * @returns A builder that constructs instances of entity type `TimeSheetEntry`.
     */
    TimeSheetEntry.builder = function () {
        return cloud_sdk_core_1.Entity.entityBuilder(TimeSheetEntry);
    };
    /**
     * Returns a request builder to construct requests for operations on the `TimeSheetEntry` entity type.
     * @returns A `TimeSheetEntry` request builder.
     */
    TimeSheetEntry.requestBuilder = function () {
        return new TimeSheetEntryRequestBuilder_1.TimeSheetEntryRequestBuilder();
    };
    /**
     * Returns a selectable object that allows the selection of custom field in a get request for the entity `TimeSheetEntry`.
     * @param fieldName Name of the custom field to select
     * @returns A builder that constructs instances of entity type `TimeSheetEntry`.
     */
    TimeSheetEntry.customField = function (fieldName) {
        return cloud_sdk_core_1.Entity.customFieldSelector(fieldName, TimeSheetEntry);
    };
    /**
     * Overwrites the default toJSON method so that all instance variables as well as all custom fields of the entity are returned.
     * @returns An object containing all instance variables + custom fields.
     */
    TimeSheetEntry.prototype.toJSON = function () {
        return __assign(__assign({}, this), this._customFields);
    };
    /**
     * Technical entity name for TimeSheetEntry.
     */
    TimeSheetEntry._entityName = 'TimeSheetEntryCollection';
    /**
     * @deprecated Since v1.0.1 Use [[_defaultServicePath]] instead.
     * Technical service name for TimeSheetEntry.
     */
    TimeSheetEntry._serviceName = 'API_MANAGE_WORKFORCE_TIMESHEET';
    /**
     * Default url path for the according service.
     */
    TimeSheetEntry._defaultServicePath = '/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET';
    return TimeSheetEntry;
}(cloud_sdk_core_1.Entity));
exports.TimeSheetEntry = TimeSheetEntry;
(function (TimeSheetEntry) {
    /**
     * Static representation of the [[timeSheetDataFields]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.TIME_SHEET_DATA_FIELDS = new TimeSheetDataFields_1.TimeSheetDataFieldsField('TimeSheetDataFields', TimeSheetEntry, 'TimeSheetDataFields');
    /**
     * Static representation of the [[personWorkAgreementExternalId]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.PERSON_WORK_AGREEMENT_EXTERNAL_ID = new cloud_sdk_core_1.StringField('PersonWorkAgreementExternalID', TimeSheetEntry, 'Edm.String');
    /**
     * Static representation of the [[companyCode]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.COMPANY_CODE = new cloud_sdk_core_1.StringField('CompanyCode', TimeSheetEntry, 'Edm.String');
    /**
     * Static representation of the [[timeSheetRecord]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.TIME_SHEET_RECORD = new cloud_sdk_core_1.StringField('TimeSheetRecord', TimeSheetEntry, 'Edm.String');
    /**
     * Static representation of the [[personWorkAgreement]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.PERSON_WORK_AGREEMENT = new cloud_sdk_core_1.StringField('PersonWorkAgreement', TimeSheetEntry, 'Edm.String');
    /**
     * Static representation of the [[timeSheetDate]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.TIME_SHEET_DATE = new cloud_sdk_core_1.DateField('TimeSheetDate', TimeSheetEntry, 'Edm.DateTime');
    /**
     * Static representation of the [[timeSheetIsReleasedOnSave]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.TIME_SHEET_IS_RELEASED_ON_SAVE = new cloud_sdk_core_1.BooleanField('TimeSheetIsReleasedOnSave', TimeSheetEntry, 'Edm.Boolean');
    /**
     * Static representation of the [[timeSheetPredecessorRecord]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.TIME_SHEET_PREDECESSOR_RECORD = new cloud_sdk_core_1.StringField('TimeSheetPredecessorRecord', TimeSheetEntry, 'Edm.String');
    /**
     * Static representation of the [[timeSheetStatus]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.TIME_SHEET_STATUS = new cloud_sdk_core_1.StringField('TimeSheetStatus', TimeSheetEntry, 'Edm.String');
    /**
     * Static representation of the [[timeSheetIsExecutedInTestRun]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.TIME_SHEET_IS_EXECUTED_IN_TEST_RUN = new cloud_sdk_core_1.BooleanField('TimeSheetIsExecutedInTestRun', TimeSheetEntry, 'Edm.Boolean');
    /**
     * Static representation of the [[timeSheetOperation]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    TimeSheetEntry.TIME_SHEET_OPERATION = new cloud_sdk_core_1.StringField('TimeSheetOperation', TimeSheetEntry, 'Edm.String');
    /**
     * All fields of the TimeSheetEntry entity.
     */
    TimeSheetEntry._allFields = [
        TimeSheetEntry.TIME_SHEET_DATA_FIELDS,
        TimeSheetEntry.PERSON_WORK_AGREEMENT_EXTERNAL_ID,
        TimeSheetEntry.COMPANY_CODE,
        TimeSheetEntry.TIME_SHEET_RECORD,
        TimeSheetEntry.PERSON_WORK_AGREEMENT,
        TimeSheetEntry.TIME_SHEET_DATE,
        TimeSheetEntry.TIME_SHEET_IS_RELEASED_ON_SAVE,
        TimeSheetEntry.TIME_SHEET_PREDECESSOR_RECORD,
        TimeSheetEntry.TIME_SHEET_STATUS,
        TimeSheetEntry.TIME_SHEET_IS_EXECUTED_IN_TEST_RUN,
        TimeSheetEntry.TIME_SHEET_OPERATION
    ];
    /**
     * All fields selector.
     */
    TimeSheetEntry.ALL_FIELDS = new cloud_sdk_core_1.AllFields('*', TimeSheetEntry);
    /**
     * All key fields of the TimeSheetEntry entity.
     */
    TimeSheetEntry._keyFields = [TimeSheetEntry.PERSON_WORK_AGREEMENT_EXTERNAL_ID, TimeSheetEntry.COMPANY_CODE, TimeSheetEntry.TIME_SHEET_RECORD];
    /**
     * Mapping of all key field names to the respective static field property TimeSheetEntry.
     */
    TimeSheetEntry._keys = TimeSheetEntry._keyFields.reduce(function (acc, field) {
        acc[field.fieldName] = field;
        return acc;
    }, {});
})(TimeSheetEntry = exports.TimeSheetEntry || (exports.TimeSheetEntry = {}));
exports.TimeSheetEntry = TimeSheetEntry;
//# sourceMappingURL=TimeSheetEntry.js.map