package com.example.servicenovigrad.ui.homepages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.servicenovigrad.users.Account;
import com.example.servicenovigrad.users.BranchEmployee;
import com.example.servicenovigrad.users.Customer;

public abstract class UserPage extends AppCompatActivity {
    protected static Account userObject;

    protected BranchEmployee branchObject(){
        return (BranchEmployee) userObject;
    }

    protected Customer customerObject(){
        return (Customer) userObject;
    }
}