/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */

import { GetAllRequestBuilder, GetByKeyRequestBuilder, CreateRequestBuilder, UpdateRequestBuilder, DeleteRequestBuilder, ODataBatchRequestBuilder, ODataBatchChangeSet } from '@sap/cloud-sdk-core';
import { Yy1_Bpsocialmedia, Yy1_Socialmediaaccount_Bpso000 } from './index';

/**
 * Batch builder for operations supported on the [[Yy1BpsocialmediaCdsService]] service.
 * @param requests The requests of the batch
 * @returns A request builder for batch.
 */
export function batch(...requests: Array<ReadYy1BpsocialmediaCdsServiceRequestBuilder | ODataBatchChangeSet>): ODataBatchRequestBuilder {
  return new ODataBatchRequestBuilder(defaultYy1BpsocialmediaCdsServicePath, requests, map);
}

/**
 * Change set constructor consists of write operations supported on the [[Yy1BpsocialmediaCdsService]] service.
 * @param requests The requests of the change set
 * @returns A change set for batch.
 */
export function changeset(...requests: WriteYy1BpsocialmediaCdsServiceRequestBuilder[]): ODataBatchChangeSet {
  return new ODataBatchChangeSet(requests);
}

export const defaultYy1BpsocialmediaCdsServicePath = '/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS';
const map = { 'YY1_BPSOCIALMEDIAType': Yy1_Bpsocialmedia, 'YY1_SOCIALMEDIAACCOUNT_BPSO000Type': Yy1_Socialmediaaccount_Bpso000 };
export type ReadYy1BpsocialmediaCdsServiceRequestBuilder = GetAllRequestBuilder<Yy1_Bpsocialmedia> | GetAllRequestBuilder<Yy1_Socialmediaaccount_Bpso000> | GetByKeyRequestBuilder<Yy1_Bpsocialmedia> | GetByKeyRequestBuilder<Yy1_Socialmediaaccount_Bpso000>;
export type WriteYy1BpsocialmediaCdsServiceRequestBuilder = CreateRequestBuilder<Yy1_Bpsocialmedia> | UpdateRequestBuilder<Yy1_Bpsocialmedia> | DeleteRequestBuilder<Yy1_Bpsocialmedia> | CreateRequestBuilder<Yy1_Socialmediaaccount_Bpso000> | UpdateRequestBuilder<Yy1_Socialmediaaccount_Bpso000> | DeleteRequestBuilder<Yy1_Socialmediaaccount_Bpso000>;
