package com.example.servicenovigrad.services;

import com.example.servicenovigrad.users.Customer;

import java.util.HashMap;

public class ServiceRequest extends Service{
    //The customer that made the request
    private Customer customer;
    private long requestID;

    public ServiceRequest(Service service, Customer customer, long requestID){
        super(service);
        this.customer = customer;
        this.requestID = requestID;
    }

    public void fillFormField(String field, String value){
        if (formFields.containsKey(field)){
            formFields.put(field,value);
        }
        else throw new IllegalArgumentException("Form field does not exist in service: " + getName());
    }
}
