/**
 * BranchEmployee class is for employee users and a subclass of Account class
 * It stores employee credential information. It can create branch account and use
 * methods of the Branch class to edit it.
 **/

package com.example.servicenovigrad.users;

public class BranchEmployee extends Account {

    private final static Roles role = Roles.BRANCH_EMPLOYEE;

    private String branchName, address, phoneNumber;

    /**
     * Constructor
     *
     * @param firstName
     * @param lastName
     * @param userName
     * @param branchName
     * @param address
     * @param phoneNumber
     */

    public BranchEmployee(String firstName, String lastName, String userName, String branchName, String address, String phoneNumber) {
        super(firstName, lastName, userName, role);
        this.branchName = branchName;
        this.address = address;
        this.phoneNumber = phoneNumber;
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
        this(firstName, lastName, userName, branchName, null, null);
    }


    public String getBranchName() {
        return branchName;
    }

    public String getAddress() { return address; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString(){
        return ("Branch Name: " + branchName + "\nEmployee Name: " + getFullName());
    }
}

