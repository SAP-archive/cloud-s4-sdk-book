/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
import { BigNumber } from 'bignumber.js';
import { ComplexTypeStringPropertyField, ComplexTypeBigNumberPropertyField, ComplexTypeField, Entity, FieldType } from '@sap/cloud-sdk-core';
/**
 * TimeSheetDataFields
 */
export interface TimeSheetDataFields {
    /**
     * Controlling Area.
     * Uniquely identifies a controlling area.
     * The controlling area is the highest organizational unit in Controlling.Whether controlling area and company code are in a 1:1 relationship or a 1:n relationship, the number of posting periods in both controlling area and company code(s) must be identical. However, special periods may vary.
     * @nullable
     */
    controllingArea?: string;
    /**
     * Sender Cost Center.
     * Identifies the cost center you selected as the sender object.
     * @nullable
     */
    senderCostCenter?: string;
    /**
     * Receiver Cost Center.
     * Key specifically identifying the cost center selected as the receiver object.
     * @nullable
     */
    receiverCostCenter?: string;
    /**
     * Receiver Order.
     * Key specifically identifying the order selected as the receiver object.
     * @nullable
     */
    internalOrder?: string;
    /**
     * Activity Type.
     * Key uniquely identifying .
     * Activity types describe the activity produced by a cost center and are measured in units of time or quantity.In activity type planning, control data determines whether the activity price for evaluation of the activity type is manually set or is derived iteratively through activity price calculation.
     * @nullable
     */
    activityType?: string;
    /**
     * Work Breakdown Structure Element (WBS Element).
     * Key identifying a  WBS element.
     * @nullable
     */
    wbsElement?: string;
    /**
     * Work Item ID.
     * @nullable
     */
    workItem?: string;
    /**
     * Billing Control Category.
     * @nullable
     */
    billingControlCategory?: string;
    /**
     * Sending purchase order.
     * Specifies an alphanumeric key that uniquely identifies the Sender purchase order.
     * @nullable
     */
    purchaseOrder?: string;
    /**
     * Sending purchase order item.
     * Specifies a number that uniquely identifies the item within the purchasing document.
     * @nullable
     */
    purchaseOrderItem?: string;
    /**
     * Task Type.
     * Characterisation of content of a specific task that an employee performs, for example "consulting" or "instructor function". Specific task components are usual for each task type.
     * In the Cross-Application Time Sheet (SAP CATS), the task type, task component, and task level fields enable you to specify the task elements that are relevant for valuation. For example, elements that are relevant for valuation are entered for the following business processes:Payroll (wage types, attendance types)Controlling and billing (activity types, statistical key figures)Invoice verification for external employees (activity numbers)The SAP R/3 system can derive the following working time attributes from the combination of task types, components, and levels:Attendance/absence typesWage typesActivity typesStatistical key figuresActivity numbersSender business processesYou can replace these input fields on your time sheet user interface with the task type, task component, and task level input fields, which are tailored to suit task recording needs. You can also valuate the task type, component, and level in reporting.You can make the relevant settings in the Implementation Guide (IMG), under Cross-Application Components -&gt; Time Sheet-&gt; Time Recording -&gt; Task Levels, Components, and Categories.ExampleAt a consultancy, employees record their tasks. The data recorded is then used for creating invoices.Task type: consultingTask components for consulting: working hours, overtime, number of kilometers drivenTask levels for consulting: junior, seniorThe SAP R/3 system valuates the "overtime" task component for the "junior" task level with a specific activity type. The SAP R/3 system valuates the "overtime" task component for the "senior" task level with a different activity type with a higher price.
     * @nullable
     */
    timeSheetTaskType?: string;
    /**
     * Task Level.
     * Differentiation between an employee's tasks depending on the level of valuation.
     * Specific task levels are allowed for each task type. The task levels determine the valuation level for each task component.In the Cross-Application Time Sheet (SAP CATS), the task type, task component, and task level fields enable you to specify the task elements that are relevant for valuation. For example, elements that are relevant for valuation are entered for the following business processes:Payroll (wage types, attendance types)Controlling and billing (activity types, statistical key figures)Invoice verification for external employees (activity numbers)The SAP R/3 system can derive the following working time attributes from the combination of task types, components, and levels:Attendance/absence typesWage typesActivity typesStatistical key figuresActivity numbersSender business processesYou can replace these input fields on your time sheet user interface with the task type, task component, and task level input fields, which are tailored to suit task recording needs. You can also valuate the task type, component, and level in reporting.You can make the relevant settings in the Implementation Guide (IMG), under Cross-Application Components -&gt; Time Sheet-&gt; Time Recording -&gt; Task Levels, Components, and Categories.ExampleAt a consultancy, employees record their tasks. The data recorded is then used for creating invoices.Task type: consultingTask components for consulting: working hours, overtime, number of kilometers drivenTask levels for consulting: junior, seniorThe SAP R/3 system valuates the "overtime" task component for the "junior" task level with a specific activity type. The SAP R/3 system valuates the "overtime" task component for the "senior" task level with a different activity type with a higher price.
     * @nullable
     */
    timeSheetTaskLevel?: string;
    /**
     * Task component.
     * Task element that is relevant for valuation.
     * A task generally comprises more than one task component, for example, normal working hours, overtime, or travel hours. Specific task components are usual for each task type.In the Cross-Application Time Sheet (SAP CATS), the task type, task component, and task level fields enable you to specify the task elements that are relevant for valuation. For example, elements that are relevant for valuation are entered for the following business processes:Payroll (wage types, attendance types)Controlling and billing (activity types, statistical key figures)Invoice verification for external employees (activity numbers)The SAP R/3 system can derive the following working time attributes from the combination of task types, components, and levels:Attendance/absence typesWage typesActivity typesStatistical key figuresActivity numbersSender business processesYou can replace these input fields on your time sheet user interface with the task type, task component, and task level input fields, which are tailored to suit task recording needs. You can also valuate the task type, component, and level in reporting.You can make the relevant settings in the Implementation Guide (IMG), under Cross-Application Components -&gt; Time Sheet-&gt; Time Recording -&gt; Task Levels, Components, and Categories.ExampleAt a consultancy, employees record their tasks. The data recorded is then used for creating invoices.Task type: consultingTask components for consulting: working hours, overtime, number of kilometers drivenTask levels for consulting: junior, seniorThe SAP R/3 system valuates the "overtime" task component for the "junior" task level with a specific activity type. The SAP R/3 system valuates the "overtime" task component for the "senior" task level with a different activity type with a higher price.
     * @nullable
     */
    timeSheetTaskComponent?: string;
    /**
     * Time Sheet Note.
     * @nullable
     */
    timeSheetNote?: string;
    /**
     * Hours.
     * The duration of an activity or absence in hours.
     * When you maintain the time sheet, one data record is stored for each entry you make in the Hours field - that is, per day and account assignment (order number, attendance/absence type, purchase order number).
     * @nullable
     */
    recordedHours?: BigNumber;
    /**
     * Time Sheet: Number (Unit of Measure).
     * Specifies the number of units entered for the unit of measure.
     * @nullable
     */
    recordedQuantity?: BigNumber;
    /**
     * Unit of Measure for Display.
     * Specifies the unit of measure in which the quantity represented  is displayed.
     * @nullable
     */
    hoursUnitOfMeasure?: string;
    /**
     * Reason for rejection.
     * Comment line that can go back to the person who entered the data when an approval is rejected.
     * @nullable
     */
    rejectionReason?: string;
    /**
     * Tax area work center.
     * Employees current work center for tax calculation (USA). The current work center is used to determine the appropriate tax authorities.
     * @nullable
     */
    timeSheetWrkLocCode?: string;
    /**
     * Overtime Category.
     * Overtime is defined as the time an employee works outside the regular working times. Overtime Category is the classification of the overtime hours.
     * It is used in the Manage My Timesheet app to efficiently create time entries.
     * @nullable
     */
    timeSheetOvertimeCategory?: string;
}
/**
 * @deprecated since v1.6.0. Use [[TimeSheetDataFields.build]] instead.
 */
