package com.sap.cloud.s4hana.examples.addressmgr.blockchain;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sap.cloud.s4hana.examples.addressmgr.util.CloudPlatformService;
import com.sap.cloud.sdk.cloudplatform.exception.ShouldNotHappenException;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.services.scp.blockchain.hyperledgerfabric.FabricInvocationType;
import com.sap.cloud.sdk.services.scp.blockchain.hyperledgerfabric.FabricService;
import org.slf4j.Logger;

import java.util.Optional;

public class BlacklistService {
    private static final Logger logger = CloudLoggerFactory.getLogger(BlacklistService.class);
    private static final String BLACKLIST_CHAINCODE_ID = "BLACKLIST_CHAINCODE_ID";

    private FabricService blockchainService;

    private final String blacklistChaincodeId;

    public BlacklistService(CloudPlatformService cloudPlatformService, FabricService blockchainService) {
        this.blockchainService = blockchainService;

        blacklistChaincodeId = getBlacklistChaincodeIdOrThrow(cloudPlatformService);
    }

    static String getBlacklistChaincodeIdOrThrow(CloudPlatformService cloudPlatformService) {
        final Optional<String> chaincodeIdEnv = cloudPlatformService.getCloudFoundryOrThrow()
                .getEnvironmentVariable(BLACKLIST_CHAINCODE_ID);
        if (!chaincodeIdEnv.isPresent()) {
            final String message = "Environment variable " + BLACKLIST_CHAINCODE_ID + " missing. " +
                    "Read up in the book and in the documentation on GitHub to learn how to deploy " +
                    "the blacklist example chaincode to a hyperledger fabric channel instance " +
                    "and learn its chaincode id";
            logger.error(message);
            throw new IllegalStateException(message);
        }
        return chaincodeIdEnv.get();
    }

    public int getEmailBlacklistCount(String emailAddress) throws Exception {
        final String response = blockchainService.invokeOrQuery(FabricInvocationType.QUERY, blacklistChaincodeId,
                "count", "email", emailAddress);

        JsonObject resultObject = checkErrorfreeAndJsonContent(response);
        if (!(resultObject.has("count") && resultObject.get("count").isJsonPrimitive())) {
            throw new ShouldNotHappenException("Chaincode result of count operation does not contain expected " +
                    "count integer property");
        }

        return resultObject.get("count").getAsInt();
    }

    private JsonObject checkErrorfreeAndJsonContent(String response) {
        final JsonElement resultElement;
        try {
            resultElement = new JsonParser().parse(response);
        } catch (Exception e) {
            throw new ShouldNotHappenException("Chaincode execution resulted in non-json result or unparseable " +
                    "result: " + e.getMessage(), e);
        }

        if (!resultElement.isJsonObject()) {
            logger.error("Unexpectedly root of response json content is not a json object: {}", resultElement);
            throw new ShouldNotHappenException("Unexpected content in response of chaincode call. Not json or no " +
                    "content");
        }
        JsonObject result = resultElement.getAsJsonObject();
        if (result.has("error")) {
            throw new ShouldNotHappenException("Error raised by chaincode:" + result.get("error"));
        }
        return result;
    }

    public void addEmailToBlacklist(String emailAddress) throws Exception {
        blockchainService.invokeOrQuery(FabricInvocationType.INVOKE, blacklistChaincodeId, "add", "email",
                emailAddress);
    }
}
