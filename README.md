# Instructions to Follow to Build and Deploy a Hello World Application in SAP Cloud Platform, Neo
In the following, we introduce the steps that you need to execute to build your Hello World application using the [SAP S/4HANA Cloud SDK](https://www.sap.com/developer/topics/s4hana-cloud-sdk.html) archetype for SAP Cloud Platform, Neo and to deploy this application locally and in SAP Cloud Platform account.

## Prerequisites
Before we can continue with the generation step, we need to make sure that the required software is installed. The blog post [Set up](https://blogs.sap.com/2017/05/15/step-1-with-sap-s4hana-cloud-sdk-set-up/) describes how to install JDK and Maven on your local machine. Run the following commands to check the results:
1. Check Java version:
```
java -version
```
This command should reeturn java 8 version (1.8.*)

2. Check Maven version:
```
mvn -version
```
This command should display maven version. In case of issues, double check the following description of the [maven installation process](https://maven.apache.org/install.html).

In addition, make sure that you have Eclipse with the corresponding Maven Pluging or IntelliJ installed. This is required to do programming exercises more efficiently.

## Generating the Application from a Maven Archetype
To generate the project from the SDK archetype in the batch mode, run the following command in your command line:
```
mvn archetype:generate -B -DarchetypeGroupId=com.sap.cloud.s4hana.archetypes -DarchetypeArtifactId=scp-neo-javaee7 -DarchetypeVersion=LATEST -DgroupId=com.sap.cloud.s4hana.examples -DartifactId=address-manager -Dversion=1.0-SNAPSHOT
```
This generates your project structure, which you can import into the IDE of your choice as "Maven Project".

## Building the Application
Before we can deploy created application locally or in SAP Cloud Platform, we need to build the deployable archive. For that, run the following commands in your command line:
```
cd address-manager
mvn clean install
```

## Local Deployment
To deploy the application locally run the following command in your command line:
```
mvn scp:clean scp:push -pl application
```
After that, you can use the following URL to access the deployed application:
http://localhost:8080/address-manager-application

Note that it can take few minutes before the application will get accessible. 
After the application will be accessible, you will get the login page. You can use the following credentials for the login:
User: test, password: test.

To stop the local server, run the following command:
```
mvn scp:clean -pl application
```

## Deployment in SAP Cloud Platform, Neo
To deploy the application in SAP Cloud Platform, Neo, you can use the SDK for SAP Cloud Platform, Neo or SAP Cloud Platform Cockpit. 
To deploy using the cockpit, go to your account, choose Applications -> Java Applications -> Deploy Application. The screenshot below demonstrates the artifacts and additional parameters that you need to set for the deployment.

![SAP Cloud Platform, Neo Deployment](https://github.com/SAP/cloud-s4-sdk-book/blob/powerweek_helloworld/Deployment.png).
