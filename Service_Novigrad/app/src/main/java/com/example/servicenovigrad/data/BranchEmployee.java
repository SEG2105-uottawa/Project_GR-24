package com.example.servicenovigrad.data;

public class BranchEmployee {
    private String branchName, firstName, lastName, userName;
    final static String type = "BRANCH_EMPLOYEE";

    public BranchEmployee() {
        branchName = null;
        firstName = null;
        lastName = null;
        userName = null;
    }

    public BranchEmployee(String branchName, String firstName, String lastName, String userName) {
        this.branchName = branchName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getBranchName() {
        return branchName;
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

