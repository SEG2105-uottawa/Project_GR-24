package com.example.servicenovigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.Service;

public class AdminEditService extends AppCompatActivity {

    Service service;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_service);

        service = (Service) getIntent().getSerializableExtra("service");

        //Get views
        header = findViewById(R.id.admin_edit_service_header);

        //Set text
        header.setText(service.getName());

        //Listeners

        //Populate Lists
    }
}