export declare function createTimeSheetDataFields(json: {
    [keys: string]: FieldType;
}): TimeSheetDataFields;
/**
 * TimeSheetDataFieldsField
 * @typeparam EntityT Type of the entity the complex type field belongs to.
 */
export declare class TimeSheetDataFieldsField<EntityT extends Entity> extends ComplexTypeField<EntityT> {
    /**
     * Representation of the [[TimeSheetDataFields.controllingArea]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    controllingArea: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.senderCostCenter]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    senderCostCenter: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.receiverCostCenter]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    receiverCostCenter: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.internalOrder]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    internalOrder: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.activityType]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    activityType: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.wbsElement]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    wbsElement: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.workItem]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    workItem: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.billingControlCategory]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    billingControlCategory: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.purchaseOrder]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    purchaseOrder: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.purchaseOrderItem]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    purchaseOrderItem: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.timeSheetTaskType]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    timeSheetTaskType: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.timeSheetTaskLevel]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    timeSheetTaskLevel: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.timeSheetTaskComponent]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    timeSheetTaskComponent: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.timeSheetNote]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    timeSheetNote: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.recordedHours]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    recordedHours: ComplexTypeBigNumberPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.recordedQuantity]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    recordedQuantity: ComplexTypeBigNumberPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.hoursUnitOfMeasure]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    hoursUnitOfMeasure: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.rejectionReason]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    rejectionReason: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.timeSheetWrkLocCode]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    timeSheetWrkLocCode: ComplexTypeStringPropertyField<EntityT>;
    /**
     * Representation of the [[TimeSheetDataFields.timeSheetOvertimeCategory]] property for query construction.
     * Use to reference this property in query operations such as 'filter' in the fluent request API.
     */
    timeSheetOvertimeCategory: ComplexTypeStringPropertyField<EntityT>;
}
export declare namespace TimeSheetDataFields {
    function build(json: {
        [keys: string]: FieldType;
    }): TimeSheetDataFields;
}
//# sourceMappingURL=TimeSheetDataFields.d.ts.map