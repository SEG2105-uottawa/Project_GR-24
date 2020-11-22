package com.example.servicenovigrad.ui.branchEmployee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ui.homepages.UserPage;

public class ServiceRequests extends UserPage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_requests);
    }
}