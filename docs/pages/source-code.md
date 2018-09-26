---
layout: default
title: Source Code Repository
---
# Source Code Repository
The source code of the Business Partner Address Manager sample application is stored in the [GitHub repository `cloud-s4-sdk-book`](https://github.com/SAP/cloud-s4-sdk-book).
If you are familiar with [git / GitHub](https://help.github.com/articles/set-up-git/), you can simply clone the repository to your local machine.
Otherwise, you can also use the links provided below to download a certain state of the source code as a ZIP archive.

The git repository is organized in branches, where each branch corresponds to the state of the application in a certain chapter of the book.
As the source code gets updated to [new versions of the SAP S/4HANA Cloud SDK](https://help.sap.com/http.svc/rc/6c02295dfa8f47cf9c08a19f2e172901/1.0/en-US/index.html), the branches do not necessarily reflect the state described in the book.
For your reference, the state described in the book is tagged accordingly, with tags prefixed `v1/*` as explained below.

## How to Navigate the Branches
The following table gives an overview of the branches in the repository.
It references the related chapters and briefly describes the state reflected in the branch description.
To inspect the source code of a particular state, either checkout the corresponding branch (if you have cloned the git repository), view the branch on GitHub in your browser via the link in the first column, or download the project as an archive using the links provided in the last column below.
The links always point to the latest version, incorporating updates of the SAP S/4HANA Cloud SDK.
If you want to get the state exactly as described in the book, use the last link in each row, labeled *⇓v1*.

### Overview of Branches

| Branch | Chapters | Description | Download as Archive  |
|---|---|---|---|
| [04_1_archetype](https://github.com/SAP/cloud-s4-sdk-book/tree/04_1_archetype) | 4.1 | Plain project generated from archetype | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/04_1_archetype.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/04_1_archetype.zip)) |
| [04_2_simple](https://github.com/SAP/cloud-s4-sdk-book/tree/04_2_simple) | 4.2–4.5 | Servlet-based implementation, including frontend | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/04_2_simple.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/04_2_simple.zip)) |
| [04_3_resilient](https://github.com/SAP/cloud-s4-sdk-book/tree/04_3_resilient) | 4.6 | ... with resilience via Hystrix commands | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/04_3_resilient.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/04_3_resilient.zip)) |
| [04_4_input_validation](https://github.com/SAP/cloud-s4-sdk-book/tree/04_4_input_validation)`*` | N/A | ... with input validation and error handling (not described in the book, included for reference as a best practice) | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/04_4_input_validation.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/04_4_input_validation.zip)) |
| [05_06_1_security](https://github.com/SAP/cloud-s4-sdk-book/tree/05_06_1_security) | 5–6 | ... including setup of AppRouter, other security-relevant changes, and use of destination service. *Note that due to the security setup you cannot run this state in a local deployment.* | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/05_06_1_security.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/05_06_1_security.zip)) |
| [07_1_multitenancy](https://github.com/SAP/cloud-s4-sdk-book/tree/07_1_multitenancy) | 7 | ... including setup for multi-tenant persistence | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/07_1_multitenancy.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/07_1_multitenancy.zip)) |
| [08_1_REST](https://github.com/SAP/cloud-s4-sdk-book/tree/08_1_REST) | 8.2 | REST API generated with Swagger | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/08_1_REST.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/08_1_REST.zip)) |
| [08_2_OData](https://github.com/SAP/cloud-s4-sdk-book/tree/08_2_OData) | 8.3 | Provisioning an OData v4 service | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/08_2_OData.zip) |
| [15_1_javaee](https://github.com/SAP/cloud-s4-sdk-book/tree/15_1_javaee) | 15.1 | JavaEE-based implementation, including security and multi-tenant persistence | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/15_1_javaee.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/15_1_javaee.zip)) |
| [15_2_spring_boot](https://github.com/SAP/cloud-s4-sdk-book/tree/15_2_spring_boot) | 15.2 | Spring Boot-based implementation, including security and multi-tenant persistence | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/15_2_spring_boot.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/15_2_spring_boot.zip)) |
| [17_1_ml](https://github.com/SAP/cloud-s4-sdk-book/tree/17_1_ml) | 17.2 | Integrating machine learning services into the Servlet-based implementation, see [notes below](#17_1_ml) | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/17_1_ml.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/17_1_ml.zip)) |
| [17_2_blockchain](https://github.com/SAP/cloud-s4-sdk-book/tree/17_2_blockchain) | 17.3 | Integrating blockchain services into the Servlet-based implementation, see [notes below](#17_2_blockchain) | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/17_2_blockchain.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/17_2_blockchain.zip)) |
| [20_1_integrate_in_app](https://github.com/SAP/cloud-s4-sdk-book/tree/20_1_integrate_in_app) | 20.1 | Integrating in-app extensions into the Servlet-based implementation | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/20_1_integrate_in_app.zip) ([⇓v1](https://github.com/SAP/cloud-s4-sdk-book/archive/v1/20_1_integrate_in_app.zip)) |

> `*` denotes that this branch is also kept in sync with the `master` branch, that is, it is an alias

> **Understanding the branch name:**
> The first two digits of the name of a branch denote the respective chapter in the book.
> The next digit is an increasing number that denotes the sequential steps within that chapter.
> The last part of the name is a speaking label for the content of the branch.
For example, `04_3_resilient` refers to the third step in Chapter 4, after implementing resilience into the application.

### Relationship Between Branches
The following diagram illustrates how the branches build up on each other, from top to bottom. For example, the branch `08_1_REST` builds up on the state of branch `04_4_input_validation` and applies unto that the changes described in Chapter 8.1.
```
 o 04_1_archetype
 |
 o 04_2_simple
 |
 o 04_3_resilient
 |
 o 04_4_input_validation*
 |\
 | o 08_1_REST
 |\
 | o 08_2_OData
 |\
 | o 17_1_ml
 |\
 | o 17_2_blockchain
 |\
 | o 20_1_integrate_in_app
 |
 o 05_06_1_security_destinations
 |
 o 07_1_multitenancy
 |\
 | o 15_1_javaee
  \
   o 15_2_spring_boot
```

### Source Code for Other Chapters
You may be wondering where to find the source code described in chapters that are not mentioned above.
Please consider the following explanations:
* Chapter 9: each branch already includes the tests, so there is no separate branch. Just check out the `master` branch and look for the tests in the folders `integration-tests` and `unit-tests`.
* Part III: many of the changes depend on your local setup or are simple enough to be left for your own exercise.
* Chapter 14: each branch already includes the code for the frontend. If you have checked out the `master` branch, look for the folder `application/src/main/webapp/address-manager`.

## Further Notes
Below you find specific setup instructions for certain branches.

### 17_1_ml
The machine learning services used in the book are in the meantime available as proper services on Cloud Foundry, although still in alpha / beta status.
Hence, we have adapted the code to use those services in a more production-ready manner, instead of accessing the sandbox services from the SAP API Business Hub.
The first subsection describes the configuration necessary to run the production-ready variant.
The old configuration, applicable for the state of version  *v1* with the sandbox API, can be found in the second subsection

#### Configuration for production-ready code
You need the following to run the app with machine learning capabilities on Cloud Foundy:
* Create an instance of the machine learning foundation trial beta service (`ml-foundation-trial-beta`) in your Cloud Platform account.
* Bind the service to your application.

If you want to run the application locally with machine learning capabilities, you need to:
* Copy the value of the environment variable `VCAP_SERVICES` from your application on Cloud Foundry (caution. this holds sensitive information).
* Create an environment variable `VCAP_SERVICES` on your local machine that has the copied value.

#### Old configuration
> The following is only applicable for *v1*, not the current state.
> It accesses the machine learning services from the SAP API Business Hub.

You need to add an additional destination to access the machine learning API.
To this end, add a destination called `mlApi` to the `destinations` environment variable and/or the destination service on Cloud Foundry.
The URL of that destination shall be `https://sandbox.api.sap.com/ml`.
The destination needs to have an additional property with key `mlApiKey`.
Supply the API key that you generated on the SAP API Business Hub as the value of the property.

The syntax for the environment variable looks similar to the following (can be supplied as a single line):
```
destinations=[... other destinations, e.g., to SAP S/4HANA systems ..., ↩
   {name: 'mlApi', url: 'https://sandbox.api.sap.com/ml', ↩
     properties: [{key: 'mlApiKey', value: '<insert API key from SAP API Business Hub>'}]} ↩
]
```

To run the integration tests, define a new environment variable `ML_API_KEY` with your API key as value so that it is picked up when running the`MachineLearningServletsTest.java`.

### 17_2_blockchain
To run the application including the Blockchain integration on Cloud Foundry, you need to supply the environment variable `BLACKLIST_CHAINCODE_ID`.
You need to set its value to the chaincode ID from the Hyperledger dashboard, which looks similar to `someguid-with-lett-ersa-nddigits11-com-sap-cloud-sdk-blockchain-example-blacklistChaincode`.

To add an email address to the blacklist, call the URL `/api/blacklistadd?email=example@mail.com`.
To retrieve the count for an address, call `/api/blackliststatus?email=example@mail.com`.
Remember that our application as one participant of the blockchain can only add the same email address once.
Additional calls with the same address will not increase the count beyond 1.
