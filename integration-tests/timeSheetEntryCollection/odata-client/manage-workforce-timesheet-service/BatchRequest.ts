/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */

import { GetAllRequestBuilder, GetByKeyRequestBuilder, CreateRequestBuilder, UpdateRequestBuilder, DeleteRequestBuilder, ODataBatchRequestBuilder, ODataBatchChangeSet } from '@sap/cloud-sdk-core';
import { TimeSheetEntry } from './index';

/**
 * Batch builder for operations supported on the [[ManageWorkforceTimesheetService]] service.
 * @param requests The requests of the batch
 * @returns A request builder for batch.
 */
export function batch(...requests: Array<ReadManageWorkforceTimesheetServiceRequestBuilder | ODataBatchChangeSet>): ODataBatchRequestBuilder {
  return new ODataBatchRequestBuilder(defaultManageWorkforceTimesheetServicePath, requests, map);
}

/**
 * Change set constructor consists of write operations supported on the [[ManageWorkforceTimesheetService]] service.
 * @param requests The requests of the change set
 * @returns A change set for batch.
 */
export function changeset(...requests: WriteManageWorkforceTimesheetServiceRequestBuilder[]): ODataBatchChangeSet {
  return new ODataBatchChangeSet(requests);
}

export const defaultManageWorkforceTimesheetServicePath = '/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET';
const map = { 'TimeSheetEntry': TimeSheetEntry };
export type ReadManageWorkforceTimesheetServiceRequestBuilder = GetAllRequestBuilder<TimeSheetEntry> | GetByKeyRequestBuilder<TimeSheetEntry>;
export type WriteManageWorkforceTimesheetServiceRequestBuilder = CreateRequestBuilder<TimeSheetEntry> | UpdateRequestBuilder<TimeSheetEntry> | DeleteRequestBuilder<TimeSheetEntry>;
