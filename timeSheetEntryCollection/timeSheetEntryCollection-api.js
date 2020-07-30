const express = require('express');
const bodyParser = require('body-parser');
const router = express.Router();

const odata = require('../odata-helpers.js');
const timeSheetEntryModel = require('./timeSheetEntryCollection-model')

const getAllTimeSheetEntries = function(req, res, next) {
    console.log('Reading all TimeSheetEntries')
    res.result = timeSheetEntryModel.getAllTimeSheetEntries()
    next()
}

const getSingleTimeSheetEntry = function(req, res, next) {
    console.log(`Reading TimeSheetEntry for ${req.params.personWorkAgreementExternalID} ${req.params.companyCode} ${req.params.timeSheetRecord}`)
    res.result = timeSheetEntryModel.findTimeSheetEntry(req.params.personWorkAgreementExternalID, req.params.companyCode, req.params.timeSheetRecord)
    next()
}

const createTimeSheetEntry = function(req, res, next) {
    console.log(`Creating TimeSheetentry for ${req.body.PersonWorkAgreementExternalID}`)
    res.result = timeSheetEntryModel.createAndAddTimeSheetEntry(req.body)
    next()
}

router.route('/TimeSheetEntryCollection')
    .get(getAllTimeSheetEntries, odata.middlewareForSet())
    .post(odata.middlewareForCreate(createTimeSheetEntry))

router.route('/TimeSheetEntryCollection\\((PersonWorkAgreementExternalID=)?(\':personWorkAgreementExternalID\'|%27:personWorkAgreementExternalID%27),(CompanyCode=)?(\':companyCode\'|%27:companyCode%27),(TimeSheetRecord=)?(\':timeSheetRecord\'|%27:timeSheetRecord%27)\\)')
    .get(getSingleTimeSheetEntry, odata.middlewareForEntity())

// Serve EDMX file for /$metadata
router.get('/([$])metadata', function(req, res) {
    const options = {
        root: __dirname + '/',
        headers: {
            'Content-Type': 'application/xml'
        }
    };
    console.log('Serving metadata for TimeSheetEntryCollection API');
    res.sendFile('API_MANAGE_WORKFORCE_TIMESHEET.edmx', options, function(err) {
        if(err) {
            console.error('No metadata file found at timeSheetEntryCollection/API_MANAGE_WORKFORCE_TIMESHEET.edmx. Please check the documentation on how to retrieve and where to store this file.')
            res.sendStatus(404);
        }
    })
})

router.post('/([$])batch', bodyParser.text({ type: () => true }), odata.set201Created, odata.batch);

router.get('/', function(req, res) {
    res.json({
        "d": {
            "EntitySets": [
                "TimeSheetDataFields",
                "PersonWorkAgreementExternalID",
                "CompanyCode",
                "TimeSheetRecord",
                "PersonWorkAgreement",
                "TimeSheetDate",
                "TimeSheetIsReleasedOnSave",
                "TimeSheetPredecessorRecord",
                "TimeSheetStatus",
                "TimeSheetIsExecutedInTestRun",
                "TimeSheetOperation"
            ]
        }
    })
})

module.exports = router;
