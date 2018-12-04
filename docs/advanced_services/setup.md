# Setup for the Integration of SAP Leonardo Machine Learning Services
This setup describes additional configuration in SAP Cloud Platform to setup the translation scenario shown in the Open SAP Video, showing how to create machine learning service instance for the integration with the translation service available in trial accounts. 

## Create Machine Learning Service Instance
The translation service will be integrated using the service binding in SAP Cloud Platform and using the SDK class ScpCfService.
The service binding will be done using the manifest.yml of the application. For binding, add the new service with the name "my-ml" in the "services" section of the manifest.yml file.

Now, let us create this service instance in SAP Cloud Platform. On the level of your development space, go to Services -> Service Marketplace and choose ml-foundation-trial-beta. Then go to "Instances" and select "New Instance". Stay with all the defaults and in the final screen set the name to my-ml (the one that we have specified in manifest.yml file).

After the described steps are executed, you can develop the integration with the translation service as it was shown in the video. After that, deploy your application and check if the translation working. 
