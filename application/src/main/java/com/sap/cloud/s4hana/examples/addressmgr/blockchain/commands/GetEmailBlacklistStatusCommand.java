package com.sap.cloud.s4hana.examples.addressmgr.blockchain.commands;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.sap.cloud.s4hana.examples.addressmgr.blockchain.BlacklistService;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.frameworks.hystrix.Command;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.Map;

public class GetEmailBlacklistStatusCommand extends Command<Map<String, Integer>> {
    private static final Logger logger = CloudLoggerFactory.getLogger(GetEmailBlacklistStatusCommand.class);

    private final BlacklistService blacklistService;

    private final String email;

    public GetEmailBlacklistStatusCommand(BlacklistService blacklistService, final String email) {
        super(HystrixCommandGroupKey.Factory.asKey("LeonardoHyperledgerFabric-blackliststatus"), 5000);

        this.blacklistService = blacklistService;
        this.email = email;
    }

    @Override
    protected Map<String, Integer> run()
            throws Exception {
        return Collections.singletonMap(getEmail(), blacklistService.getEmailBlacklistCount(getEmail()));
    }

    public String getEmail() {
        return email;
    }

    @Override
    protected Map<String, Integer> getFallback() {
        logger.error("Fallback called because of exception:", getExecutionException());
        return Collections.emptyMap();
    }

    @Override
    public String toString() {
        return "GetEmailBlacklistStatusCommand{" +
                "email='" + email + '\'' +
                '}';
    }
}
