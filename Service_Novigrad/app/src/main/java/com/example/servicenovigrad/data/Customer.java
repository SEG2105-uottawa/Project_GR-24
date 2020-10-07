/**
 * Customer class is for customer users and a subclass of Account class
 * It has customer credential informations and methods for serching branches
 * making ServiceRequest and rating a Brach
 */

package com.example.servicenovigrad.data;

public class Customer extends Account {

    /*Variable initialization*/
    private final static Roles role = Roles.CUSTOMER;

    /**
     * Default constructor
     */
    public Customer() {
        super();
    }

    /**
     * Constructor
     *
     * @param firstName
     * @param lastName
     * @param userName
     */
    public Customer(String firstName, String lastName, String userName) {
        super(firstName, lastName, userName, role);
    }
    
}
