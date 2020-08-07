const data = require('./timeSheetEntryCollection-data.js').data
const uuid = require('uuid/v1')

function generateUuid() {
    return uuid().substring(0, 12)
}

module.exports = {
    data: data,
    newTimeSheetEntry: function(timeSheetEntryInput) {
        const timeSheetRecordId = generateUuid()
        
        return Object.seal({
            "__metadata": {
                "id": `https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='${timeSheetEntryInput.PersonWorkAgreementExternalID}',CompanyCode='1010',TimeSheetRecord='${timeSheetRecordId}')`,
                "uri": `https://{host}:{port}/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET/TimeSheetEntryCollection(PersonWorkAgreementExternalID='${timeSheetEntryInput.PersonWorkAgreementExternalID}',CompanyCode='1010',TimeSheetRecord='${timeSheetRecordId}')`,
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
                "TimeSheetNote": timeSheetEntryInput.TimeSheetDataFields.TimeSheetNote,
                "RecordedHours": "10",
                "RecordedQuantity": "10",
                "HoursUnitOfMeasure": "H",
                "RejectionReason": "",
                "TimeSheetWrkLocCode": "",
                "TimeSheetOvertimeCategory": ""
            },
            "PersonWorkAgreementExternalID": timeSheetEntryInput.PersonWorkAgreementExternalID,
            "CompanyCode": "1010",
            "TimeSheetRecord": timeSheetRecordId,
            "PersonWorkAgreement": "50000485",
            "TimeSheetDate": timeSheetEntryInput.TimeSheetDate,
            "TimeSheetIsReleasedOnSave": false,
            "TimeSheetPredecessorRecord": "string",
            "TimeSheetStatus": "30",
            "TimeSheetIsExecutedInTestRun": false,
            "TimeSheetOperation": "C"
        })
    },
    getAllTimeSheetEntries: function() {
        return this.data
    },
    findTimeSheetEntry: function(personWorkAgreementExternalID, companyCode, timeSheetRecord) {
        var entries = this.getAllTimeSheetEntries()
        return entries.find(function(element) {
            return element.PersonWorkAgreementExternalID == personWorkAgreementExternalID 
                && element.CompanyCode == companyCode 
                && element.TimeSheetRecord == timeSheetRecord
        })
    },
    createAndAddTimeSheetEntry: function(timeSheetEntryInput) {
        const newTimeSheetEntry = this.newTimeSheetEntry(timeSheetEntryInput)
        this.getAllTimeSheetEntries().push(newTimeSheetEntry)
        console.log(`Created new entry: ${newTimeSheetEntry}`)
        return newTimeSheetEntry
    }
}
