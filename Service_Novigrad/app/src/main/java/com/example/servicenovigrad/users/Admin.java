/**
 * Admin class is for admin users and a subclass of Account class
 * It has hard coded credential information and able to edit Service and
 * manage accounts of customers and branches
 */

package com.example.servicenovigrad.users;

public class Admin extends Account {

    private final static Roles role = Roles.ADMIN;

    public Admin(String firstName, String lastName, String userName){
        super(firstName, lastName, userName, role);
    }
    
}
