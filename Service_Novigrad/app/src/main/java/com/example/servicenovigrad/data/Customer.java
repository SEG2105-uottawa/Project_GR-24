package com.example.servicenovigrad.data;

public class Customer {
    private String fName, lName, userName;
    final static String type = "CUSTOMER";

    public Customer() {
        fName = null;
        lName = null;
        userName = null;
    }

    public Customer(String first, String last, String uName) {
        fName = first;
        lName = last;
        userName = uName;
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }

    public String getName() {
        return fName+" "+lName;
    }

    public String getUserName() {
        return userName;
    }

    public String getType() {
        return type;
    }

}
