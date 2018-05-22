package com.sap.cloud.s4hana.examples.addressmgr;

import com.sap.cloud.s4hana.examples.addressmgr.models.Address;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/addresses-local")
public class LocalAddressRestService {
    @EJB
    private LocalAddressBean localAddressBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Address> doGet() {
        return localAddressBean.getAllAddresses();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Address> doStore(final Address address) {
        localAddressBean.storeNewAddress(address);
        return localAddressBean.getAllAddresses();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Address> doUpdate(final Address address) {
        localAddressBean.updateExistingAddress(address);
        return localAddressBean.getAllAddresses();
    }

}
