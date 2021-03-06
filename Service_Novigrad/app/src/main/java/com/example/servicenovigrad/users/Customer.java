/**
 * Customer class is for customer users and a subclass of Account class
 * It has customer credential informations and methods for serching branches
 * making ServiceRequest and rating a Brach
 */

package com.example.servicenovigrad.users;

public class Customer extends Account {

    private final static Roles role = Roles.CUSTOMER;

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

    public String toString(){
        return (getFullName() +"\n" + getUserName());
    }
    
}
