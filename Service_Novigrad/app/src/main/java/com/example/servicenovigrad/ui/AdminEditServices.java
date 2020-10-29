package com.example.servicenovigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.BranchEmployee;

public class AdminEditServices extends AppCompatActivity {

    BranchEmployee branchEmployee;
    String employeeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_services);
    }
}