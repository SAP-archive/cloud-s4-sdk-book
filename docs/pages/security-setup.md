# Security Setup Instructions
This page explains how to setup security for authorization flow with the App router and Authorization and Trust Management service (xsUAA) in SAP Cloud Platform, Cloud Foundry using the provided artifacts in the branch [course/2_3_security](https://github.com/SAP/cloud-s4-sdk-book/tree/course/2_3_security).

Please note that in this unit (2.3) of the corresponding Open SAP course, you can check out the branch [course/2_3_security](https://github.com/SAP/cloud-s4-sdk-book/tree/course/2_3_security) and use this branch as an initial state for the setup in contrast to the previous units, where you modified already exising app. This is done to reduce the setup effort, as this branch already contains required configuration files for your application router and required adaptations to web.xml and manifest.yml to secure your backend micro-service.

In case you have time and want to investigate the topic deeper, we encourage you the also try to secure the address manage application by yourself from scratch. 

## Prerequisites
To execute steps in this document, ensure that npm is installed on your machine.
You can do that by running the command 
```
npm --version
```

If npm is not intalled, you can download and install it from here: https://www.npmjs.com/

## Install the App Router
Make sure that you use the project version from the security branch. You can also download it as a zip archive [here](https://github.com/SAP/cloud-s4-sdk-book/archive/course/2_3_security.zip). This version already includes the pre-defined set of files for the approuter and it also adds the security constraints for your web.xml in the application to protect business partners endpoints.

See the file `<your project location>\cloud-s4-sdk-book\approuter\approuter\package.json`
This is the descriptor required to install the correct version of the approuter using npm tool (NodeJS Packet Manager).
To start the installation run the following commands:
```
cd <your project location>\cloud-s4-sdk-book\approuter\approuter
npm config set @sap:registry https://npm.sap.com
npm install
```

## Configure the App Router
We already provide required configuration files for the App Router. However, you need to adapt some parameters for your execution environment.
In the file `<your project location>\cloud-s4-sdk-book\approuter\xs-security.json`, change substitute the placeholder `<subaccount_id>` with your subaccount ID. For example, your trial subaccount ID can look as follows: `p2000389027trial`.

In the file `<your project location>\cloud-s4-sdk-book\approuter\manifest.yml`:
* Substitute the placeholder `<subaccount_id>` with your subaccount ID. For example, your trial subaccount ID can look as follows: p2000389027trial
* Substitute the placeholder `<generated_random_route>` with your own generated route for your Business Partner Address Manager application that was deployed in previous modules. 
You can check the generated route in your cloud platform cockpit drilling down to your application in your space. Here is the example of my application URL, used in the demo: `address-manager-sublaryngal-subspecies.cfapps.eu10.hana.ondemand.com`, where the generated random route is `sublaryngal-subspecies`.

## Delete existing instances of the XSUAA service
In case you already created an instance of the XSUAA service and called it  my-xsuaa, you should first delete this instance in order to create a new one in the next step. To do that execute the following commands:
```
cf api https://api.cf.eu10.hana.ondemand.com
cf login -u <your email address>

cf unbind-service address-manager my-xsuaa
cf delete-service my-xsuaa
```

## Create a New XSUAA Instance using xs-security.json
To create a new instance of XSUAA using the provided xs-security.json configuration file, execute the following commands in CLI.
You can skip the login if you already have logged in before.

```
cf api https://api.cf.eu10.hana.ondemand.com
cf login -u <your email address>

cf create-service xsuaa application my-xsuaa -c xs-security.json
```

## Create a New Destination Instance
In this unit, file manifest.yml of the address manager application does not include the destination environment variable, pointing to the S/4HANA (mock) server. To retrieve the data from the server in this setup, we need to create a destination service instance and a new destination in SAP Cloud Platform pointing to the mock server.
To create a new instance "my-destination" of the destination service, execute the following command in CLI:
```
cf create-service destination lite my-destination
```

To create a new destination endpoint, go to the SAP Cloud Platform Cockpit and on the subaccount level select Connectivity -> Destinations -> New Destination Enter the following parameters and save your destination:<br>
Name: ErpQueryEndpoint<br>
Type: HTTP<br>
URL: insert URL of your mock server<br>
Proxy Type: Internet<br>
Authentication: NoAuthentication<br>

If you want to connect to a real S/4HANA system instead of the mock server, make sure to choose the appropriate authentication and credentials, for example, BasicAuthentication.

## Remove Mock of the User and Tenant in Your Deployed Application
As we will secure our example application with the App Router and the tenant and user information will be provided via the Json Web Token, we need to remove the user variable used for mocking of those parameters.
To do so, choose `User-Provided Variables` for your application in the cockpit and remove the variable `ALLOW_MOCKED_AUTH_HEADER`.

## Deploy the New Version of the Application and the App Router
To deploy the Business Partner Address Manager application with the introduced security, run the following commands:
```
cd <your project location>\cloud-s4-sdk-book
mvn clean install
cf push
```

To deploy the App Router, run the following commands:
```
cd <your project location>\cloud-s4-sdk-book\approuter
cf push
```

We have protected access to the business partner APIs. Now, you won't see the data coming back from this service via the application, as your user is not authenticated. Application router does not allow to read the data, as well, as our user is not authorized and still requires a role assignement in the SAP Cloud Platform Cockpit.

## Configure Roles, Roles Collections, and User Assignments
Now, follow the instructions in the video to set up your role, role collection, and to assign this role collection to your user. After this is done and you have logged out and logged in to your application router (use <approuter url>/logout for that), you will be able to see the data via the application router.
