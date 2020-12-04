package com.example.servicenovigrad.ui.homepages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.servicenovigrad.R;

import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.data.ServiceRequest;
import com.example.servicenovigrad.data.WorkingHours;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.branchEmployee.BranchInfo;
import com.example.servicenovigrad.ui.branchEmployee.BranchReviews;
import com.example.servicenovigrad.ui.branchEmployee.ServiceRequests;
import com.example.servicenovigrad.ui.branchEmployee.ServicesOffered;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BranchEmployeeHomePage extends HomePage {
    Button branchInfo, servicesOffered, serviceRequests, branchReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_branch_employee_home_page);
        super.onCreate(savedInstanceState);
        linkUserObject();

        branchInfo = findViewById(R.id.branch_info);
        servicesOffered = findViewById(R.id.services_offered);
        serviceRequests = findViewById(R.id.service_requests);
        branchReviews = findViewById(R.id.branch_reviews);

        message = findViewById(R.id.message_employee_HP);
        updateHomePage();

        branchInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BranchEmployeeHomePage.this, BranchInfo.class));
            }
        });

        servicesOffered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BranchEmployeeHomePage.this, ServicesOffered.class));
            }
        });

        serviceRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BranchEmployeeHomePage.this, ServiceRequests.class));
            }
        });

        branchReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BranchEmployeeHomePage.this, BranchReviews.class));
            }
        });

    }

    public void updateHomePage() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String branchName = dataSnapshot.child("branchName").getValue().toString();
                String firstName = dataSnapshot.child("firstName").getValue().toString();
                String role = dataSnapshot.child("role").getValue().toString();
                String welcomeMessage = "Welcome, "+ firstName + " of branch: "+ branchName + ", to the Employee homepage!";
                message.setText(welcomeMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BranchEmployeeHomePage.this, "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Auto updates userObject
    public void linkUserObject(){
        userRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) return;
                if (!curUser.getUid().equals(snapshot.getKey())) return;

                userObject = getBranchEmployee(snapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BranchEmployeeHomePage.this, "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
