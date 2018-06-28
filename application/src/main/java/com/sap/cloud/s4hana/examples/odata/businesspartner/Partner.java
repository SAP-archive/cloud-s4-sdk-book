package com.sap.cloud.s4hana.examples.odata.businesspartner;

public class Partner {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    public Partner() {

    }

    public Partner(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setProductID(String partnerID) {
        this.id = partnerID;
    }

    public String getName() {
        return firstName;
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
