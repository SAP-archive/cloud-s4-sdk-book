---
layout: default
title: Source Code Repository
---
# Source Code Repository
The source code of the Business Partner Address Manager sample application is stored in the [GitHub repository `cloud-s4-sdk-book`](https://github.com/SAP/cloud-s4-sdk-book).
If you are familiar with [git / GitHub](https://help.github.com/articles/set-up-git/), you can simply clone the repository to your local machine.
Otherwise, you can also use the links provided below to download a certain state of the source code as a ZIP archive.

The git repository is organized in branches, where each branch corresponds to the state of the application in a certain chapter of the book.

## How to Navigate the Branches
The following table gives an overview of the branches in the repository.
It references the related chapters and briefly describes the state reflected in the branch description.
To inspect the source code of a particular state, either checkout the corresponding branch (if you have cloned the git repository), view the branch on GitHub in your browser via the link in the first column, or download the project as an archive using the links provided in the last column below.

### Overview of Branches

| Branch | Chapters | Description | Download as Archive |
|---|---|---|---|
| [04_1_archetype](https://github.com/SAP/cloud-s4-sdk-book/tree/04_1_archetype) | 4.1 | Plain project generated from archetype | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/04_1_archetype.zip) |
| [04_2_simple](https://github.com/SAP/cloud-s4-sdk-book/tree/04_2_simple) | 4.2–4.5 | Servlet-based implementation, including frontend | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/04_2_simple.zip) |
| [04_3_resilient](https://github.com/SAP/cloud-s4-sdk-book/tree/04_3_resilient) | 4.6 | ... with resilience via Hystrix commands | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/04_3_resilient.zip) |
| [04_4_input_validation](https://github.com/SAP/cloud-s4-sdk-book/tree/04_4_input_validation)`*` | N/A | ... with input validation and error handling (not described in the book, included for reference as a best practice) | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/04_4_input_validation.zip) |
| [05_06_1_security](https://github.com/SAP/cloud-s4-sdk-book/tree/05_06_1_security) | 5–6 | ... including setup of AppRouter, other security-relevant changes, and use of destination service. *Note that due to the security setup you cannot run this state in a local deployment.* | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/05_06_1_security.zip) |
| [07_1_multitenancy](https://github.com/SAP/cloud-s4-sdk-book/tree/07_1_multitenancy) | 7 | ... including setup for multi-tenant persistence | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/07_1_multitenancy.zip) |
| [08_1_REST](https://github.com/SAP/cloud-s4-sdk-book/tree/08_1_REST) | 8.2 | REST API generated with Swagger | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/08_1_REST.zip) |
| [15_1_javaee](https://github.com/SAP/cloud-s4-sdk-book/tree/15_1_javaee) | 15.1 | JavaEE-based implementation, including security and multi-tenant persistence | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/15_1_javaee.zip) |
| [15_2_spring_boot](https://github.com/SAP/cloud-s4-sdk-book/tree/15_2_spring_boot) | 15.2 | Spring Boot-based implementation, including security and multi-tenant persistence | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/15_2_spring_boot.zip) |
| [20_1_integrate_in_app](https://github.com/SAP/cloud-s4-sdk-book/tree/20_1_integrate_in_app) | 20.1 | Integrating in-app extensions into the Servlet-based implementation | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/20_1_integrate_in_app.zip) |

<!--
IMPORTANT: keep the empty line before and after the table!
| #branch:# |  |  | #zip:# |
-->
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
* Chapter 17: stay tuned!

## Further Notes
Below you find specific setup instructions for certain branches.