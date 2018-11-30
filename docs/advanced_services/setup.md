# Setup for the Integration of SAP Leonardo Machine Learning Services
This setup describes additional configuration in SAP Cloud Platform to setup the translation scenario shown in the Open SAP Video. It includes the following configurations:
* Configuration of mpApi destination: For the integration with the language detection service running in SAP API Business Hub, we need to configure this destination and set a corresponding API key for the SAP API Business Hub
* Create machine learning service instance: for the integration with the translation service available in trial accounts, we create an instance of the service ml-foundation-trial-beta. 

## Configuration of mpApi Destination
This step is required to be able to connect to the language detection service via the SAP API Business Hub. In productive scenarios, you will use connectivity to SAP Leonardo services using service bindings, as we do it for the translation service. The language detection service is used via the SAP API Business Hub, as it was in development at the time of preparing of this course. 
To configure mlApi destination, on the level of your subaccount go to Connectivity -> Destinations and set the following parameters: <br>
Name: mlApi <br>
Type: HTTP <br>
URL: https://sandbox.api.sap.com/ml <br>
Proxy Type: Internet <br>
Authentication: BasicAuthentication <br>
User: Your SAP Cloud Platform trial email address <br>
Password: Your password for SAP Cloud Platform trial <br>

Then, choose "New Property" and add the following property: <br>
mlApiKey: your API key <br>

This property is required for the OAuth 2.0 authentication against the Business Hub APIs.

In case you do not know how to find your API key, here is a quick instruction: 
* Go to https://api.sap.com/
* Logon with your user and password that you have used in the destination configuration above
* Choose any API, for instance you can search for "language detection". After you select this service, you will have an option to "Show API Key"
![Show API Key]()
* Copy the shown value as a value of the property mlApiKey in the endpoint configuration above.
![API Key Value]()

## Create Machine Learning Service Instance
The translation service will be integrated using the service binding in SAP Cloud Platform and using the SDK class ScpCfService.
The service binding will be done using the manifest.yml of the application. For binding, add the new service with the name "my-ml" in the "services" section of the manifest.yml file.

Now, let us create this service instance in SAP Cloud Platform. On the level of your development space, go to Services -> Service Marketplace and choose ml-foundation-trial-beta. Then go to "Instances" and select "New Instance". Stay with all the defaults and in the final screen set the name to my-ml (the one that we have specified in manifest.yml file).

After the described steps are executed, you can go ahead and develop the integration with the translation service as it was shown in the video. After that deploy your application and check if the translation working. Feel free to reach out to us in case if issues!
