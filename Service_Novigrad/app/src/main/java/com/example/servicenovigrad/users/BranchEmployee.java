/**
 * BranchEmployee class is for employee users and a subclass of Account class
 * It stores employee credential information. It can create branch account and use
 * methods of the Branch class to edit it.
 **/

package com.example.servicenovigrad.users;

import com.example.servicenovigrad.services.Service;
import com.example.servicenovigrad.services.ServiceRequest;

import java.util.HashMap;

public class BranchEmployee extends Account {

    private final static Roles role = Roles.BRANCH_EMPLOYEE;

    private String branchName, address, phoneNumber;
    private HashMap<String, Service> servicesOffered;
    private HashMap<String, ServiceRequest> serviceRequests;

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
        servicesOffered = new HashMap<>();
        serviceRequests = new HashMap<>();
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

    public void addService(Service service){
        servicesOffered.put(service.getName(), service);
    }

    public void deleteService(Service service){
        servicesOffered.remove(service.getName());
    }

    public void addServiceRequest(ServiceRequest request){
        if(!serviceRequests.containsKey(request.getRequestID())){
            serviceRequests.put(request.getRequestID(), request);
        }
        else throw new IllegalArgumentException("The request ID " +
                request.getRequestID() + " is a duplicate..." +
                "\nExisting request: " + serviceRequests.get(request.getRequestID()) +
                "\nNew Request: " + request);
    }

    public String getBranchName() {
        return branchName;
    }

    public String getAddress() { return address; }

    public String getPhoneNumber() { return phoneNumber; }

    public HashMap<String, Service> getServicesOffered(){
        return servicesOffered;
    }

    public HashMap<String, ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }

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

