/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */

import { transformReturnValueForEntity, FunctionImportRequestBuilder, FunctionImportParameter } from '@sap/cloud-sdk-core';
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
export function yy1BpsocialmediaSapUpsert(parameters: Yy1BpsocialmediaSapUpsertParameters): FunctionImportRequestBuilder<Yy1BpsocialmediaSapUpsertParameters, Yy1_Bpsocialmedia> {
  const params = {
    businessPartner: new FunctionImportParameter('BusinessPartner', 'Edm.String', parameters.businessPartner)
  }

  return new FunctionImportRequestBuilder('post', '/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS', 'YY1_BPSOCIALMEDIASap_upsert', (data) => transformReturnValueForEntity(data, Yy1_Bpsocialmedia), params);
}

export const functionImports = {
  yy1BpsocialmediaSapUpsert
};
