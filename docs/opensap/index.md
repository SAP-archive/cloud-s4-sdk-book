---
layout: default
title: 'openSAP Course: Create and Deliver Cloud-Native SAP S/4HANA Extensions'
---

# openSAP Course: Create and Deliver Cloud-Native SAP S/4HANA Extensions

Welcome to our [course](https://open.sap.com/courses/s4h13) on creating and delivering game changing cloud-native SAP S/4HANA extensions.
This page explains how to use our example code repository and provides setup instructions.

## Source Code Repository

The example source code for this course is hosted in a [GitHub repository](https://github.com/SAP/cloud-s4-sdk-book).

### Requirements

During the course, we use IntelliJ IDEA for editing the code of our Java projects. You can get your free copy of it [on this website](https://www.jetbrains.com/idea/download/).

You will need the following on your local machine to work with the source code and run the application:

* Java Development Kit (JDK), **version 8 (1.8.0)**
    * Please note that **only JDK version 8** works with the example source code as of now. Don't use any earlier or later version.
* Apache Maven, version 3.5.0, or above
* Git, version 2.15.0, or above for cloning the repository (Download from the [project homepage](https://git-scm.com/downloads))

Please follow the instructions outlined in [this blog post](https://blogs.sap.com/2017/05/15/step-1-with-sap-s4hana-cloud-sdk-set-up/) to install the JDK and Maven using a package manager on Windows or macOS.

Verify the tools are installed correctly by running the commands `java -version`, `mvn -version` and `git --version` in a terminal or command prompt.
Check that the environment variable `JAVA_HOME` points to the path of your JDK installation, e.g., `C:\Program Files\path\to\java\jdk1.8.0_72`.

### Get the example code

To get the example code on your local machine, clone it from GitHub via `git clone https://github.com/SAP/cloud-s4-sdk-book`.
Alternatively, you can download the individual branches as ZIP files in the table below.

Please note that this repository contains different branches, some of which are related to the openSAP course as explained below, while others are relevant for the [book](https://sap.github.io/cloud-s4-sdk-book/) only.
We recommend to use this repository as a reference throughout the course, and work on an `address-manager` repository in your own user account on GitHub.
Creating your own repository is described in unit 5 of week 1 of the course.

### Repository layout

The example repository has branches for individual units in this course.
All branches prefixed with `course/` are relevant in the context of this course.
The branches are named according to the schema `course/{week}_{unit}_{label}`.
To inspect the source code of a particular state, either checkout the corresponding branch (if you have cloned the git repository, by running `git checkout course/{week}_{unit}_{label}`), view the branch on GitHub in your browser via the link in the first column, or download the project as an archive using the links provided in the last column below.

| Branch | Week/ Unit | Description | Download as Archive  |
|---|---|---|---|
| [course/1_5_hello_world](https://github.com/SAP/cloud-s4-sdk-book/tree/course/1_5_hello_world) | 1/5 | Plain project generated from archetype | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/1_5_hello_world.zip) |
| [course/2_1_copyover](https://github.com/SAP/cloud-s4-sdk-book/tree/course/2_1_copyover) | 2/1 | Delta containing front-end and command stubs to apply to your project | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/2_1_copyover.zip) |
| [course/2_1_start](https://github.com/SAP/cloud-s4-sdk-book/tree/course/2_1_start) | 2/1 | As an alternative to the copyover, use this branch for a fresh start | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/2_1_start.zip) |
| [course/2_2_start](https://github.com/SAP/cloud-s4-sdk-book/tree/course/2_2_start) | 2/2 | Starting point for the unit | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/2_2_start.zip) |
| [course/2_2_end](https://github.com/SAP/cloud-s4-sdk-book/tree/course/2_2_end) | 2/2 | Final state after the unit for reference | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/2_2_end.zip) |
| [course/2_3_security](https://github.com/SAP/cloud-s4-sdk-book/tree/course/2_3_security) | 2/3 | Adding security to our application | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/2_3_security.zip) |
| [course/2_4_multitenancy](https://github.com/SAP/cloud-s4-sdk-book/tree/course/2_4_multitenancy) | 2/4 | Making our application multi tenant-aware | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/2_4_multitenancy.zip) |
| [course/2_5_testing](https://github.com/SAP/cloud-s4-sdk-book/tree/course/2_5_testing) | 2/5 | Starting point for the unit | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/2_5_testing.zip) |
| [course/2_5_testing_practice](https://github.com/SAP/cloud-s4-sdk-book/tree/course/2_5_testing_practice) | 2/5 | Starting point for self-learning by implementing tests | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/2_5_testing_practice.zip) |
| [course/4_2_in_app_step1_start](https://github.com/SAP/cloud-s4-sdk-book/tree/course/4_2_in_app_step1_start) | 4/2 | Start for demo: Retrieving and storing information on last address check | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/4_2_in_app_step1_start.zip) |
| [course/4_2_in_app_step1_solution](https://github.com/SAP/cloud-s4-sdk-book/tree/course/4_2_in_app_step1_solution) | 4/2 | Solution for demo: Retrieving and storing information on last address check | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/4_2_in_app_step1_solution.zip) |
| [course/4_2_in_app_step2_start](https://github.com/SAP/cloud-s4-sdk-book/tree/course/4_2_in_app_step2_start) | 4/2 | Start for demo: Retrieving social media accounts | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/4_2_in_app_step2_start.zip) |
| [course/4_2_in_app_step2_solution](https://github.com/SAP/cloud-s4-sdk-book/tree/course/4_2_in_app_step2_solution) | 4/2 | Solution for demo: Retrieving social media accounts | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/4_2_in_app_step2_solution.zip) |
| [course/4_3_advanced_services_initial](https://github.com/SAP/cloud-s4-sdk-book/tree/course/4_3_advanced_services_initial) | 4/3 | Starting point for the unit | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/4_3_advanced_services_initial.zip) |
| [course/4_3_advanced_services_solution](https://github.com/SAP/cloud-s4-sdk-book/tree/course/4_3_advanced_services_solution) | 4/3 | Solution | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/4_3_advanced_services_solution.zip) |
| [course/4_4_eventing](https://github.com/SAP/cloud-s4-sdk-book/tree/course/4_4_eventing) | 4/4 | Example for consuming messages | [ZIP](https://github.com/SAP/cloud-s4-sdk-book/archive/course/4_4_eventing.zip) |

## Errata

### Week 3

#### Unit 2

* In the demo for creating a Jenkins job, we used the repository `https://github.com/fwilhe/address-manager` as an example.
    We recommend to use your own `address-manager` repository (for example `https://github.com/{your-GitHub-username}/address-manager`) as the branch source in Jenkins.

### Unit 3

* In the description of automatic versioning, the term "released" is used where "deployed" should have been used.
    Using feature toggles, it is possible to decouple _deployment_ and _release_ of features.
    This process allows us to activate features which are not production ready yet for individual users and _release_ them to all users later.
