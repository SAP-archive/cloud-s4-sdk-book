/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
import { Yy1_Socialmediaaccount_Bpso000RequestBuilder } from './Yy1_Socialmediaaccount_Bpso000RequestBuilder';
import { StringField, OneToOneLink, AllFields, CustomField, Entity, EntityBuilderType, Selectable } from '@sap/cloud-sdk-core';
/**
 * This class represents the entity "YY1_SOCIALMEDIAACCOUNT_BPSO000" of service "YY1_BPSOCIALMEDIA_CDS".
 */
export declare class Yy1_Socialmediaaccount_Bpso000 extends Entity implements Yy1_Socialmediaaccount_Bpso000Type {
    /**
     * Technical entity name for Yy1_Socialmediaaccount_Bpso000.
     */
    static _entityName: string;
    /**
     * @deprecated Since v1.0.1 Use [[_defaultServicePath]] instead.
     * Technical service name for Yy1_Socialmediaaccount_Bpso000.
     */
    static _serviceName: string;
    /**
     * Default url path for the according service.
     */
    static _defaultServicePath: string;
    /**
     * 16 Byte UUID in 16 Bytes (Raw Format).
     */
    sapUuid: string;
    /**
     * 16 Byte UUID in 16 Bytes (Raw Format).
     * @nullable
     */
    sapParentUuid?: string;
    /**
     * Service.
     * Maximum length: 10.
     * @nullable
     */
    service?: string;
    /**
     * Account.
     * Maximum length: 60.
     * @nullable
     */
    account?: string;
    /**
     * One-to-one navigation property to the [[Yy1_Bpsocialmedia]] entity.
     */
    toBpsocialmedia: Yy1_Bpsocialmedia;
    /**
     * Returns an entity builder to construct instances `Yy1_Socialmediaaccount_Bpso000`.
     * @returns A builder that constructs instances of entity type `Yy1_Socialmediaaccount_Bpso000`.
     */
    static builder(): EntityBuilderType<Yy1_Socialmediaaccount_Bpso000, Yy1_Socialmediaaccount_Bpso000TypeForceMandatory>;
    /**
     * Returns a request builder to construct requests for operations on the `Yy1_Socialmediaaccount_Bpso000` entity type.
     * @returns A `Yy1_Socialmediaaccount_Bpso000` request builder.
     */
    static requestBuilder(): Yy1_Socialmediaaccount_Bpso000RequestBuilder;
    /**
     * Returns a selectable object that allows the selection of custom field in a get request for the entity `Yy1_Socialmediaaccount_Bpso000`.
     * @param fieldName Name of the custom field to select
     * @returns A builder that constructs instances of entity type `Yy1_Socialmediaaccount_Bpso000`.
     */
    static customField(fieldName: string): CustomField<Yy1_Socialmediaaccount_Bpso000>;
    /**
     * Overwrites the default toJSON method so that all instance variables as well as all custom fields of the entity are returned.
     * @returns An object containing all instance variables + custom fields.
     */
    toJSON(): {
        [key: string]: any;
    };
}
import { Yy1_Bpsocialmedia, Yy1_BpsocialmediaType } from './Yy1_Bpsocialmedia';
export interface Yy1_Socialmediaaccount_Bpso000Type {
    sapUuid: string;
    sapParentUuid?: string;
    service?: string;
    account?: string;
    toBpsocialmedia: Yy1_BpsocialmediaType;
}
export interface Yy1_Socialmediaaccount_Bpso000TypeForceMandatory {
    sapUuid: string;
    sapParentUuid: string;
    service: string;
    account: string;
    toBpsocialmedia: Yy1_BpsocialmediaType;
}
export declare namespace Yy1_Socialmediaaccount_Bpso000 {
    /**
     * Static representation of the [[sapUuid]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const SAP_UUID: StringField<Yy1_Socialmediaaccount_Bpso000>;
    /**
     * Static representation of the [[sapParentUuid]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const SAP_PARENT_UUID: StringField<Yy1_Socialmediaaccount_Bpso000>;
    /**
     * Static representation of the [[service]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const SERVICE: StringField<Yy1_Socialmediaaccount_Bpso000>;
    /**
     * Static representation of the [[account]] property for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const ACCOUNT: StringField<Yy1_Socialmediaaccount_Bpso000>;
    /**
     * Static representation of the one-to-one navigation property [[toBpsocialmedia]] for query construction.
     * Use to reference this property in query operations such as 'select' in the fluent request API.
     */
    const TO_BPSOCIALMEDIA: OneToOneLink<Yy1_Socialmediaaccount_Bpso000, Yy1_Bpsocialmedia>;
    /**
     * All fields of the Yy1_Socialmediaaccount_Bpso000 entity.
     */
    const _allFields: Array<StringField<Yy1_Socialmediaaccount_Bpso000> | OneToOneLink<Yy1_Socialmediaaccount_Bpso000, Yy1_Bpsocialmedia>>;
    /**
     * All fields selector.
     */
    const ALL_FIELDS: AllFields<Yy1_Socialmediaaccount_Bpso000>;
    /**
     * All key fields of the Yy1_Socialmediaaccount_Bpso000 entity.
     */
    const _keyFields: Array<Selectable<Yy1_Socialmediaaccount_Bpso000>>;
    /**
     * Mapping of all key field names to the respective static field property Yy1_Socialmediaaccount_Bpso000.
     */
    const _keys: {
        [keys: string]: Selectable<Yy1_Socialmediaaccount_Bpso000>;
    };
}
//# sourceMappingURL=Yy1_Socialmediaaccount_Bpso000.d.ts.map