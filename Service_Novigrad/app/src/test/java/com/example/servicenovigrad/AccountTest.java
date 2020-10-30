package com.example.servicenovigrad;

import com.example.servicenovigrad.users.Admin;
import com.example.servicenovigrad.users.BranchEmployee;
import com.example.servicenovigrad.users.Customer;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void testCustomerCreation() {
        Customer customer = new Customer("Chris", "Hamilton", "chami033");
        assertEquals(customer.getFirstName(), "Chris");
        assertEquals(customer.getLastName(), "Hamilton");
        assertEquals(customer.getUserName(), "chami033");
        assertEquals(customer.getRole(), "CUSTOMER");
    }

    @Test
    public void testBranchCreation() {
        BranchEmployee employee = new BranchEmployee("Akarsh", "Gharge", "aghar028", "Bank of Akarsh");
        assertEquals(employee.getFirstName(), "Akarsh");
        assertEquals(employee.getLastName(), "Gharge");
        assertEquals(employee.getUserName(), "aghar028");
        assertEquals(employee.getRole(), "BRANCH_EMPLOYEE");
        assertEquals(employee.getBranchName(), "Bank of Akarsh");
    }

    @Test public void testAdminCreation() {
        Admin admin = new Admin("Mustafa", "Sarikaya", "msari049");
        assertEquals(admin.getFirstName(), "Mustafa");
        assertEquals(admin.getLastName(), "Sarikaya");
        assertEquals(admin.getUserName(), "msari049");
        assertEquals(admin.getRole(), "ADMIN");
    }




}

