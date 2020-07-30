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
export declare function batch(...requests: Array<ReadManageWorkforceTimesheetServiceRequestBuilder | ODataBatchChangeSet>): ODataBatchRequestBuilder;
/**
 * Change set constructor consists of write operations supported on the [[ManageWorkforceTimesheetService]] service.
 * @param requests The requests of the change set
 * @returns A change set for batch.
 */
export declare function changeset(...requests: WriteManageWorkforceTimesheetServiceRequestBuilder[]): ODataBatchChangeSet;
export declare const defaultManageWorkforceTimesheetServicePath = "/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET";
export declare type ReadManageWorkforceTimesheetServiceRequestBuilder = GetAllRequestBuilder<TimeSheetEntry> | GetByKeyRequestBuilder<TimeSheetEntry>;
export declare type WriteManageWorkforceTimesheetServiceRequestBuilder = CreateRequestBuilder<TimeSheetEntry> | UpdateRequestBuilder<TimeSheetEntry> | DeleteRequestBuilder<TimeSheetEntry>;
//# sourceMappingURL=BatchRequest.d.ts.map