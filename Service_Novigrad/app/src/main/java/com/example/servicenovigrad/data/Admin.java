/**
 * Admin class is for admin users and a subclass of Account class
 * It has hard coded credential information and able to edit Service and
 * manage accounts of customers and branches
 */

package com.example.servicenovigrad.data;

public class Admin extends Account {

    /*Variable initialization*/
    private static final String role = "ADMIN";

    private static final String username = "ADMIN";

    private static final String password = "ADMIN";

    public Admin() {

    }
}
