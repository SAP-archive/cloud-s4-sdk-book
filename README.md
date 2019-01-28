# SAP Leonardo Machine Learning Foundation and the SAP S/4HANA Cloud SDK
Here, we provide the instructions to proceed with the code jam "SAP Leonardo Machine Learning and the SAP S/4HANA Cloud SDK". Below, you find the following information:
* [Technical prerequisites](#prerequisites): software required to execute the steps described in this documentation. This information was provided before the workshop, so, we assume that those prerequisites are already fulfilled. Nevertheless, you can use this description to double check.
* [Task 0: Preparation steps](#task0)
* [Task 1: Retrieve SAP S/4HANA data using the SAP S/4HANA Cloud SDK virtual data model](#task1)
* [Task 2: Integrate SAP Leonardo Machine Learning service to provide translations](#task2)
* [Bonus, Task 3: Write data back to SAP S/4HANA using the SAP S/4HANA Cloud SDK virtual data model](#task3)

So, let us get started!

## <a name="prerequisites">Technical prerequisites</a>
Please, find the local setup and how to install the required software in the blog post [Step 1 with SAP S/4HANA Cloud SDK: Set up](https://blogs.sap.com/2017/05/15/step-1-with-sap-s4hana-cloud-sdk-set-up/).
Make sure to install all the mentioned tool, including the IDE. All the exercises in the code jam are based on the local development environment.

Verify your installation by running `javac -version` and `mvn -version` on a command line shell. The output should look similar to the following:
```
> javac -version
javac 1.8.0_72

> mvn -version
Apache Maven 3.5.0 (ff8f5e7444045639af65f6095c62210b5713f426; 2017-04-03T21:39:06+02:00)
Maven home: C:\Program Files\path\to\maven\bin\..
Java version: 1.8.0_72, vendor: Oracle Corporation
Java home: C:\Program Files\path\to\java\jdk1.8.0_72\jre
...
```
Check that the environment variable `JAVA_HOME` points to the path of your JDK installation, e.g., `C:\Program Files\path\to\java\jdk1.8.0_72`.

We will deploy the application in SAP Cloud Platform, Cloud Foundry. For that purpose, you would require your own trial account. [Here](https://cloudplatform.sap.com/try.html), you can find information on how to get your trial account in SAP Cloud Platform, Cloud Foundry. 

For this workshop, we provide a running SAP S/4HANA Mock server that mocks business partner APIs, so you do not need to set up it by yourself. You can assess the mock server via the URL: https://odata-mock-server-shy-sitatunga.cfapps.eu10.hana.ondemand.com/. The mock server does not require authentication.
In case you want to try out this hands on later and the service is not available, follow [this description](https://sap.github.io/cloud-s4-sdk-book/pages/mock-odata.html) to set up your own instance of the mock server.

## <a name="task0">Task 0: Preparation steps</a>
Before, we get started with the actual implementation, we need to perform some preparation steps and familiarize ourselves with the project structure. 
* Download the [archive with the initial project version](https://github.com/SAP/cloud-s4-sdk-book/archive/ml-codejam.zip) from the GitHub
* Load into your IDE as a Maven project
* Investigate your project structure:
  * **application** folder contains the business logic that we will extend in this code jam. It also contains the JS based frontend components in the **webapp** subfolder. We will only focus on backend components, though.
  * **integration-tests** and **unit-tests** folders include integration and unit tests. We have already prepared the integration tests for your application, they do not pass yet, though, and therefore are ignored for now.
  * **solutions** is another important folder that you can use when you get need help with the exercises and want to look up the solution. It contains the full source code of the solutions.
  * Artifacts **cx-server**, **Jenkinsfile**, **pipeline_config.yml** help to set up and customize CI/CD server and the pipeline for your SDK based solutions. We will not cover this topic in this code jam, but we highly encourage you to check out [the related resources after the workshop](https://blogs.sap.com/2017/09/20/continuous-integration-and-delivery/)
  * **pom.xml** is a [maven configuration file](https://maven.apache.org/pom.html)
  * **manifest.yml** is a deployment descriptor to be able to deploy the application in SAP Cloud Platform, Cloud Foundry.

Before we get started with the development, let us build and deploy the current state of the application locally.

### Build and test
While building the application, we will execute integration tests. For the integration tests, you need to provide the URL and credentials of your SAP S/4HANA system.
* Open the file `integration-tests/src/test/resources/systems.yml`.
As we will be using the pre-deployed mock server in this code jam, please make sure that the file contains the following content:
```
---
erp:
  default: "MOCK_SYSTEM"
  systems:
#    - alias: "ERP_SYSTEM"
#      uri: "https://myXXXXXX.s4hana.ondemand.com"
#      proxy: "http://proxy:8080"
    - alias: "MOCK_SYSTEM"
      uri: "https://odata-mock-server-shy-sitatunga.cfapps.eu10.hana.ondemand.com/"

```
In case you are working on this code jam after the seminar, make sure to substitute the mock server URL with your own one.
* In the same directory, create a `credentials.yml` file used during tests with the following content:
```
---
credentials:
- alias: "MOCK_SYSTEM"
  username: "(username)"
  password: "(password)"
```
As the mock server does not require authentication, you do not need to update the username and password in this file.
* In the root folder of the project, run the following command to build and test the application:
```
mvn clean install
```

### Deploy locally
After you have successfully built the project, you can deploy it locally as follows. This will start a local server that hosts your application.
* Configure your local environment by setting the following environment variables. Replace the URL and credentials with the appropriate values for your SAP S/4HANA Cloud system in case you are not using the provided mock server.
* Adapt the below commands for setting environment variables as appropriate for your operating system. The following commands are for the Windows command line:
```
set destinations=[{name: 'ErpQueryEndpoint', url: 'https://odata-mock-server-shy-sitatunga.cfapps.eu10.hana.ondemand.com/', username: 'USERNAME', password: 'PASSWORD'}]
set ALLOW_MOCKED_AUTH_HEADER=true
```
For Mac, use the following commands:
```
export destinations="[{name: 'ErpQueryEndpoint', url: 'https://odata-mock-server-shy-sitatunga.cfapps.eu10.hana.ondemand.com/', username: 'USERNAME', password: 'PASSWORD'}]"
export ALLOW_MOCKED_AUTH_HEADER=true
```
* Run the following commands to deploy the application on a local server.
```
mvn tomee:run -pl application
```
* Open the URL http://localhost:8080/address-manager in your browser to see the frontend of the launched application.

At this phase, we do not have any data returned from the application and we see the runtime exception in the console, saying that we need to implement the functionality. Let us start with the first step: integrating SAP S/4HANA into this application using the SAP S/4HANA Cloud SDK.

## <a name="task1">Task 1: Retrieve SAP S/4HANA data using the SAP S/4HANA Cloud SDK virtual data model</a>
In this step, we will investigate two queries to SAP S/4HANA to retrieve business partner data. Firstly, we will retrieve the list of business partners for the list view in  the application. Secondly, we will take a look at the query retrieving detailed data of a single business partner by ID.

### Implement the SAP S/4HANA Integration
Start the development of queries by looking into the class BusinessPartnerServlet, which is the servlet exposing the business partner APIs. 
We could use any API framework here, such as JAX-RS or Spring. However, we use a servlet here for simplicity. Looking into the servlet, we can see that the main functionality is moved out into the commands GetAllBusinessPartnersCommand and GetSingleBusinessPartnerByIdCommand. Open and implement the command *GetAllBusinessPartnersCommand* as explained below.

The *GetAllBusinessPartnersCommand* should return a list of available business partners in the ERP system. The class was already created. We just have to implement the execute method:
* The instance of the class *BusinessPartnerService* already provides a method to retrieve all business partners. Type *service* to see a list of all available methods. Use the method *getAllBusinessPartner* to fetch multiple business partner entities.
* We only want to return the properties first name, last name and id. Thus, select only these properties by using the *select* method on the result from step 1. Luckily, we do not have to know the exact names of these properties in the public API of S/4HANA. They are codified as static member of the class *BusinessPartner*. We can select the business partner id by using *BusinessPartner.BUSINESS_PARTNER*. Please, do the same for the first name and last name.
* There are multiple categories of business partners. In this session, we only want to retrieve persons. The category is identified by a number, which is stored in the static class variable called *CATEGORY_PERSON*. The method to filter is called *filter* and can be executed on the result from the previous step.
The property *BusinessPartner.BUSINESS_PARTNER_CATEGORY* should equal *CATEGORY_PERSON*. To express that use the methods provided by the object *BusinessPartner.BUSINESS_PARTNER_CATEGORY*.
* All the previous steps did not execute any requests, but just defined the request. With the method *execute* you finally execute the query and retrieve the result.
Hint: Try to solve it on your own. However, the solution can also be found in the solution folder in the session material.

Now, also take a look at he command *GetSingleBusinessPartnerByIdCommand*. It was already implemented for you. Based on this source code, can you find out how the OData "expand" method can be implemented using the Virtual Data Model of the SAP S/4HANA Cloud SDK? Hint: addresses of business partners are retrieved using *expand*.

To check whether the queries are implemented correctly, go to the integration-tests folder and remove the *@Ignore* annotation for the following tests: *BusinessPartnerServletTest.testGetAll()* and *BusinessPartnerServletTest.testGetSingle()*.
Now, build and test the application and make sure that the tests ran successfully. 
```
mvn clean install
```

If the uncommented test do not show errors, congratulations! You have successfully integrated SAP S/4HANA with your application. 

### Deploy Locally
You can also deploy the application locally and see the business partner data from the S/4HANA Mock server:
```
mvn tomee:run -pl application
```

![Business partner address manager](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/AddressManager.PNG)

### Deploy in SAP Cloud Platform, Cloud Foundry
Generally, you can use several ways to deploy your applications in SAP Cloud Platform. The recommended way to do it for productive applications is to use the [Continuous Delivery Toolkit](https://github.com/SAP/cloud-s4-sdk-pipeline), which also ensures that your source code is properly tested and checked before being deployed. 
Alternatively, you can do it manually using the [CLI of Cloud Foundry](https://docs.cloudfoundry.org/cf-cli/install-go-cli.html).
Here, we describe how to deploy your application manually using the SAP Cloud Platform, Cloud Foundry Cockpit. Necessary preliminary steps are also described here.
* Create service instances for S/4HANA connectivity
* Create destination endpoints
* Deploy the application using the SAP Cloud Platform cockpit

Firstly, create an instance of the destination service to connect to SAP S/4HANA (mock) system. For that, in the cloud platform cockpit on the level of your development space choose Services -> Service Marketplace and choose the destination service from the catalog.
Instantiate the service with all the default parameters. Give the name my-destination to your instance.
![Destination service in the Service Marketplace](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/destination.PNG)

Secondly, create an instance of the Authorization and Trust Management service. In the Service Marketplace, choose the Authorization and Trust Management service and instantiate it with the default parameters. Give the name my-xsuaa to your service instance.
![Authorization and Trust Management](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/uaa.PNG)

Next, we will create a destination endpoint to connect to the S/4HANA mock server.
You can find the configuration of the destination endpoints on the level of your subaccount by choosing Connectivity -> Destinations.
Then, you can create a new destination endpoint by choosing "New Destination".

For the S/4HANA connectivity, create the destination with the following parameters: <br>
Name: ErpQueryEndpoint <br>
Type: HTTP <br>
URL: https://odata-mock-server-shy-sitatunga.cfapps.eu10.hana.ondemand.com/ <br>
Proxy type: Internet <br>
Authentication: NoAuthentication <br>

Now, we are ready to deploy our application in SAP Cloud Platform, Cloud Foundry.
Firstly, go to your project *manifest.yml* file and adapt the application name, adding your P-user to avoid domain collisions with the other participants. For example:

```
---
applications:

- name: address-manager-ml-Pxxxxxxxxxx
  memory: 768M
  random-route: true
  path: application/target/address-manager-application.war
  buildpack: sap_java_buildpack
  env:
    TARGET_RUNTIME: tomee
    JBP_CONFIG_SAPJVM_MEMORY_SIZES: 'metaspace:96m..'
    SET_LOGGING_LEVEL: '{ROOT: INFO, com.sap.cloud.sdk: INFO}'
    ALLOW_MOCKED_AUTH_HEADER: true
  services:
    - my-xsuaa
    - my-destination
```

Secondly, in your development space, choose Application -> Deploy Application. Choose the location of your archive (see the folder application/target/address-manager-application.war) and the corresponding manifest.yml file, as shown in the Figure.

![Application Deployment](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/deployment.PNG)

When the application is deployed, you can drill down into the application, choose the link for the application and append "/address-manager" to it. You should be able to see the business partner coming back from the mock server.

If you still have time, continue with the next task.
In the next step, we will see how to integrate one of the SAP Leonardo Machine Learning services in few lines of code.

## <a name="task2">Task 2: Integrate SAP Leonardo Machine Learning service to provide translations</a>
In this step, we will integrate SAP Leonardo Machine Learning services into an application using an example of the translation service, which is a part of the set of functional services.

There are several steps involved to make the integration with SAP Leonardo ML services work, those steps are described in details below:
* Implement the integration with ML services in Java Backend
* Create service instance for Leonardo ML integration
* Deploy the application using the SAP Cloud Platform cockpit

### Implement the integration with ML services in Java Backend 
To implement the integration with ML services, we will leverage the SAP S/4HANA Cloud SDK component that simplifies the integration and handles the boilerplate code for you, such as OAuth 2.0 authentication against ML services.

To implement the integration, find the package *machinelearning* in your project, where you will find the *TranslateServlet* class. This class contains the method *translate()*, which delegates the translation logic to the commands *MlLanguageDetectionCommand* for the language detection and *MlTranslationCommand* for the translation.

Navigate to the class *MlTranslationCommand* and investigate its methods. Here, in the method *executeRequest*, you will find the next task. 
In this method, we already provide the logic for the execution of the translation request using the instance of *LeonardoMlService* class and retrive the resulting payload. The rest is left for you. To make the translation work in integration with your application, add the following steps into the *executeRequest* method:

* In you IDE, navigate to the LeonardoMlService, which is a part of the *machinelearning* package of the SAP S/4HANA Cloud SDK and investigate its methods. Also, looks through the other classes and methods provided in this library. You may also use the [Javadoc for those classes](https://help.sap.com/http.svc/rc/76ceac609c19443099fca151cf9c9e21/1.0/en-US/com/sap/cloud/sdk/services/scp/machinelearning/package-summary.html) to get more information.
* Instantiate the *LeonardoMlService* class, which is a part of the SAP S/4HANA Cloud SDK component for ML services integration. Consider that you use trial beta as Cloud Foundry Leonardo ML service type and Translation as a Leonardo ML service type.
* Create an object request of type *HttpPost*
* Create an object body of type *HttpEntity* (*StringEntity*). Use *requestJson* and *ContentType.APPLICATION_JSON* to instantiate the object.
* Add the created body to the request using the method *setEntity*.

If you experience difficulties, you can compare you solution with the one provided in the [folder solutions](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/solutions/application/src/main/java/com/sap/cloud/s4hana/examples/addressmgr/machinelearning/commands/MlTranslationCommand.java).

Build the latest version using the command:
```
mvn clean install
```

### Create service instances for Leonardo ML integration
In the cloud platform cockpit on the level of your development space choose Services -> Service Marketplace and choose ml-foundation-trial-beta. Instantiate the service with the defailt parameters and give it the name my-ml.
![SAP Leonardo Machine Learning](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/ml.PNG)

### Deploy the application using the SAP Cloud Platform cockpit
Finally, we will deploy the application in your development space in SAP Cloud Platform, Cloud Foundry, as it was done in the previous task.

Firstly, go to your project *manifest.yml* file and add the service my-ml into the section "services" to make sure that the newly create machine learning service will be bound to our application. Please, see in the example below:

![Service bindings in the deployment descriptor manifest.yml](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/manifest.PNG)

```
---
applications:

- name: address-manager-ml-Pxxxxxxxxxx
  memory: 768M
  random-route: true
  path: application/target/address-manager-application.war
  buildpack: sap_java_buildpack
  env:
    TARGET_RUNTIME: tomee
    JBP_CONFIG_SAPJVM_MEMORY_SIZES: 'metaspace:96m..'
    SET_LOGGING_LEVEL: '{ROOT: INFO, com.sap.cloud.sdk: INFO}'
    ALLOW_MOCKED_AUTH_HEADER: true
  services:
    - my-xsuaa
    - my-destination
    - my-ml
```

In case you deploy using the Cloud Platform Cockpit and if you have already deployed the application in SAP Cloud Platform in Task 1, you would need to remove the previous instance (this is not required if you use Command Line Interface for the deployment). Also note that in productive scenarios the delition won't be required and you will achieve no downtime with the blue-green deployments with tools such as the [Continuous Delivery Toolkit](https://blogs.sap.com/2017/09/20/continuous-integration-and-delivery/).

Then choose Application -> Deploy Application. Choose the location of your archive (see the folder application/target/address-manager-application.war) and the corresponding manifest.yml file, as shown in the Figure.

![Application Deployment](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/deployment.PNG)

As the deployment descriptor of the application (manifest.yml) was containing the instructions to bind the application instance to the Machine Learning instance, we can also now see this binding in the cockpit. To investigate this, drill down to your deployed application and choose "Service Bindings", where you will find the binding the the instance my-ml.

![SAP Leonardo ML service binding](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/mlServiceBinding.PNG)

If you drill click the link "my-ml", you can see all the URLs of ML services, available in the scope of the chosed ml-foundation-trial-beta service. Those URLs are used behind the scenes by the SDK class LeonardoMlService to execute corresponding queries. Among others, you can also find the URL for the translation service that we connect in this code jam.

![SAP Leonardo ML services information](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/mlURLs.PNG)

When the application is deployed, you can drill down into the application, choose the link for the application and append "/address-manager" to it. You should be able to see the business partner coming back from the mock server and you should be able to translate their professions by clicking on them.

![Result of the deployment](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/deploymentResult.PNG)

![Business Partner Address Manager with the integrated translation service](https://github.com/SAP/cloud-s4-sdk-book/blob/ml-codejam/docs/pictures/Translation.PNG)

Congratulations! You have just finished the main steps in this code jam:
* Firstly, we have integrated SAP S/4HANA Business Partner APIs to read the list of business partners and to read the detailed information
* Secondly, we have integrated SAP Leonardo Machine Learning functional service, using the Translation as an example.

Continue to the next steps in case you have time. Alternatively, you can execute those steps offline.

## <a name="task3">Bonus, Task 3: Write data back to SAP S/4HANA using the SAP S/4HANA Cloud SDK virtual data model</a>
Here, we will further investigate the capabilities of the SAP S/4HANA Cloud SDK virtual data model to integrate SAP S/4HANA now also for update, and delete operations.

*	The class BusinessPartnerService already offers methods to create, update or delete address. The input values, such as the addresses or IDs to delete are member variables of the commands. They are passed into the command from the servlet.
*	Implement the run methods in the commands UpdateAddressCommand and DeleteAddressCommand. 
*	For the delete method we first have to create a business partner address instance, which has the IDs for the business partner and the address specified. The class BusinessPartnerAddress offers the method builder to create a builder and can be used as follows:

```
BusinessPartnerAddress addressToDelete = BusinessPartnerAddress.builder()
        .businessPartner(businessPartnerId)
        .addressID(addressId)
        .build();
```

*	Two commands expect an integer to be returned as result. These integers should correspond to the status code returned from SAP S/4HANA as result of the modification. You can simple call getHttpStatusCode to get the status code back.

Try to implement the queries by yourself. Feel free to check out the solution folder that we have prepared for you in case you are experiencing difficulties.

To test your logic, we have already prepared the tests. Go the the class AddressServletTest, which resides in the integration-tests module and remove all @Ignore annotations. Run the tests in this class and make sure that all tests are green. If not, get back to your commands and fix the issues.

If the tests are successful, you can now deploy the application locally or in SAP Cloud Platform, as show before to test the new capabilities of your application from the user interface.
