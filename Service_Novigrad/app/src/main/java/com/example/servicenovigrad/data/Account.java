/**
 * Account class is the superclass that different user accounts inherits from
 * It has role, username, password labels
 */

package com.example.servicenovigrad.data;

public class Account {

    /*Variable initialization*/
    private String firstName, lastName, userName;

    /**
     * Default constructor
     */
    public Account() {
        firstName = null;
        lastName = null;
        userName = null;
    }

    public Account(String firstName, String lastName, String userName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }
}
