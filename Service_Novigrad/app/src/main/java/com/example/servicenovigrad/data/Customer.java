package com.example.servicenovigrad.data;

public class Customer {
    private String firstName, lastName, userName;
    final static String type = "CUSTOMER";

    public Customer() {
        firstName = null;
        lastName = null;
        userName = null;
    }

    public Customer(String first, String last, String uName) {
        firstName = first;
        lastName = last;
        userName = uName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getType() {
        return type;
    }
}
