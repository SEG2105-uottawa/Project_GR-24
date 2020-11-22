package com.example.servicenovigrad.ui.branchEmployee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.services.Service;
import com.example.servicenovigrad.ui.homepages.UserPage;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServicesOffered extends UserPage {
    TextView branchName_header, list_header;
    Button edit_button;
    ListView list;
    ArrayList<Service> servicesOfferedList;
    ArrayAdapter<Service> arrayAdapter;
    String[] allServices;
    boolean[] checkedServices;
    ArrayList<Integer> selectedServices;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_offered);

//        //Testing
//        final BranchEmployee branchObject = branchObject();
//        branchObject.addService(new Service("test service 1", 20.00));
//        branchObject.addService(new Service("test service 2", 20.00));
//        branchObject.addService(new Service("test service 3", 20.00));
//        userRef.setValue(branchObject);

        //get views
        branchName_header = findViewById(R.id.services_offered_branchName);
        edit_button = findViewById(R.id.services_offered_edit);
        list = findViewById(R.id.services_offered_listView);

        //set Text
        branchName_header.setText(branchObject().getBranchName());

        //Listeners
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editServicesDialog();
            }
        });

        //Fill List
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Clear local data after a change
                servicesOfferedList = new ArrayList<>();

                //Loop through services
                HashMap<String,Service> servicesOffered = branchObject().getServicesOffered();
                for (Map.Entry<String, Service> service : servicesOffered.entrySet()){
                    servicesOfferedList.add(service.getValue());
                }

                //Put the data into the list view
                arrayAdapter = new ArrayAdapter<>(ServicesOffered.this,
                        android.R.layout.simple_expandable_list_item_1, servicesOfferedList);
                list.setAdapter(arrayAdapter);

                //Set on click listener for all items
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ServicesOffered.this, ServiceInfo.class);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editServicesDialog(){
        selectedServices = new ArrayList<>();
        serviceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,Object> servicesByName = (HashMap<String,Object>) snapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
