/**
 * BranchEmployee class is for employee users and a subclass of Account class
 * It stores employee credential information. It can create branch account and use
 * methods of the Branch class to edit it.
 */

package com.example.servicenovigrad.data;

public class BranchEmployee {

    /*Variable initialization*/
    private final static String role = "EMPLOYEE";

    private String username;

    private String password;

    private String fName;

    private String lName;

    private String email;

    /**
     * Default constructor
     */
    public BranchEmployee() {
    }

    /**
     * Constructor
     *
     * @param aFirstName
     * @param aLastName
     * @param aUsername
     * @param aEmail
     */
    public BranchEmployee(String aFirstName, String aLastName, String aUsername, String aEmail) {
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
