/**
 * Account class is the superclass that different user accounts inherits from
 * It has role, username, password labels
 */

package com.example.servicenovigrad.data;

import java.io.Serializable;
import java.util.HashSet;

//Serializable interface allows objects to be passed between activities
public class Account implements Serializable {

    private String firstName, lastName, userName;
    private Roles role;
    private HashSet<Services> services;

    public Account(String firstName, String lastName, String userName, Roles role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.role = role;
        services = null;
    }

    public HashSet<Services> getServices() {
        return services;
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

    public Roles getRole() {
        return role;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
