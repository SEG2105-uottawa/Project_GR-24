/**
 * Account class is the superclass that different user accounts inherits from
 * It has role, username, password labels
 */

package com.example.servicenovigrad.data;

public class Account {

    /*Variable initialization*/
    private String role;

    private String username;

    private String password;

    /**
     * Default constructor
     */
    public Account() {
    }

    /*Role getter method*/
    public String getRole() {
        return role;
    }

    /*Username getter method*/
    public String getUsername() {
        return username;
    }

    /*Username setter method*/
    public void setUsername(String username) {
        this.username = username;
    }

    /*Password getter method*/
    public String getPassword() {
        return password;
    }

    /*Password setter method*/
    public void setPassword(String password) {
        this.password = password;
    }
}
