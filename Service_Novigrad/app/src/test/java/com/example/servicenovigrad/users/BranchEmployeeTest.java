package com.example.servicenovigrad.users;

import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.data.ServiceRequest;
import com.example.servicenovigrad.users.BranchEmployee;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.*;

public class BranchEmployeeTest extends TestCase {
    BranchEmployee branch = new BranchEmployee("firstName",
            "lastName", "userName", "branchName",
            "address", "phoneNumber");
    Service service = new Service("serviceName", 22.00);
    String date = new Date().toString();
    String id = UUID.randomUUID().toString();
    ServiceRequest request = new ServiceRequest(service, date, id);

    @Test
    public void testBranchEmployee(){
        assertEquals(branch.getFirstName(), "firstName");
        assertEquals(branch.getLastName(), "lastName");
        assertEquals(branch.getUserName(), "userName");
        assertEquals(branch.getBranchName(), "branchName");
        assertEquals(branch.getAddress(), "address");
        assertEquals(branch.getPhoneNumber(), "phoneNumber");
    }

    @Test
    public void testServiceRequest(){
        assertEquals(request.getServiceName(), "serviceName");
        assertEquals(request.getDateCreated(), date);
        assertEquals(request.getRequestID(), id);
        assertEquals(request.getPrice(), "22.0");
        assertEquals(request.returnPrice(), 22.00);
    }

    @Test
    public void testInsertService(){
        branch.addService(service);
        HashMap <String, Service> servicesMap = new HashMap<>();
        servicesMap.put(service.getName(),service);
        assertEquals(branch.getServicesOffered(), servicesMap);
    }

    @Test
    public void testInsertRequest(){
        branch.addServiceRequest(request);
        HashMap<String, ServiceRequest> requestsMap = new HashMap<>();
        requestsMap.put(id,request);
        assertEquals(branch.getServiceRequests(), requestsMap);
    }
}