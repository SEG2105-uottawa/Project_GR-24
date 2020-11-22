package com.example.servicenovigrad.ui.branchEmployee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.services.Service;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.admin.AdminEditService;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceInfo extends UserPage {

    Service service;
    TextView header, servicePrice;
    ListView reqFormsList, reqDocsList;
    Button delServiceBtn;
    ArrayList<String> allForms, allDocs;
    ArrayAdapter<String> formsAdapter, docsAdapter;
    DatabaseReference databaseServiceReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info_employee);
        service = (Service) getIntent().getSerializableExtra("service");
        databaseServiceReference = FirebaseDatabase.getInstance().getReference().child("services").child(service.getName());

        //get views
        header = findViewById(R.id.service_info_employee_header);
        reqFormsList = findViewById(R.id.service_info_employee_form_list);
        reqDocsList = findViewById(R.id.service_info_employee_doc_list);
        delServiceBtn = findViewById(R.id.service_info_employee_del);
        servicePrice = findViewById(R.id.service_info_employee_price);

        //Set Text
        header.setText(service.getName());
        servicePrice.setText("Price: $" + String.format("%.2f",service.returnPrice()));

        //Populate Lists
        allForms = new ArrayList<>();
        allDocs = new ArrayList<>();

        final HashMap<String, String> formFields = service.getFormFields();
        HashMap<String, Object> docFields = service.getDocumentTypes();

        if (formFields != null) {
            for (Map.Entry<String, String> formField: formFields.entrySet()) {
                allForms.add(formField.getKey());
            }

            formsAdapter = new ArrayAdapter<>(ServiceInfo.this, android.R.layout.simple_expandable_list_item_1, allForms);
            reqFormsList.setAdapter(formsAdapter);
        }

        if (docFields != null) {
            for (Map.Entry<String, Object> docField: docFields.entrySet()) {
                allDocs.add(docField.getKey());
            }

            docsAdapter = new ArrayAdapter<>(ServiceInfo.this, android.R.layout.simple_expandable_list_item_1, allDocs);
            reqDocsList.setAdapter(docsAdapter);
        }

        //Listener
        delServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.child("servicesOffered").child(service.getName()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getApplicationContext(), "Service Removed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}