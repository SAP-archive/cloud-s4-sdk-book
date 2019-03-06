---
layout: default
title: Blacklist chaincode in Go
---
# Blacklist chaincode in Go
You can find the example chaincode for the Blacklist blockchain described in Chapter 17.3 in this repository.
Proceed as follows:
* Checkout the branch `17_2_blockchain` if you have cloned the repository to your local machine (otherwise [download it](https://github.com/SAP/cloud-s4-sdk-book/archive/17_2_blockchain.zip)). You will see a folder `blacklist_chaincode` that contains the chaincode in Go.
* Create an archive that contains the content of the folder `blacklist_chaincode` using the following command.
```
jar cvf blacklist_chaincode.zip -C blacklist_chaincode .
```
* Upload the created zip file as chaincode to the channel as described in Chapter 17.3 of the book, with the following additional step: You need to supply a name for the chaincode as first argument. In the dialog to deploy the chaincode, click on *+ Add Argument* and enter `blacklist-chaincode` or similar.

> _Note_: There is no need to compile the Go source code. The SAP Leonardo Blockchain service will compile the go code.

