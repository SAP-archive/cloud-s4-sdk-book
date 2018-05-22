package com.sap.cloud.s4hana.examples.addressmgr;

import com.netflix.hystrix.HystrixCommand;
import com.sap.cloud.sdk.frameworks.hystrix.HystrixUtil;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartnerAddress;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

public class GetAddressCommand extends HystrixCommand<BusinessPartnerAddress> {

    private String bupaId;
    private String addressId;

    public GetAddressCommand(final String bupaId, final String addressId) {
        super(HystrixUtil
            .getDefaultErpCommandSetter(
                GetAddressCommand.class,
                HystrixUtil.getDefaultErpCommandProperties()
                    .withExecutionTimeoutInMilliseconds(10000)));
        this.bupaId = bupaId;
        this.addressId = addressId;
    }

    @Override
    protected BusinessPartnerAddress run() throws ODataException {
        try {
            return new DefaultBusinessPartnerService()
                    .getBusinessPartnerAddressByKey(bupaId, addressId)
                    .execute();
        } catch (ODataException e) {
            if (e.getCode().equals("404")) {
                return null;
            } else {
                throw e;
            }
        }
    }
}
