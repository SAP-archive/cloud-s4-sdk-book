/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
import { RequestBuilder, GetAllRequestBuilder, GetByKeyRequestBuilder, CreateRequestBuilder } from '@sap/cloud-sdk-core';
import { TimeSheetEntry } from './TimeSheetEntry';
/**
 * Request builder class for operations supported on the [[TimeSheetEntry]] entity.
 */
export declare class TimeSheetEntryRequestBuilder extends RequestBuilder<TimeSheetEntry> {
    /**
     * Returns a request builder for retrieving one `TimeSheetEntry` entity based on its keys.
     * @param personWorkAgreementExternalId Key property. See [[TimeSheetEntry.personWorkAgreementExternalId]].
     * @param companyCode Key property. See [[TimeSheetEntry.companyCode]].
     * @param timeSheetRecord Key property. See [[TimeSheetEntry.timeSheetRecord]].
     * @returns A request builder for creating requests to retrieve one `TimeSheetEntry` entity based on its keys.
     */
    getByKey(personWorkAgreementExternalId: string, companyCode: string, timeSheetRecord: string): GetByKeyRequestBuilder<TimeSheetEntry>;
    /**
     * Returns a request builder for querying all `TimeSheetEntry` entities.
     * @returns A request builder for creating requests to retrieve all `TimeSheetEntry` entities.
     */
    getAll(): GetAllRequestBuilder<TimeSheetEntry>;
    /**
     * Returns a request builder for creating a `TimeSheetEntry` entity.
     * @param entity The entity to be created
     * @returns A request builder for creating requests that create an entity of type `TimeSheetEntry`.
     */
    create(entity: TimeSheetEntry): CreateRequestBuilder<TimeSheetEntry>;
}
//# sourceMappingURL=TimeSheetEntryRequestBuilder.d.ts.map