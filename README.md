# OData Mock Service for Business Partner API of SAP S/4HANA Cloud
The simple Node.js-based server contained in this repository represents a mock server for the purposes of testing the SAP S/4HANA integration capabilities of the SAP S/4HANA Cloud SDK.
The server makes it possible to test the SAP S/4HANA integration capabilities of the SAP S/4HANA Cloud SDK without access to an SAP S/4HANA system.
The server hosts an OData v2 mock service that mimicks the business partner API of SAP S/4HANA Cloud to a limited extent.

The mock server can be used as a stand-in for simple tests and experiments with the SAP S/4HANA Cloud SDK if no SAP S/4HANA system is available.
It is especially tailored towards the examples found in the book [_Extending SAP S/4HANA Cloud_](https://www.sap-press.com/extending-sap-s4hana_4655/) or in the [tutorials of the SAP S/4HANA Cloud SDK](https://blogs.sap.com/2017/05/10/first-steps-with-sap-s4hana-cloud-sdk/).
This page explains how to run the mock server and how to integrate it into the tests of the sample application.

> **Note**: the server is not secured in any way. Run the server on your own risk and only for experiments. Do not use the server to store any personal data - only use fake data.

## How to run the server
When you have cloned this repository, checkout the branch `mock-server`.
Alternatively, download [this archive](https://github.com/SAP/cloud-s4-sdk-book/archive/mock-server.zip) and unzip it to your local machine.
All of the following steps shall happen in this folder where you checked out or extraced the code of the mock server.

Before you can launch the mock server, you need to manually put the metadata EDMX document of the business partner OData service into the folder `business-partner` and prepare the document:
* Go to the description of the [Business Partner API in the SAP API Business Hub](https://api.sap.com/shell/discover/contentpackage/SAPS4HANACloud/api/API_BUSINESS_PARTNER).
* Click on *Log On* and log in with your credentials (you may need to register beforehand).
* Click on *Details* and *Download Specification* and choose *EDMX*.
* Store the downloaded file with the name `API_BUSINESS_PARTNER.edmx` in the subfolder `business-partner` of the mock server folder.
* Open the metadata document and locate the entity type `A_BusinessPartnerType`. Within the `EntityType` item with that name, add the following two lines behind the line that contains `</Key>`:
```
<Property Name="YY1_AddrLastCheckedOn_bus" Type="Edm.DateTime" Precision="0" sap:display-format="Date" sap:label="Addresses Last Checked On"/>
<Property Name="YY1_AddrLastCheckedBy_bus" Type="Edm.String" MaxLength="50" sap:label="Addresses Last Checked By"/>
```

After you have thus prepared the mock server,  you can run the mock server on your local machine (at http://localhost:3000) or on SAP Cloud Platform, Cloud Foundry, as described in either of the following two sections.

### Locally
#### Prerequisites
The following tools need to be installed on your local machine.
* [node.js](http://npmjs.com) 8.x or higher
* [npm](http://npmjs.com)

#### Launch the mock server
Execute the following commands in a command line shell of your choice within the folder where you stored the artifacts:
```
npm install
npm start
```

Wait until you see the output `Mock server started`. Access the mock OData service at http://localhost:3000/sap/opu/odata/sap/API_BUSINESS_PARTNER (no credentials required).

Use `http://localhost:3000` as the URL for your destination `ErpQueryEndpoint` with any dummy user and password, for example:
```
destinations=[{name: 'ErpQueryEndpoint', url: 'http://localhost:3000', username: 'DUMMY', password: 'dummy'}]
```

### On SAP Cloud Platform, Cloud Foundry
#### Prerequisites
The following tools need to be installed on your local machine.
* [Cloud Foundry command line interface (CLI)](https://docs.cloudfoundry.org/cf-cli/install-go-cli.html)

#### Launch the mock server
Execute the following commands (this assumes that you have set up your Cloud Foundry account at region EU10):
```
cf api https://api.cf.eu10.hana.ondemand.com
cf login
cf push
```

Watch the output of the command for the URL of the Cloud Foundry app. Look for a line towards the end similar to the following:
```
urls: bupa-mock-odata-<random-route>.cfapps.eu10.hana.ondemand.com
```
Access the mock OData service at that URL, by appending the path `/sap/opu/odata/sap/API_BUSINESS_PARTNER`.

Use your specific URL like `https://bupa-mock-odata-<random-route>.cfapps.eu10.hana.ondemand.com` as the URL for your destination `ErpQueryEndpoint` with any dummy user and password (or choose _No Authentication_ when defining the destination with the destination service on SAP Cloud Platform).

## Limitations
As a mock server, the functionality of the mock OData service is limited to the most essential features. It is by no means a complete OData service compliant with the OData v2 specification. Also, it is beyond the plain API not comparable with the business partner API of SAP S/4HANA Cloud.

Consider the following detailed limitations:
* The service performs almost no business validations, most fields can be set to any value independent of business semantics.
* The behavior is not comparable at all to SAP S/4HANA Cloud.
* Error handling and error messages are entirely different.
* No security: no authentication, no CSRF protection.
* The mock service supports deleting business partners, in contrast to the API of SAP S/4HANA.
* Only simple filters of the kind `PropertyName eq 'value'` are supported.
* Sorting via `orderby` is only supported for single simple String properties of the returned entities, not complex-typed or navigation properties.
* `PUT` behaves like `PATCH` and merges the supplied entity's properties into the existing entity.
* Only multi-valued navigation properties are properly handled in expand and select query options.

## Supported OData operations and capabilities
The OData mock service supports the following features centered around the `API_BUSINESS_PARTNER`, and thus allows to test certain scenarios using this mock server instead of an SAP S/4HANA system:
* Entity types _Business Partner_ and _Business Partner Address_ with all properties (including two custom fields on business partners).
* OData queries (`GET`) on entity sets and by key with query options `expand`, `select`, `filter`, and `orderby`. For filtering, only string equality comparisons are supported, no complex filter expressions nor other operators than `eq`.
* Creating (`POST`), updating (`PATCH`, `PUT`), and deleting (`DELETE`) entities. Modifications are only stored in-memory.
* Retrieving metadata document (`/$metadata`). The full metadata of the API is returned, not only the supported part.

Also see the accompanying test suite in folder `integration-tests`, implemented as Java JUnit tests using the SAP S/4HANA Cloud SDK, specifically its Virtual Data Model (VDM).

## Background
The mock server is implemented in JavaScript / Node.js using [Express](http://expressjs.com).

Entry point of the server is in `app.js`.
The implementation of the business partner and address api and model can be found in folder `business-partner`. The demo data is defined in the file `business-partner-data.js`.
OData features are implemented as generic Express middlewares in `odata-helper.js`.

## License
Copyright (c) 2018 SAP SE or an SAP affiliate company. All rights reserved.
This file is licensed under the Apache Software License, v. 2 except as noted otherwise in the [LICENSE](LICENSE) file.
