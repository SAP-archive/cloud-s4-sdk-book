---
layout: default
title: Setup Instructions
---
# Evaluation of tenant-aware local persistence service
This guide describes how to evaluate the APIs related to the tenant-aware local persistence with Postman, as shown in the related OpenSAP video. 

## Prerequisites
* Ensure that you have set up the approuter to secure your application, as described in the unit "2.3 Security" 
* Create an instance of the PostgreSQL service in your development space. You can do it using the SAP Cloud Platform Cockpit (navigating to the service marketplace in your development space) or running the following command in the Cloud Foundry CLI:
cf create-service postgresql v9.6-dev my-postgresql
* To evaluate the APIs introduced in this module, you can use Postman. The tool is available for the Download [here](https://www.getpostman.com/)

## How to test tenant-aware local persistence using Postman
For you convenience, we provide you with the Postman collection that you can simply upload to your Postman and use for the evaluation. Make sure that you substitute the place holders in the template as follows:
