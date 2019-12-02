"use strict";
/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
Object.defineProperty(exports, "__esModule", { value: true });
var cloud_sdk_core_1 = require("@sap/cloud-sdk-core");
var index_1 = require("./index");
/**
 * Batch builder for operations supported on the [[Yy1BpsocialmediaCdsService]] service.
 * @param requests The requests of the batch
 * @returns A request builder for batch.
 */
function batch() {
    var requests = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        requests[_i] = arguments[_i];
    }
    return new cloud_sdk_core_1.ODataBatchRequestBuilder(exports.defaultYy1BpsocialmediaCdsServicePath, requests, map);
}
exports.batch = batch;
/**
 * Change set constructor consists of write operations supported on the [[Yy1BpsocialmediaCdsService]] service.
 * @param requests The requests of the change set
 * @returns A change set for batch.
 */
function changeset() {
    var requests = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        requests[_i] = arguments[_i];
    }
    return new cloud_sdk_core_1.ODataBatchChangeSet(requests);
}
exports.changeset = changeset;
exports.defaultYy1BpsocialmediaCdsServicePath = '/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS';
var map = { 'YY1_BPSOCIALMEDIAType': index_1.Yy1_Bpsocialmedia, 'YY1_SOCIALMEDIAACCOUNT_BPSO000Type': index_1.Yy1_Socialmediaaccount_Bpso000 };
//# sourceMappingURL=BatchRequest.js.map