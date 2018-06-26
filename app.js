const nodeAppStarted = Date.now();
const express = require('express');
const app = express();

const bupaApi = require('./business-partner/business-partner-api.js');
const socialMediaApi = require('./social-media-accounts/social-media-accounts-api.js');

const logRequests = function(req, res, next) {
    console.log(`Request: ${req.method} ${req.originalUrl}`)
    next();
};

app.use(logRequests);

app.use('/sap/opu/odata/sap/API_BUSINESS_PARTNER', bupaApi);
app.use('/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS', socialMediaApi);

app.get('/', function(req, res) {
    res.set('Content-Type', 'text/html');
    res.send(`<html>
    <head>
        <title>OData Mock Service for Business Partner API of SAP S/4HANA Cloud</title>
    </head>
    <body>
        <div>OData mock service for Business Partner API of SAP S/4HANA Cloud is running at <a href="/sap/opu/odata/sap/API_BUSINESS_PARTNER">/sap/opu/odata/sap/API_BUSINESS_PARTNER</a>.</div>
        <div>OData mock service for Business Partner Social Media custom API is running at <a href="/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS">/sap/opu/odata/sap/YY1_BPSOCIALMEDIA_CDS</a>.</div>
    </body>
</html>`);
});

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`Mock server started on port ${port} after ${Date.now() - nodeAppStarted} ms, running - stop with CTRL+C (or CMD+C)...`))
