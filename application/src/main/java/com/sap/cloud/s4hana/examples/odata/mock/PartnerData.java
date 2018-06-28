package com.sap.cloud.s4hana.examples.odata.mock;

import com.sap.cloud.s4hana.examples.odata.businesspartner.Partner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartnerData {
    private final Map<String, Partner> partnerList = new HashMap<>();

    public PartnerData() {
        partnerList.put("0100000000",
                new Partner("0100000000", "SAP", "SE", "do.not.reply@sap.com"));
        partnerList.put("0100000001",
                new Partner("0100000001", "Becker Berlin", "GmbH", "dagmar.schulze@beckerberlin.de"));
        partnerList.put("0100000002",
                new Partner("0100000002", "DelBont Industries", "Ltd.", "maria.brown@delbont.com"));
        partnerList.put("0100000003",
                new Partner("0100000003", "Talpa", "GmbH", "saskia.sommer@talpa-hannover.de"));
        partnerList.put("0100000004",
                new Partner("0100000004", "Panorama Studios", "Inc.", "bob.buyer@panorama-studios.biz"));
    }

    public List<Partner> getAllPartners() {

        return new ArrayList<>(partnerList.values());
    }

    public Partner createNewPartner(Partner partnerData) throws Exception {
        if (partnerList.get(partnerData.getId()) != null) {
            throw new Exception("Partner already exists");
        }
        return partnerList.put(partnerData.getId(), partnerData);

    }

    public Partner updatePartner(String partnerID, Partner partner) throws Exception {
        if (partnerList.get(partnerID) == null) {
            throw new Exception("Partner does not exist");
        }
        return partnerList.put(partnerID, partner);
    }

}
