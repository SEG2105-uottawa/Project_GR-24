/**
 * BranchEmployee class is for employee users and a subclass of Account class
 * It stores employee credential information. It can create branch account and use
 * methods of the Branch class to edit it.
 */

package com.example.servicenovigrad.data;

import java.util.HashMap;

public class BranchEmployee extends Account {

    private final static Roles role = Roles.BRANCH_EMPLOYEE;

    private String branchName;
    //Service Name, and an empty service
    private HashMap<String, Service> servicesOffered;

    /**
     * Constructor
     *
     * @param firstName
     * @param lastName
     * @param userName
     * @param branchName
     */
    public BranchEmployee(String firstName, String lastName, String userName, String branchName) {
        super(firstName, lastName, userName, role);
        this.branchName = branchName;
        servicesOffered = new HashMap<>();
    }

    public void addService(String name, Service service){
        servicesOffered.put(name,service);
    }

    public void removeService(String name){
        servicesOffered.remove(name);
    }

    public HashMap<String, Service> getServicesOffered() {
        return servicesOffered;
    }

    public String getBranchName() {
        return branchName;
    }

    @Override
    public String toString(){
        return ("Branch Name: " + branchName + "\nEmployee Name: " + getFullName());
    }
}

