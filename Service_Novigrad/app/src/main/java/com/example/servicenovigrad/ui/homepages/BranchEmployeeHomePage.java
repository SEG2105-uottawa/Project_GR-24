package com.example.servicenovigrad.ui.homepages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.servicenovigrad.R;

import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.branchEmployee.BranchInfo;
import com.example.servicenovigrad.ui.branchEmployee.ServiceRequests;
import com.example.servicenovigrad.ui.branchEmployee.ServicesOffered;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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
                //Check if there any services
                if (servicesByName != null) {
                    //Loop through outer HashMap (Service names)
                    for (Map.Entry<String, Object> serviceName : servicesByName.entrySet()) {
                        //Convert each value to a HashMap of strings (this is the service object)
                        HashMap<String, Object> serviceData = (HashMap<String, Object>) serviceName.getValue();
                        //Get price, form fields and document types
                        String price;
                        HashMap<String, String> formFields, documentTypes;
                        price = (String) serviceData.get("price");
                        formFields = (HashMap<String, String>) serviceData.get("formFields");
                        documentTypes = (HashMap<String, String>) serviceData.get("documentTypes");

                        //Convert data to objects
                        Service service = new Service(serviceName.getKey(), Double.parseDouble(price));
                        //Loop through form fields (if any)
                        if (formFields != null) {
                            for (Map.Entry<String, String> formField : formFields.entrySet()) {
                                service.addFormField(formField.getKey());
                            }
                        }
                        //Loop through document types (if any)
                        if (documentTypes != null) {
                            for (Map.Entry<String, String> documentType : documentTypes.entrySet()) {
                                service.addDocType(documentType.getKey());
                            }
                        }
                        userObject.addService(service);
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
