/**
 * BranchEmployee class is for employee users and a subclass of Account class
 * It stores employee credential information. It can create branch account and use
 * methods of the Branch class to edit it.
 */

package com.example.servicenovigrad.data;

import java.util.HashSet;

public class BranchEmployee extends Account {

    private final static Roles role = Roles.BRANCH_EMPLOYEE;

    private String branchName;

    private HashSet<Service> servicesOffered;

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
        servicesOffered = new HashSet<>();
    }

    public void addService(Service service){
        servicesOffered.add(service);
    }

    public void removeService(Service service){
        servicesOffered.remove(service);
    }

    public HashSet<Service> getServicesOffered() {
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

