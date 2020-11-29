package com.example.servicenovigrad.ui.customer;

import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class SearchPage extends UserPage {
    protected static HashMap<BranchEmployee, DatabaseReference> searchResults;
    protected static BranchEmployee selection;
}
