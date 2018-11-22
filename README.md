# SAP Leonardo Machine Learning und the SAP S/4HANA Cloud SDK
Here, we provide the instructions to proceed with the code jam "SAP Leonardo Machine Learning and the SAP S/4HANA Cloud SDK". Below, you find the following information:
* [Technical prerequisites](#prerequisites): software required to execute the steps described in this documentation. This information was provided before the workshop, so, we assume that those prerequisites are already fulfilled. Nevertheless, you can use this description to double check.
* [Task 0: Preparation steps](#task0)
* [Task 1: Retrieve SAP S/4HANA data using the SAP S/4HANA Cloud SDK virtual data model](#task1)
* [Task 2: Integrate SAP Leonardo Machine Learning service to provide translations](#task2)
* [Bonus, Task 3: Write data back to SAP S/4HANA using the SAP S/4HANA Cloud SDK virtual data model](#task3)
* [Bonus, Task 4: Integrate advanced ML capabilities](#task4)

So, let us get started!

## <a name="prerequisites">Technical prerequisites</a>
Please, find the local setup and how to install the required software in the blog post [Step 1 with SAP S/4HANA Cloud SDK: Set up](https://blogs.sap.com/2017/05/15/step-1-with-sap-s4hana-cloud-sdk-set-up/).
Make sure to install all the mentioned tool, including the IDE. All the exercises in the code jam are based on the local development environment.

We will deploy the application in SAP Cloud Platform, Cloud Foundry. For that purpose, you would require your own trial account. [Here](https://cloudplatform.sap.com/try.html), you can find information on how to get your trial account in SAP Cloud Platform, Cloud Foundry. 

For this workshop, we provide a running SAP S/4HANA Mock server that mocks business partner APIs, so you do not need to set up it by yourself. The server is accessable via URL https://bupa-mock-odata-sagittal-inserter.cfapps.eu10.hana.ondemand.com and does not require authentication.
In case you want to try out this hands on later and the service is not available, follow [this description](https://sap.github.io/cloud-s4-sdk-book/pages/mock-odata.html) to set up your own instance of the mock server.

## <a name="task0">Task 0: Preparation steps</a>
Before, we get started with the actual implementation, we need to perform some preparation steps and familiarize ourselves with the project structure. 
* Download the [archive with the initial project version](https://github.com/SAP/cloud-s4-sdk-book/archive/ml-codejam.zip) from the GitHub
* Load into your IDE as a Maven project
* Investigate your project structure:
  * **application** folder contains the business logic that we will extend in this code jam. It also contains the JS based frontend components in the **webapp** subfolder. We will only focus on backend components, though.
  * **integration-tests** and **unit-tests** folders include integration and unit tests. We have already prepared the integration tests for your application, they do not pass yet, though.
  * Artifacts **cx-server**, **Jenkinsfile**, **pipeline_config.yml** help to set up and customize CI/CD server and the pipeline for your SDK based solutions. We will not cover this topic in this code jam, but we highly encourage you to check out [the related resources after the workshop](https://blogs.sap.com/2017/09/20/continuous-integration-and-delivery/)
  * **pom.xml** is a [maven configuration file](https://maven.apache.org/pom.html)
  * **manifest.yml** is a deployment descriptor to be able to deploy the application in SAP Cloud Platform, Cloud Foundry.

Before we get started with the development, let us build and deploy the current state of the application locally.

### Build and test
While building the application, we will execute integration tests. For the integration tests, you need to provide the URL and credentials of your SAP S/4HANA system.
* Open the file `integration-tests/src/test/resources/systems.yml`. Set the default to `MOCK_SYSTEM`: `default: "MOCK_SYSTEM"`, uncomment the following two lines (remove the `#` found in the original file) and supply the URL to your SAP S/4HANA system or Mock server. In the code jam we will use the provided mock server URL.
```
    - alias: "MOCK_SYSTEM"
      uri: "https://bupa-mock-odata-sagittal-inserter.cfapps.eu10.hana.ondemand.com"
```
* Create a `credentials.yml` file (preferably in the same directory) used during tests with the following content:
```
---
credentials:
- alias: "MOCK_SYSTEM"
  username: "(username)"
  password: "(password)"
```
* In the root folder of the project, run the following command to build and test the application. The credentials path is only required if the file is not located in the same folder as `systems.yml`.
```
mvn clean install "-Dtest.credentials=//absolute/path/to/credentials.yml"
```

### Deploy locally
After you have successfully built the project, you can deploy it locally as follows. This will start a local server that hosts your application.
* Configure your local environment by setting the following environment variables. Replace the URL and credentials with the appropriate values for your SAP S/4HANA Cloud system in case you are not using the provided mock server.
  * Adapt the below commands for setting environment variables as appropriate for your operating system. The following commands are for the Windows command line.
```
set destinations=[{name: 'ErpQueryEndpoint', url: 'https://bupa-mock-odata-sagittal-inserter.cfapps.eu10.hana.ondemand.com', username: 'USERNAME', password: 'PASSWORD'}]
set ALLOW_MOCKED_AUTH_HEADER=true
```
* Run the following commands to deploy the application on a local server.
```
mvn tomee:run -pl application
```
* Open the URL http://localhost:8080/address-manager in your browser to see the frontend of the launched application.

At this phase, we do not have any data returned from the application and we see the runtime exception in the console, saying that we need to implement the functionality. Let us start with the first step: integrating SAP S/4HANA into this application using the SAP S/4HANA Cloud SDK.

## <a name="task1">Task 1: Retrieve SAP S/4HANA data using the SAP S/4HANA Cloud SDK virtual data model</a>

## <a name="task2">Task 2: Integrate SAP Leonardo Machine Learning service to provide translations</a>

## <a name="task3">Bonus, Task 3: Write data back to SAP S/4HANA using the SAP S/4HANA Cloud SDK virtual data model</a>

## <a name="task4">Bonus, Task 4: Integrate advanced ML capabilities</a>
