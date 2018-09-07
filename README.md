# Instructions to Follow to Build and Deploy a Hello World Application in SAP Cloud Platform, Neo
In the following, we introduce the steps that you need to execute to build your Hello World application using the [SAP S/4HANA Cloud SDK](https://www.sap.com/developer/topics/s4hana-cloud-sdk.html) archetype for SAP Cloud Platform, Neo and to deploy this application locally and in SAP Cloud Platform account.

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

To stop the local server, run the following command:
```
mvn scp:clean -pl application
```

## Deployment in SAP Cloud Platform, Neo
To deploy the application in SAP Cloud Platform, Neo, you can use the SDK for SAP Cloud Platform, Neo or SAP Cloud Platform Cockpit. 
To deploy using the cockpit, go to your account, choose Applications -> Java Applications -> Deploy Application. The screenshot below demonstrates the artifacts and additional parameters that you need to set for the deployment.

![SAP Cloud Platform, Neo Deployment](https://github.com/SAP/cloud-s4-sdk-book/blob/powerweek_helloworld/Deployment.png).
