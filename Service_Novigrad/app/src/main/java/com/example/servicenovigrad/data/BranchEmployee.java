package com.example.servicenovigrad.data;

public class BranchEmployee {
    private String branchName, fName, lName, userName;
    final static String type = "BRANCH_EMPLOYEE";

    public BranchEmployee() {
        branchName = null;
        fName = null;
        lName = null;
        userName = null;
    }

    public BranchEmployee(String branchName, String fName, String lName, String userName) {
        this.branchName = branchName;
        this.fName = fName;
        this.lName = lName;
        this.userName = userName;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }

    public String getUserName() {
        return userName;
    }

    public String getType() {
        return type;
    }
}

