/**
 * BranchEmployee class is for employee users and a subclass of Account class
 * It stores employee credential information. It can create branch account and use
 * methods of the Branch class to edit it.
 */

package com.example.servicenovigrad.data;

public class BranchEmployee extends Account {

    /*Variable initialization*/
    private final static String role = "BRANCH_EMPLOYEE";

    private String branchName;

    /**
     * Default constructor
     */
    public BranchEmployee() {
        super();
        branchName = null;
    }

    /**
     * Constructor
     *
     * @param firstName
     * @param lastName
     * @param userName
     * @param branchName
     */
    public BranchEmployee(String firstName, String lastName, String userName, String branchName) {
        super(firstName, lastName, userName);
        this.branchName = branchName;
    }

    public String getRole() {
        return role;
    }

    public String getBranchName() {
        return branchName;
    }
}

