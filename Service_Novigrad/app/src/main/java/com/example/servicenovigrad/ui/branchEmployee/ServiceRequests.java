package com.example.servicenovigrad.ui.branchEmployee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.data.ServiceRequest;
import com.example.servicenovigrad.ui.UserPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ServiceRequests extends UserPage {

    ListView list;
    ArrayList<ServiceRequest> allRequests;
    ArrayAdapter<ServiceRequest> arrayAdapter;
    Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_requests);

        refresh = findViewById(R.id.refresh_requests);
        list = findViewById(R.id.employee_service_requests);
        updateList();

        //refresh button currently creates a random request as well as refreshing
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRequest();
            }
        });

    }

    public void updateList(){
        allRequests = new ArrayList<>();
        for (Map.Entry<String, ServiceRequest> request : branchObject().getServiceRequests().entrySet()){
            allRequests.add(request.getValue());
        }
        arrayAdapter = new ArrayAdapter<>(ServiceRequests.this,
                android.R.layout.simple_expandable_list_item_1, allRequests);
        list.setAdapter(arrayAdapter);

        //Set on click listener for all items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ServiceRequests.this, ProcessRequest.class);
                intent.putExtra("serviceRequest", (ServiceRequest) list.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }

    public  void createRequest(){
        Random generator = new Random();
        Object[] allServices = allServicesMap.values().toArray();
        Service randomService = (Service) allServices[generator.nextInt(allServices.length)];
        ServiceRequest request = new ServiceRequest(
                randomService,
                new Date().toString(),
                UUID.randomUUID().toString());
        userRef.child("serviceRequests").child(request.getRequestID()).setValue(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                updateList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }
}