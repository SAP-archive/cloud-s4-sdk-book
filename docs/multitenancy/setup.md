---
layout: default
title: Setup Instructions
---
# Evaluation of the tenant-aware local persistence
This guide describes how to evaluate the APIs related to the tenant-aware local persistence with Postman, as shown in the related OpenSAP video. 

## Prerequisites
* Ensure that you have set up the approuter to secure your application, as described in the unit "2.3 Security" 
* Create an instance of the PostgreSQL service in your development space. You can do it using the SAP Cloud Platform Cockpit (navigating to the service marketplace in your development space) or running the following command in the Cloud Foundry CLI:
cf create-service postgresql v9.6-dev my-postgresql
* To evaluate the APIs introduced in this module, you can use Postman. The tool is available for the Download [here](https://www.getpostman.com/)

## How to test tenant-aware local persistence using Postman
For you convenience, we provide you with the [Postman collection](https://github.com/SAP/cloud-s4-sdk-book/blob/course/2_4_multitenancy/docs/multitenancy/BP%20multitenancy.postman_collection.json) that you can simply upload to your Postman and use for the evaluation. Before the upload, make sure that you substitute the placeholders in the template as follows:
* Substitute <your_account> with your account ID. It should looks like <your_Puser>trial and can be found in SAP Cloud Platform Cockpit
![Account ID](https://github.com/SAP/cloud-s4-sdk-book/blob/course/2_4_multitenancy/docs/multitenancy/figures/subaccount.PNG)
* Substitute <your_tenant_number> with the ID of your subaccount, where the application is deployed. You can find the tenant ID in the SAP Cloud Cockpit, as shown in the figure:
![Tenant ID](https://github.com/SAP/cloud-s4-sdk-book/blob/course/2_4_multitenancy/docs/multitenancy/figures/tenantID.PNG)
* Create global variables in Postman <br>
*cookie*: to populate this variable, login to your application using the approuter domain and copy the cookie from the brauser to this global variable in Postman. This is one of the way to provide the valid user session to your postman to connect to application router. <br>
*csrfToken*: this variable will be automatically populated by the initial "GET csrf token" call that you will execute and is required to be set for POST and PUT operations <br>
![Postman global variables](https://github.com/SAP/cloud-s4-sdk-book/blob/course/2_4_multitenancy/docs/multitenancy/figures/PostmanGlobalVars.PNG)

If all the above done, you are set up to execute the queries shown in the video:
* *GET csrf token* will set up the variable *csrfToken* which is used in the POST and PUT queries
* *PUT tenant* does the tenant onboarding calling doPut of TenantProvisioningServlet 
* *GET data* executes HTTP Get method against addresses-local API and does not return the data so far, as we haven't created to local addresses yet
* *POST data* will create a local address in your tenant schema created during the tenant onboarding (PUT tenant)
* Try to run *GET data* again, it should return just created address
* *DELETE tenant* to remove the tenant: this deletes the DB schema and the related data, as it is implemented this way in our application logic.
