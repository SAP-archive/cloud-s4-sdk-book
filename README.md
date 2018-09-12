# Instructions to Follow to Build and Deploy Business Partner Address Manager Application in SAP Cloud Platform, Neo
Here, we describe the steps that need to be executed to build and run Business Partner Address Manager application in SAP Cloud Platform, Neo.

## Prerequisites
To follow these instructions, you first need to generate the Hello World project from the SAP S/4HANA Cloud SDK archetype for SAP Cloud Platform, Neo.
For this, please proceed as described in the [following instructions](https://github.com/SAP/cloud-s4-sdk-book/blob/powerweek_helloworld/README.md).

## Prepare Project Structure
Download zip file from the [following URL](https://github.com/SAP/cloud-s4-sdk-book/archive/powerweek_copyover.zip) and unzip it into the folder of your choice.
Copy the src folder into the "../address-manager/application" folder generated in the previous step.

## Implement SAP S/4HANA Query in Class GetAllBusinessPartnersCommand
The current version of the application does not return any business partners. To fix this gap, we need to implement the getAllBusinessPartner query in the corresponding class.
Find the TODO in the class and implement the following query using the [Virtual Data Model of the SAP S/4HANA Cloud SDK](https://blogs.sap.com/2017/05/21/step-4-with-sap-s4hana-cloud-sdk-calling-an-odata-service/):
* Use get all business partner method
* Select only the following fields: business partner id, first name, last name
* Filter selected business partner by business partner category "person"
* Order the business partner by the last name ascending
* Execute the query and return the result of the execution

With that, the required integration functionality is implemented. Proceed to the next steps, to build and deploy the application.

## Connecting to SAP S/4HANA
It does not matter whether you run your code against SAP S/4HANA On-Premise or Cloud, source code will not change, just the configuration of the system destination.
S/4HANA Cloud is accessable via the following URL: https://my301481.s4hana.ondemand.com

Alternatively, we provide an SAP S/4HANA mock server that mocks business partner OData services whitelisted in [SAP API business hub](https://api.sap.com/api/API_BUSINESS_PARTNER/resource).
You have two options:
* Simple option: just use the URL of the already running mock server: https://bupa-mock-odata-tonsorial-occultness.cfapps.eu10.hana.ondemand.com
* Alternatively, you can deploy and run your own version of the mock server, as described [here](https://sap.github.io/cloud-s4-sdk-book/pages/mock-odata.html). Please note that Mock server is a SAP Cloud Platform, Cloud Foundry application and you would need SAP Cloud Platform, Cloud Foundry account to be able to run it.

## Deploy Application in SAP Cloud Platform, Neo in Trial Account
Before we deploy and run the application in SAP Cloud Platform, we will configure the target SAP S/4HANA system, to which the application will be talking to.
For that, you need to configure Destination in the SAP Cloud Platform Cockpit. In the source code, we did not specify any special name of the destination. 
So, the default name will be used by the application, which is "ErpQueryEndpoint". To configure the destination, go to you trial account of SAP Cloud Platform, Neo and select
Connectivity -> Destinations in the left menu bar. Configure the destination exactly as shown in the screenshot below.

![Configure Destination](https://github.com/SAP/cloud-s4-sdk-book/blob/powerweek_copyover/ErpQueryEndpoint.PNG)

After the destination is configured you can deploy the application using the Cloud Cockpit, by navigating to your account, selecting Java Application -> Deploy Application, as shown in the screenshot below.

![Deploy application](https://github.com/SAP/cloud-s4-sdk-book/blob/powerweek_copyover/Deployment.png)

## Optional: Build and Deploy Application Locally
Build application by executing the following Maven command:
```
mvn clean install
```

To run locally and to connect against the running SAP S/4HANA mock server, execute the following command:
```
mvn scp:clean scp:push -pl application -Derp.url=https://bupa-mock-odata-tonsorial-occultness.cfapps.eu10.hana.ondemand.com
```

After that, you can access the application using the following URL:
http://localhost:8080/address-manager-application/address-manager

Please note, that in the current version, you can only read business partners and their addresses. In case you want to execute update, create, delete operations on addresses, you
will need to implement the following classes: UpdateAddressCommand, CreateAddressCommand, DeleteAddressCommand. You can do it using the Virtual Data Model of the SAP S/4HANA Cloud SDK, as well.
We encourage you to experiment with this after the session.

To stop the local server, run the following command:
```
mvn scp:clean -pl application
```



