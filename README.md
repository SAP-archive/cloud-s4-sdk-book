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

## <a name="task0">Task 0: Preparation steps</a>
Before, we get started with the actual implementation, we need to perform some preparation steps and familiarize ourselves with the project structure. 
* Download the [archive with the initial project version](https://github.com/SAP/cloud-s4-sdk-book/archive/ml-codejam.zip) from the GitHub
* Load into your IDE as a Maven project
* Investigate your project structure:
..* **application** folder contains the business logic that we will extend in this code jam. It also contains the JS based frontend components in the **webapp** subfolder. We will only focus on backend components, though.
..* **integration-tests** and **unit-tests** folders include integration and unit tests. We have already prepared the integration tests for your application, they do not pass yet, though.
..* Artifacts **cx-server**, **Jenkinsfile**, **pipeline_config.yml** help to set up and customize CI/CD server and the pipeline for your SDK based solutions. We will not cover this topic in this code jam, but we highly encourage you to check out [the related resources after the workshop](https://blogs.sap.com/2017/09/20/continuous-integration-and-delivery/)
..* **pom.xml** is a [maven configuration file](https://maven.apache.org/pom.html)
..* **manifest.yml** is a deployment descriptor to be able to deploy the application in SAP Cloud Platform, Cloud Foundry.

explain the local deployment

## <a name="task1">Task 1: Retrieve SAP S/4HANA data using the SAP S/4HANA Cloud SDK virtual data model</a>

## <a name="task2">Task 2: Integrate SAP Leonardo Machine Learning service to provide translations</a>

## <a name="task3">Bonus, Task 3: Write data back to SAP S/4HANA using the SAP S/4HANA Cloud SDK virtual data model</a>

## <a name="task4">Bonus, Task 4: Integrate advanced ML capabilities</a>
