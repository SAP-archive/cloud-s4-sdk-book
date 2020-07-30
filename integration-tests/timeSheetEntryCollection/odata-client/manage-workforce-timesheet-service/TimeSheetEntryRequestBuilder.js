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
Object.defineProperty(exports, "__esModule", { value: true });
var cloud_sdk_core_1 = require("@sap/cloud-sdk-core");
var TimeSheetEntry_1 = require("./TimeSheetEntry");
/**
 * Request builder class for operations supported on the [[TimeSheetEntry]] entity.
 */
var TimeSheetEntryRequestBuilder = /** @class */ (function (_super) {
    __extends(TimeSheetEntryRequestBuilder, _super);
    function TimeSheetEntryRequestBuilder() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    /**
     * Returns a request builder for retrieving one `TimeSheetEntry` entity based on its keys.
     * @param personWorkAgreementExternalId Key property. See [[TimeSheetEntry.personWorkAgreementExternalId]].
     * @param companyCode Key property. See [[TimeSheetEntry.companyCode]].
     * @param timeSheetRecord Key property. See [[TimeSheetEntry.timeSheetRecord]].
     * @returns A request builder for creating requests to retrieve one `TimeSheetEntry` entity based on its keys.
     */
    TimeSheetEntryRequestBuilder.prototype.getByKey = function (personWorkAgreementExternalId, companyCode, timeSheetRecord) {
        return new cloud_sdk_core_1.GetByKeyRequestBuilder(TimeSheetEntry_1.TimeSheetEntry, {
            PersonWorkAgreementExternalID: personWorkAgreementExternalId,
            CompanyCode: companyCode,
            TimeSheetRecord: timeSheetRecord
        });
    };
    /**
     * Returns a request builder for querying all `TimeSheetEntry` entities.
     * @returns A request builder for creating requests to retrieve all `TimeSheetEntry` entities.
     */
    TimeSheetEntryRequestBuilder.prototype.getAll = function () {
        return new cloud_sdk_core_1.GetAllRequestBuilder(TimeSheetEntry_1.TimeSheetEntry);
    };
    /**
     * Returns a request builder for creating a `TimeSheetEntry` entity.
     * @param entity The entity to be created
     * @returns A request builder for creating requests that create an entity of type `TimeSheetEntry`.
     */
    TimeSheetEntryRequestBuilder.prototype.create = function (entity) {
        return new cloud_sdk_core_1.CreateRequestBuilder(TimeSheetEntry_1.TimeSheetEntry, entity);
    };
    return TimeSheetEntryRequestBuilder;
}(cloud_sdk_core_1.RequestBuilder));
exports.TimeSheetEntryRequestBuilder = TimeSheetEntryRequestBuilder;
//# sourceMappingURL=TimeSheetEntryRequestBuilder.js.map