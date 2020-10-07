/**
 * Customer class is for customer users and a subclass of Account class
 * It has customer credential informations and methods for serching branches
 * making ServiceRequest and rating a Brach
 */

package com.example.servicenovigrad.data;

public class Customer extends Account {

    /*Variable initialization*/
    private final static String role = "CUSTOMER";

    private String username;

    private String password;

    private String fName;

    private String lName;

    private String email;

    /**
     * Default constructor
     */
    public Customer() {
    }

    /**
     * Constructor
     *
     * @param aFirstName
     * @param aLastName
     * @param aUsername
     * @param aEmail
     */
    public Customer(String aFirstName, String aLastName, String aUsername, String aEmail) {
        fName = aFirstName;
        lName = aLastName;
        username = aUsername;
        email = aEmail;
    }

    /* First name getter method*/
    public String getFName() {
        return fName;
    }

    /* Last name getter method*/
    public String getLName() {
        return lName;
    }

    /* Full name getter method*/
    public String getName() {
        return fName+" "+lName;
    }

    /*Email getter method*/
    public String getEmail() {
        return email;
    }

    /*Email setter method*/
    public void setEmail(String email) {
        this.email = email;
    }

}
