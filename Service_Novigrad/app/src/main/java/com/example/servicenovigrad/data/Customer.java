package com.example.servicenovigrad.data;

public class Customer {
    private String fName, lName, userName;

    public Customer(String first, String last, String uName) {
        fName = first;
        lName = last;
        userName = uName;
    }

    public String getName() {
        return fName+" "+lName;
    }

    public String getUserName() {
        return userName;
    }

}
