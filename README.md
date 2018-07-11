# Extending SAP S/4HANA - Source Code for the Book
This GitHub repository hosts the sample source code for the book _Extending SAP S/4HANA. Side-by-Side Extensions with the SAP S/4HANA Cloud SDK_ available [from SAP Press](https://www.sap-press.com/extending-sap-s4hana_4655/). The book uses the Business Partner Address Manager sample application contained in this repository to explain how to build side-by-side extensions that extend SAP S/4HANA and SAP S/4HANA Cloud.

This repository is relevant for you if you want to follow the example application described in the book and compare your results with the sample code. For more information, please consult the [public documentation](https://sap.github.io/cloud-s4-sdk-book/) that accompanies this repository.

## Requirements
You will need the following on your local machine to work with the source code and run the application:
* Java Development Kit (JDK), version 8 (1.8.0), or above
* Apache Maven, version 3.5.0, or above

Additionally, to run the tests and the application, you need access to an SAP S/4HANA system, or alternatively setup a mock server as explained in the [documentation](https://sap.github.io/cloud-s4-sdk-book/pages/mock-odata.html). In the SAP S/4HANA system, you need a technical communication user for integration scenario `SAP_COM_0008`. The example application has been tested with SAP S/4HANA Cloud 1805.

## Download and Installation
Follow the instructions below to quickly build and launch the example application on your local machine.
For more detailed step-by-step instructions and advanced use cases, consult the corresponding sections in the book.

### 1. Prepare
Start with the following:
* Clone or download this repository.
* Navigate to the folder where you cloned or downloaded the repository. All of the steps mentioned below happen relative to this root folder. Execute all commands mentioned in the following in the root folder (which contains, besides others, this README file).

### 2. Build and test
Then, you need to build the project, which also includes testing. For the integration tests, you need to provide the URL and credentials of your SAP S/4HANA system.
* Open the file `integration-tests/src/test/resources/systems.yml`. Set the default to `ERP_SYSTEM`: `default: "ERP_SYSTEM"`, uncomment the following two lines (remove the `#` found in the original file) and supply the URL to your SAP S/4HANA system.
```
    - alias: "ERP_SYSTEM"
      uri: "https://myXXXXXX.s4hana.ondemand.com"
```
* Create a `credentials.yml` file (preferably in the same directory) used during tests with the following content, insert  the credentials of your communication user in your SAP S/4HANA Cloud system. Make sure that the file is ignored by any version control system, as it contains credentials.
```
---
credentials:
- alias: "ERP_SYSTEM"
  username: "<USERNAME>"
  password: "<PASSWORD>"
```
* Run the following command to build and test the application. The credentials path is only required if the file is not located in the same folder as `systems.yml`.
```
mvn clean install "-Dtest.credentials=//absolute/path/to/credentials.yml"
```

### 3. Deploy locally
After you have successfully built the project, you can deploy it locally as follows. This will start a local server that hosts your application.
* Configure your local environment by setting the following environment variables. Replace the URL and credentials with the appropriate values for your SAP S/4HANA Cloud system.
  * Adapt the below commands for setting environment variables as appropriate for your operating system. The following commands are for the Windows command line.
```
set destinations="[{name: 'ErpQueryEndpoint', url: '<URL>', username: '<USERNAME>', password: '<PASSWORD>'"
set ALLOW_MOCKED_AUTH_HEADER="true"
```
* Run the following commands to deploy the application on a local server.
```
mvn tomee:run -pl application
```
* Open the URL http://localhost:8080/address-manager in your browser to see the frontend of the launched application.

## Known Issues
None.

## How to Obtain Support
Please reach out to us and the community with your questions on [Stack Overflow](https://stackoverflow.com/tags/s4sdk) (tag: `s4sdk`).
If you have found a bug in the _sample source code_ hosted in this repository, please open an Issue in this GitHub repository.

## License
Copyright (c) 2018 SAP SE or an SAP affiliate company. All rights reserved.
This file is licensed under the Apache Software License, v. 2 except as noted otherwise in the [LICENSE](LICENSE) file.
