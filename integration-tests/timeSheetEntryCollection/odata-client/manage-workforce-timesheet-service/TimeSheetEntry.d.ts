/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
import { TimeSheetEntryRequestBuilder } from './TimeSheetEntryRequestBuilder';
import { Moment } from 'moment';
import { TimeSheetDataFields, TimeSheetDataFieldsField } from './TimeSheetDataFields';
import { StringField, DateField, BooleanField, AllFields, CustomField, Entity, EntityBuilderType, Selectable } from '@sap/cloud-sdk-core';
/**
 * This class represents the entity "TimeSheetEntryCollection" of service "API_MANAGE_WORKFORCE_TIMESHEET".
 */
export declare class TimeSheetEntry extends Entity implements TimeSheetEntryType {
    /**
     * Technical entity name for TimeSheetEntry.
     */
    static _entityName: string;
    /**
     * @deprecated Since v1.0.1 Use [[_defaultServicePath]] instead.
     * Technical service name for TimeSheetEntry.
     */
    static _serviceName: string;
    /**
     * Default url path for the according service.
     */
    static _defaultServicePath: string;
    /**
     * Time Sheet Data Fields.
     */
    timeSheetDataFields: TimeSheetDataFields;
    /**
     * Work Agreement External ID.
     * Maximum length: 20.
     */
    personWorkAgreementExternalId: string;
    /**
     * Company Code.
     * The company code is an organizational unit within financial accounting.
     * Maximum length: 4.
     */
    companyCode: string;
    /**
     * Counter for Records in Time Recording.
     * The system assigns time sheet records a counter. The counter represents a record's unique database ID.
     * If you change a record that has already been approved, the original record retains this ID; the changed record is assigned another unique ID.
     * Maximum length: 12.
     */
    timeSheetRecord: string;
    /**
     * Object ID.
     * Contains a unique eight-digit numerical code, that represents a specific object (for example, an organizational unit, qualification or business event).
     * When you work with infotype records, this field allows you to use the Fast entry feature. Fast entry enables you to create numerous infotype records without having to exit and re-enter the infotype window.To select the object that the infotype record should belong to, either:Enter the object's eight-digit codeUse the matchcode feature to search for the object.
     * Maximum length: 8.
     * @nullable
     */
    personWorkAgreement?: string;
    /**
     * Date.
     * @nullable
     */
    timeSheetDate?: Moment;
    /**
     * Release Records on Saving.
     * Determines that the system releases changed data records immediately when you save them. You do not have to release them manually.
     * @nullable
     */
    timeSheetIsReleasedOnSave?: boolean;
    /**
     * Reference Counter for Record to be Changed.
     * The reference counter is assigned to a record that was created when an existing record was changed. The new record is assigned a new counter and, as a reference counter, the ID of the original record. The reference counter provides a link between the new and original record.
     * Maximum length: 12.
     * @nullable
     */
    timeSheetPredecessorRecord?: string;
    /**
     * Processing Status.
     * Each time sheet record is assigned a status. This status indicates the processing status of the selected record.
     * Status   IndicatorIn process     10Released for approval     20Approved     30Approval rejected     40Changed after approval     50Cancelled     60.
     * Maximum length: 2.
     * @nullable
     */
    timeSheetStatus?: string;
    /**
     * Yes/No Switch with Values 'SPACE' and 'X'.
     * @nullable
     */
    timeSheetIsExecutedInTestRun?: boolean;
    /**
     * CATS Operations : 'C' , 'U' , 'D'.
     * Maximum length: 1.
     * @nullable
     */
    timeSheetOperation?: string;
    /**
     * Returns an entity builder to construct instances `TimeSheetEntry`.
     * @returns A builder that constructs instances of entity type `TimeSheetEntry`.
     */
    static builder(): EntityBuilderType<TimeSheetEntry, TimeSheetEntryTypeForceMandatory>;
    /**
     * Returns a request builder to construct requests for operations on the `TimeSheetEntry` entity type.
     * @returns A `TimeSheetEntry` request builder.
     */
    static requestBuilder(): TimeSheetEntryRequestBuilder;
    /**
     * Returns a selectable object that allows the selection of custom field in a get request for the entity `TimeSheetEntry`.
     * @param fieldName Name of the custom field to select
     * @returns A builder that constructs instances of entity type `TimeSheetEntry`.
     */
    static customField(fieldName: string): CustomField<TimeSheetEntry>;
    /**
     * Overwrites the default toJSON method so that all instance variables as well as all custom fields of the entity are returned.
     * @returns An object containing all instance variables + custom fields.
     */
    toJSON(): {
        [key: string]: any;
    };
}
export interface TimeSheetEntryType {
    timeSheetDataFields: TimeSheetDataFields;
    personWorkAgreementExternalId: string;
    companyCode: string;
    timeSheetRecord: string;
    personWorkAgreement?: string;
    timeSheetDate?: Moment;
    timeSheetIsReleasedOnSave?: boolean;
    timeSheetPredecessorRecord?: string;
    timeSheetStatus?: string;
    timeSheetIsExecutedInTestRun?: boolean;
    timeSheetOperation?: string;
}
export interface TimeSheetEntryTypeForceMandatory {
    timeSheetDataFields: TimeSheetDataFields;
    personWorkAgreementExternalId: string;
    companyCode: string;
    timeSheetRecord: string;
    personWorkAgreement: string;
    timeSheetDate: Moment;
    timeSheetIsReleasedOnSave: boolean;
    timeSheetPredecessorRecord: string;
    timeSheetStatus: string;
    timeSheetIsExecutedInTestRun: boolean;
    timeSheetOperation: string;
}
export declare namespace TimeSheetEntry {
    /**
     * Static representation of the [[timeSheetDataFields]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const TIME_SHEET_DATA_FIELDS: TimeSheetDataFieldsField<TimeSheetEntry>;
    /**
     * Static representation of the [[personWorkAgreementExternalId]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const PERSON_WORK_AGREEMENT_EXTERNAL_ID: StringField<TimeSheetEntry>;
    /**
     * Static representation of the [[companyCode]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const COMPANY_CODE: StringField<TimeSheetEntry>;
    /**
     * Static representation of the [[timeSheetRecord]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const TIME_SHEET_RECORD: StringField<TimeSheetEntry>;
    /**
     * Static representation of the [[personWorkAgreement]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const PERSON_WORK_AGREEMENT: StringField<TimeSheetEntry>;
    /**
     * Static representation of the [[timeSheetDate]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const TIME_SHEET_DATE: DateField<TimeSheetEntry>;
    /**
     * Static representation of the [[timeSheetIsReleasedOnSave]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const TIME_SHEET_IS_RELEASED_ON_SAVE: BooleanField<TimeSheetEntry>;
    /**
     * Static representation of the [[timeSheetPredecessorRecord]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const TIME_SHEET_PREDECESSOR_RECORD: StringField<TimeSheetEntry>;
    /**
     * Static representation of the [[timeSheetStatus]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const TIME_SHEET_STATUS: StringField<TimeSheetEntry>;
    /**
     * Static representation of the [[timeSheetIsExecutedInTestRun]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const TIME_SHEET_IS_EXECUTED_IN_TEST_RUN: BooleanField<TimeSheetEntry>;
    /**
     * Static representation of the [[timeSheetOperation]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const TIME_SHEET_OPERATION: StringField<TimeSheetEntry>;
    /**
     * All fields of the TimeSheetEntry entity.
     */
    const _allFields: Array<TimeSheetDataFieldsField<TimeSheetEntry> | StringField<TimeSheetEntry> | DateField<TimeSheetEntry> | BooleanField<TimeSheetEntry>>;
    /**
     * All fields selector.
     */
    const ALL_FIELDS: AllFields<TimeSheetEntry>;
    /**
     * All key fields of the TimeSheetEntry entity.
     */
    const _keyFields: Array<Selectable<TimeSheetEntry>>;
    /**
     * Mapping of all key field names to the respective static field property TimeSheetEntry.
     */
    const _keys: {
        [keys: string]: Selectable<TimeSheetEntry>;
    };
}
//# sourceMappingURL=TimeSheetEntry.d.ts.map