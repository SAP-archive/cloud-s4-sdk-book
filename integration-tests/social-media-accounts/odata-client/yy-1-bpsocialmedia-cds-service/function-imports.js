"use strict";
/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
Object.defineProperty(exports, "__esModule", { value: true });
var cloud_sdk_core_1 = require("@sap/cloud-sdk-core");
var Yy1_Bpsocialmedia_1 = require("./Yy1_Bpsocialmedia");
/**
 * Yy 1 Bpsocialmedia Sap Upsert.
 *
 * @param parameters Object containing all parameters for the function import.
 * @returns A request builder that allows to overwrite some of the values and execute the resultng request.
 */
function yy1BpsocialmediaSapUpsert(parameters) {
    var params = {
        businessPartner: new cloud_sdk_core_1.FunctionImportParameter('BusinessPartner', 'Edm.String', parameters.businessPartner)
    };
    return new cloud_sdk_core_1.FunctionImportRequestBuilder('post', '/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS', 'YY1_BPSOCIALMEDIASap_upsert', function (data) { return cloud_sdk_core_1.transformReturnValueForEntity(data, Yy1_Bpsocialmedia_1.Yy1_Bpsocialmedia); }, params);
}
exports.yy1BpsocialmediaSapUpsert = yy1BpsocialmediaSapUpsert;
exports.functionImports = {
    yy1BpsocialmediaSapUpsert: yy1BpsocialmediaSapUpsert
};
//# sourceMappingURL=function-imports.js.map