package com.example.servicenovigrad.ui.homepages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.servicenovigrad.R;

import com.example.servicenovigrad.services.Service;
import com.example.servicenovigrad.services.ServiceRequest;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.branchEmployee.BranchInfo;
import com.example.servicenovigrad.ui.branchEmployee.ServiceRequests;
import com.example.servicenovigrad.ui.branchEmployee.ServicesOffered;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class BranchEmployeeHomePage extends HomePage {
    Button branchInfo, servicesOffered, serviceRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_employee_home_page);
        linkUserObject();

        branchInfo = findViewById(R.id.branch_info);
        servicesOffered = findViewById(R.id.services_offered);
        serviceRequests = findViewById(R.id.service_requests);

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

    }

    public void updateHomePage() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String branchName = dataSnapshot.child("branchName").getValue().toString();
                String firstName = dataSnapshot.child("firstName").getValue().toString();
                String role = dataSnapshot.child("role").getValue().toString();
                String welcomeMessage = "Welcome, "+ firstName + " of branch: "+ branchName + ", to the Employee homepage!\nYou are logged in as: " + role;
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
            @Override
            @SuppressWarnings("unchecked")
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BranchEmployee userObject = new BranchEmployee(
                        (String) snapshot.child("firstName").getValue(),
                        (String) snapshot.child("lastName").getValue(),
                        (String) snapshot.child("userName").getValue(),
                        (String) snapshot.child("branchName").getValue(),
                        (String) snapshot.child("address").getValue(),
                        (String) snapshot.child("phoneNumber").getValue()
                );
                //Get JSON tree of all services offered (Firebase sends it as nested HashMaps)
                HashMap<String,Object> servicesByName = (HashMap<String,Object>) snapshot.child("servicesOffered").getValue();
                if (servicesByName != null) {
                    //Add all services
                    for (Map.Entry<String, Object> serviceName : servicesByName.entrySet()) {
                        userObject.addService(allServicesMap.get(serviceName.getKey()));
                    }
                }
                //Get service requests
                //TBD - READ FORM FIELDS AND DOC TYPES!!!
                HashMap<String,Object> serviceRequestsByID = (HashMap<String, Object>) snapshot.child("serviceRequests").getValue();
                if (serviceRequestsByID != null){
                    for(Map.Entry<String,Object> serviceRequest : serviceRequestsByID.entrySet()){
                        HashMap<String, Object> requestData = (HashMap<String, Object>) serviceRequest.getValue();
                        String dateCreated = (String) requestData.get("dateCreated");
                        String requestID = (String) requestData.get("requestID");
                        String serviceName = (String) requestData.get("serviceName");
                        //If service doesn't exist, delete it
                        if (!allServicesMap.containsKey(serviceName)){
                            userRef.child("serviceRequests").child(requestID).removeValue();
                        }else {
                            Service service = allServicesMap.get(serviceName);
                            ServiceRequest request = new ServiceRequest(service, dateCreated, requestID);
                            ////TBD - READ FORM FIELDS AND DOC TYPES!!!
                            userObject.addServiceRequest(request);
                        }
                    }
                }
                UserPage.userObject = userObject;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BranchEmployeeHomePage.this, "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
