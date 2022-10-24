/*
 * This module hosts the initial data of the mock server.
 * Replace the content of the array "data" with the intended demo data.
 * Replace the value of deferred (non-expanded) navigation properties with the appropriate empty value, also recursively:
 * - for multi-valued propeties: { "results": [] }
 * - for single-valued properties: null
 */
const moment = require('moment');
const yearMonth = moment().format('YYYY.MM');
 module.exports = {
        data: [
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001492')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001492')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "Consulting",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "bdavis",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001492",
                "PersonWorkAgreement": "50000485",
                "TimeSheetDate": `/Date(${new Date( moment(1567123200000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='cgrant',CompanyCode='1010',TimeSheetRecord='000000001493')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='cgrant',CompanyCode='1010',TimeSheetRecord='000000001493')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "Consulting",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "cgrant",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001493",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1567036800000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='smormony',CompanyCode='1010',TimeSheetRecord='000000001494')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='smormony',CompanyCode='1010',TimeSheetRecord='000000001494')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "Consulting",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "smormony",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001494",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1568246400000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001498')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001498')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "Consulting",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "bdavis",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001498",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1570060800000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='mhoff',CompanyCode='1010',TimeSheetRecord='000000001495')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='mhoff',CompanyCode='1010',TimeSheetRecord='000000001495')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "Consulting",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "mhoff",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001495",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1570492800000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001496')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001496')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "Consulting",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "bdavis",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001496",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1571097600000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='smormony',CompanyCode='1010',TimeSheetRecord='000000001498')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='smormony',CompanyCode='1010',TimeSheetRecord='000000001498')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "Consulting",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "smormony",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001498",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1572307200000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='cgrant',CompanyCode='1010',TimeSheetRecord='000000001497')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='cgrant',CompanyCode='1010',TimeSheetRecord='000000001497')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "Consulting",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "cgrant",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001497",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1572998400000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001499')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001499')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "TechEd Las Vegas",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "smormony",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001499",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1569283200000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001500')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001500')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "TechEd Las Vegas",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "smormony",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001500",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1569369600000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001501')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001501')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "TechEd Las Vegas",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "smormony",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001501",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1569456000000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001502')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001502')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "TechEd Las Vegas",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "smormony",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001502",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1569542400000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001503')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001503')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "TechEd Barcelona",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "bdavis",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001503",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1570492800000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001504')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001504')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "TechEd Barcelona",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "bdavis",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001504",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1570579200000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001505')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001505')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "TechEd Barcelona",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "bdavis",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001505",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1570665600000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001506')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001506')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "TechEd Bangalore",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "bdavis",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001506",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1573603200000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001507')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001507')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "TechEd Bangalore",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "bdavis",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001507",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1573689600000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            },
            {
                "__metadata": {
                    "id": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001508')",
                    "uri": "https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='bdavis',CompanyCode='1010',TimeSheetRecord='000000001508')",
                    "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetEntry"
                },
                "TimeSheetDataFields": {
                    "__metadata": {
                      "type": "API_MANAGE_WORKFORCE_TIMESHEET.TimeSheetDataFields"
                    },
                    "ControllingArea": "A000",
                    "SenderCostCenter": "17101904",
                    "ReceiverCostCenter": "",
                    "InternalOrder": "",
                    "ActivityType": "T001",
                    "WBSElement": "Teched2019",
                    "WorkItem": "M001",
                    "BillingControlCategory": "",
                    "PurchaseOrder": "",
                    "PurchaseOrderItem": "00000",
                    "TimeSheetTaskType": "",
                    "TimeSheetTaskLevel": "",
                    "TimeSheetTaskComponent": "",
                    "TimeSheetNote": "TechEd Bangalore",
                    "RecordedHours": "10.00",
                    "RecordedQuantity": "10.000",
                    "HoursUnitOfMeasure": "H",
                    "RejectionReason": "",
                    "TimeSheetWrkLocCode": "",
                    "TimeSheetOvertimeCategory": ""
                },
                "PersonWorkAgreementExternalID": "bdavis",
                "CompanyCode": "1010",
                "TimeSheetRecord": "000000001508",
                "PersonWorkAgreement": "50000486",
                "TimeSheetDate": `/Date(${new Date( moment(1573776000000).format(yearMonth + ".DD") ).getTime()})/`,
                "TimeSheetIsReleasedOnSave": false,
                "TimeSheetPredecessorRecord": "string",
                "TimeSheetStatus": "30",
                "TimeSheetIsExecutedInTestRun": false,
                "TimeSheetOperation": "C"
            }
        ]
 }
