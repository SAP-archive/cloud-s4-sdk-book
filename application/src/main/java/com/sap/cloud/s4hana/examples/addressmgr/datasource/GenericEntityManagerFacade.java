package com.sap.cloud.s4hana.examples.addressmgr.datasource;

public class GenericEntityManagerFacade extends AbstractEntityManagerFacade {
    private static GenericEntityManagerFacade facade;

    private GenericEntityManagerFacade() {}

    public static GenericEntityManagerFacade getInstance() {
        if(facade==null) {
            facade = new GenericEntityManagerFacade();
        }
        return facade;
    }
}
