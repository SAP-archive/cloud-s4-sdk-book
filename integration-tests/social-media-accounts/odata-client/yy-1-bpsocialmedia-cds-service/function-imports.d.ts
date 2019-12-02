/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
import { FunctionImportRequestBuilder } from '@sap/cloud-sdk-core';
import { Yy1_Bpsocialmedia } from './Yy1_Bpsocialmedia';
/**
 * Type of the parameters to be passed to [[yy1BpsocialmediaSapUpsert]].
 */
export interface Yy1BpsocialmediaSapUpsertParameters {
    /**
     * Business Partner.
     */
    businessPartner: string;
}
/**
 * Yy 1 Bpsocialmedia Sap Upsert.
 *
 * @param parameters Object containing all parameters for the function import.
 * @returns A request builder that allows to overwrite some of the values and execute the resultng request.
 */
export declare function yy1BpsocialmediaSapUpsert(parameters: Yy1BpsocialmediaSapUpsertParameters): FunctionImportRequestBuilder<Yy1BpsocialmediaSapUpsertParameters, Yy1_Bpsocialmedia>;
export declare const functionImports: {
    yy1BpsocialmediaSapUpsert: typeof yy1BpsocialmediaSapUpsert;
};
//# sourceMappingURL=function-imports.d.ts.map