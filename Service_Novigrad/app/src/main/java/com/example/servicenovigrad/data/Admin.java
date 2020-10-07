/**
 * Admin class is for admin users and a subclass of Account class
 * It has hard coded credential information and able to edit Service and
 * manage accounts of customers and branches
 */

package com.example.servicenovigrad.data;

public class Admin extends Account {

    private final static String role = "ADMIN";

    public Admin() {
        super();
    }

    public Admin(String firstName, String lastName, String userName){
        super(firstName, lastName, userName);
    }

    public String getType() {
        return role;
    }

    public String getRole() {
        return role;
    }
}
