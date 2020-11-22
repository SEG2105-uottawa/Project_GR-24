package com.example.servicenovigrad.ui.branchEmployee;

import android.os.Bundle;
import android.widget.TextView;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ui.homepages.UserPage;

public class ServicesOffered extends UserPage {
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_offered);
        header = findViewById(R.id.services_offered_header);
        header.setText(branchObject().getBranchName());
    }
}
