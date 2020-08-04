const express = require('express');
const app = express();

const bupaApi = require('./business-partner/business-partner-api.js');
const socialMediaApi = require('./social-media-accounts/social-media-accounts-api.js');
const timeSheetApi = require('./timeSheetEntryCollection/timeSheetEntryCollection-api.js');
const timeOff = require('./time-off/api.js');

const logRequests = function(req, res, next) {
    console.log(`Request: ${req.method} ${req.originalUrl}`)
    next();
};

const sendFakeCsrfToken = function(req, res, next) {
    res.header('x-csrf-token', 'dummyToken123')
    res.header('set-cookie', ['cookie'])
    next()
}

app.use(logRequests);
app.use(sendFakeCsrfToken)

app.use('/sap/opu/odata/sap/API_BUSINESS_PARTNER', bupaApi);
app.use('/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS', socialMediaApi);
app.use('/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET', timeSheetApi);
app.use('/odata/v2', timeOff);

app.get('/', function(req, res) {
    res.set('Content-Type', 'text/html');
    res.send(`<html>
    <head>
        <title>OData Mock Service for Business Partner API of SAP S/4HANA Cloud</title>
    </head>
    <body>
        <div>OData mock service for Business Partner API of SAP S/4HANA Cloud is running at <a href="/sap/opu/odata/sap/API_BUSINESS_PARTNER">/sap/opu/odata/sap/API_BUSINESS_PARTNER</a>.</div>
        <div>OData mock service for Business Partner Social Media custom API is running at <a href="/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS">/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS</a>.</div>
        <div>OData mock service for Timesheet API is running at <a href="/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET">/sap/opu/odata/sap/API_MANAGE_WORKFORCE_TIMESHEET</a>.</div>
        <div>OData mock service for Employee Central Time Off service of SAP SuccessFactors is running at <a href="/odata/v2/EmployeeTime">/odata/v2/EmployeeTime</a>.</div>
    </body>
</html>`);
});

module.exports = app;
