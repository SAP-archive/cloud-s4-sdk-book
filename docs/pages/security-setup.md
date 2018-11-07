# Security Setup Instructions
This page explains how to setup security for authorization flow with 
the App router and Authorization and Trust Management service (xsUAA) 
in SAP Cloud Platform, Cloud Foundry using the provided artifacts.

## Prerequisites
To execute steps in this document, ensure that npm is installed on your machine.
You can do that by running the command 
```
npm --version
```

If npm is not intalled, you can download and install it from here: https://www.npmjs.com/

## Install the App Router
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
   
## Create a New XSUAA Instance using xs-security.json
To create a new instance of XSUAA using the provided xs-security.json configuration file, execute the following commands in CLI:
```
cf api https://api.cf.eu10.hana.ondemand.com
cf login -u <your email address>

cf unbind-service address-manager my-xsuaa
cf delete-service my-xsuaa
cf create-service xsuaa application my-xsuaa -c xs-security.json
```
## Create a New Destination Instance using xs-security.json
To create a new instance "my-destination" of the destination service, execute the following command in CLI:
```
cf create-service destination lite my-destination
```

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
