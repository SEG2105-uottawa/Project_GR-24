/**
 * BranchEmployee class is for employee users and a subclass of Account class
 * It stores employee credential information. It can create branch account and use
 * methods of the Branch class to edit it.
 **/

package com.example.servicenovigrad.users;


import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.data.ServiceRequest;

import com.example.servicenovigrad.data.WorkingHours;

import java.util.ArrayList;
import java.util.HashMap;

public class BranchEmployee extends Account {

    private final static Roles role = Roles.BRANCH_EMPLOYEE;

    private String branchName, phoneNumber, streetNumber, streetName, postalCode;
    private HashMap<String, Service> servicesOffered;
    private ArrayList<WorkingHours> hours = new ArrayList<WorkingHours>();

    private HashMap<String, ServiceRequest> serviceRequests;

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

        for (int i=0; i<7; i++) {
            hours.add(i, new WorkingHours(i));
        }
        serviceRequests = new HashMap<>();
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

    public String getAddress() {
        if(streetNumber == null || streetName == null || postalCode == null) return "NO ADDRESS";
        else return streetNumber + " " + streetName + " " + postalCode;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return (phoneNumber == null ? "NO NUMBER" : phoneNumber);
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public HashMap<String, Service> getServicesOffered(){
        return servicesOffered;
    }

    public HashMap<String, ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<WorkingHours> getHours() {
        return hours;
    }

    public void setHours(ArrayList<WorkingHours> hours) {
        this.hours = hours;
    }

    @Override
    public String toString(){
        return ("Branch Name: " + branchName + "\nAddress: " + getAddress());
    }
}

