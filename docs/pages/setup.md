---
layout: default
title: Setup Instructions
---
# Setup Instructions
This page explains how to setup and prepare your computer so that you can follow the examples described in the book.

## Requirements
You will need the following on your local machine to work with the source code and run the application:
* Java Development Kit (JDK), **version 8 (1.8.0)**
    * Please note that **only JDK version 8** works with the example source code as of now. Don't use any earlier or later version.
* Apache Maven, version 3.5.0, or above

### Optional Requirements
* Git, version 2.15.0, or above for cloning the repository from GitHub.com and pushing changes to your own fork (Download from the [project homepage](https://git-scm.com/downloads))

## Installation
Please follow the instructions outlined in [this blog post](https://blogs.sap.com/2017/05/15/step-1-with-sap-s4hana-cloud-sdk-set-up/) to install the JDK and Maven using a package manager on Windows or macOS.

## Troubleshooting
Verify your installation by running `javac -version` and `mvn -version` on a command line shell.
The output should look similar to the following:
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

