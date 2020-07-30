"use strict";
/*!
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
var cloud_sdk_core_1 = require("@sap/cloud-sdk-core");
/**
 * @deprecated since v1.6.0. Use [[TimeSheetDataFields.build]] instead.
 */
function createTimeSheetDataFields(json) {
    return TimeSheetDataFields.build(json);
}
exports.createTimeSheetDataFields = createTimeSheetDataFields;
/**
 * TimeSheetDataFieldsField
 * @typeparam EntityT Type of the entity the complex type field belongs to.
 */
var TimeSheetDataFieldsField = /** @class */ (function (_super) {
    __extends(TimeSheetDataFieldsField, _super);
    function TimeSheetDataFieldsField() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        /**
         * Representation of the [[TimeSheetDataFields.controllingArea]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.controllingArea = new cloud_sdk_core_1.ComplexTypeStringPropertyField('ControllingArea', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.senderCostCenter]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.senderCostCenter = new cloud_sdk_core_1.ComplexTypeStringPropertyField('SenderCostCenter', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.receiverCostCenter]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.receiverCostCenter = new cloud_sdk_core_1.ComplexTypeStringPropertyField('ReceiverCostCenter', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.internalOrder]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.internalOrder = new cloud_sdk_core_1.ComplexTypeStringPropertyField('InternalOrder', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.activityType]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.activityType = new cloud_sdk_core_1.ComplexTypeStringPropertyField('ActivityType', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.wbsElement]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.wbsElement = new cloud_sdk_core_1.ComplexTypeStringPropertyField('WBSElement', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.workItem]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.workItem = new cloud_sdk_core_1.ComplexTypeStringPropertyField('WorkItem', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.billingControlCategory]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.billingControlCategory = new cloud_sdk_core_1.ComplexTypeStringPropertyField('BillingControlCategory', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.purchaseOrder]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.purchaseOrder = new cloud_sdk_core_1.ComplexTypeStringPropertyField('PurchaseOrder', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.purchaseOrderItem]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.purchaseOrderItem = new cloud_sdk_core_1.ComplexTypeStringPropertyField('PurchaseOrderItem', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.timeSheetTaskType]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.timeSheetTaskType = new cloud_sdk_core_1.ComplexTypeStringPropertyField('TimeSheetTaskType', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.timeSheetTaskLevel]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.timeSheetTaskLevel = new cloud_sdk_core_1.ComplexTypeStringPropertyField('TimeSheetTaskLevel', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.timeSheetTaskComponent]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.timeSheetTaskComponent = new cloud_sdk_core_1.ComplexTypeStringPropertyField('TimeSheetTaskComponent', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.timeSheetNote]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.timeSheetNote = new cloud_sdk_core_1.ComplexTypeStringPropertyField('TimeSheetNote', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.recordedHours]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.recordedHours = new cloud_sdk_core_1.ComplexTypeBigNumberPropertyField('RecordedHours', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.Decimal');
        /**
         * Representation of the [[TimeSheetDataFields.recordedQuantity]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.recordedQuantity = new cloud_sdk_core_1.ComplexTypeBigNumberPropertyField('RecordedQuantity', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.Decimal');
        /**
         * Representation of the [[TimeSheetDataFields.hoursUnitOfMeasure]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.hoursUnitOfMeasure = new cloud_sdk_core_1.ComplexTypeStringPropertyField('HoursUnitOfMeasure', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.rejectionReason]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.rejectionReason = new cloud_sdk_core_1.ComplexTypeStringPropertyField('RejectionReason', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.timeSheetWrkLocCode]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.timeSheetWrkLocCode = new cloud_sdk_core_1.ComplexTypeStringPropertyField('TimeSheetWrkLocCode', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        /**
         * Representation of the [[TimeSheetDataFields.timeSheetOvertimeCategory]] property for query construction.
         * Use to reference this property in query operations such as 'filter' in the fluent request API.
         */
        _this.timeSheetOvertimeCategory = new cloud_sdk_core_1.ComplexTypeStringPropertyField('TimeSheetOvertimeCategory', _this.entityConstructor, 'TimeSheetDataFields', 'Edm.String');
        return _this;
    }
    return TimeSheetDataFieldsField;
}(cloud_sdk_core_1.ComplexTypeField));
exports.TimeSheetDataFieldsField = TimeSheetDataFieldsField;
var TimeSheetDataFields;
(function (TimeSheetDataFields) {
    function build(json) {
        return cloud_sdk_core_1.createComplexType(json, {
            ControllingArea: function (controllingArea) { return ({ controllingArea: cloud_sdk_core_1.edmToTs(controllingArea, 'Edm.String') }); },
            SenderCostCenter: function (senderCostCenter) { return ({ senderCostCenter: cloud_sdk_core_1.edmToTs(senderCostCenter, 'Edm.String') }); },
            ReceiverCostCenter: function (receiverCostCenter) { return ({ receiverCostCenter: cloud_sdk_core_1.edmToTs(receiverCostCenter, 'Edm.String') }); },
            InternalOrder: function (internalOrder) { return ({ internalOrder: cloud_sdk_core_1.edmToTs(internalOrder, 'Edm.String') }); },
            ActivityType: function (activityType) { return ({ activityType: cloud_sdk_core_1.edmToTs(activityType, 'Edm.String') }); },
            WBSElement: function (wbsElement) { return ({ wbsElement: cloud_sdk_core_1.edmToTs(wbsElement, 'Edm.String') }); },
            WorkItem: function (workItem) { return ({ workItem: cloud_sdk_core_1.edmToTs(workItem, 'Edm.String') }); },
            BillingControlCategory: function (billingControlCategory) { return ({ billingControlCategory: cloud_sdk_core_1.edmToTs(billingControlCategory, 'Edm.String') }); },
            PurchaseOrder: function (purchaseOrder) { return ({ purchaseOrder: cloud_sdk_core_1.edmToTs(purchaseOrder, 'Edm.String') }); },
            PurchaseOrderItem: function (purchaseOrderItem) { return ({ purchaseOrderItem: cloud_sdk_core_1.edmToTs(purchaseOrderItem, 'Edm.String') }); },
            TimeSheetTaskType: function (timeSheetTaskType) { return ({ timeSheetTaskType: cloud_sdk_core_1.edmToTs(timeSheetTaskType, 'Edm.String') }); },
            TimeSheetTaskLevel: function (timeSheetTaskLevel) { return ({ timeSheetTaskLevel: cloud_sdk_core_1.edmToTs(timeSheetTaskLevel, 'Edm.String') }); },
            TimeSheetTaskComponent: function (timeSheetTaskComponent) { return ({ timeSheetTaskComponent: cloud_sdk_core_1.edmToTs(timeSheetTaskComponent, 'Edm.String') }); },
            TimeSheetNote: function (timeSheetNote) { return ({ timeSheetNote: cloud_sdk_core_1.edmToTs(timeSheetNote, 'Edm.String') }); },
            RecordedHours: function (recordedHours) { return ({ recordedHours: cloud_sdk_core_1.edmToTs(recordedHours, 'Edm.Decimal') }); },
            RecordedQuantity: function (recordedQuantity) { return ({ recordedQuantity: cloud_sdk_core_1.edmToTs(recordedQuantity, 'Edm.Decimal') }); },
            HoursUnitOfMeasure: function (hoursUnitOfMeasure) { return ({ hoursUnitOfMeasure: cloud_sdk_core_1.edmToTs(hoursUnitOfMeasure, 'Edm.String') }); },
            RejectionReason: function (rejectionReason) { return ({ rejectionReason: cloud_sdk_core_1.edmToTs(rejectionReason, 'Edm.String') }); },
            TimeSheetWrkLocCode: function (timeSheetWrkLocCode) { return ({ timeSheetWrkLocCode: cloud_sdk_core_1.edmToTs(timeSheetWrkLocCode, 'Edm.String') }); },
            TimeSheetOvertimeCategory: function (timeSheetOvertimeCategory) { return ({ timeSheetOvertimeCategory: cloud_sdk_core_1.edmToTs(timeSheetOvertimeCategory, 'Edm.String') }); }
        });
    }
    TimeSheetDataFields.build = build;
})(TimeSheetDataFields = exports.TimeSheetDataFields || (exports.TimeSheetDataFields = {}));
//# sourceMappingURL=TimeSheetDataFields.js.map