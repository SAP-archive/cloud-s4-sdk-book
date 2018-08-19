const nodeAppStarted = Date.now();

import express from 'express';
import {router as bupaApi} from './business-partner/business-partner-api';
import {router as socialMediaApi} from './social-media-accounts/social-media-accounts-api';

const app = express();

const logRequests = function(req: express.Request, res: express.Response, next: express.NextFunction) {
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
