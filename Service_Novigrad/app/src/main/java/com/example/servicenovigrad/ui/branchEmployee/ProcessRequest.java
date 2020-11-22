package com.example.servicenovigrad.ui.branchEmployee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.services.Service;
import com.example.servicenovigrad.services.ServiceRequest;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.admin.AdminEditAllServices;
import com.example.servicenovigrad.ui.admin.AdminEditService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Map;

public class ProcessRequest extends UserPage {
    TextView header, date;
    Button approve, deny;
    ServiceRequest request;
    ArrayList<String> allForms, allDocs;
    ArrayAdapter<String> formsAdapter, docsAdapter;
    ListView formList, docList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_request);

        request = (ServiceRequest) getIntent().getSerializableExtra("serviceRequest");
        allForms = new ArrayList<>();
        allDocs = new ArrayList<>();

        //get views
        header = findViewById(R.id.process_request_header);
        date = findViewById(R.id.process_request_date);
        approve = findViewById(R.id.process_request_approve);
        deny = findViewById(R.id.process_request_deny);
        formList = findViewById(R.id.process_request_form_list);
        docList = findViewById(R.id.process_request_doc_list);

        //set text
        header.setText(request.getServiceName());
        date.setText(request.getDateCreated());

        //populate lists
        for (Map.Entry<String, String> formField : request.getFormFields().entrySet()){
            String string = formField.getKey() + ": " + formField.getValue();
            allForms.add(string);
        }
        for (Map.Entry<String, Object> docType : request.getDocumentTypes().entrySet()){
            String string = docType.getKey() + ": " + docType.getValue();
            allDocs.add(string);
        }

        formsAdapter = new ArrayAdapter<>(ProcessRequest.this, android.R.layout.simple_expandable_list_item_1, allForms);
        formList.setAdapter(formsAdapter);

        docsAdapter = new ArrayAdapter<>(ProcessRequest.this, android.R.layout.simple_expandable_list_item_1, allDocs);
        docList.setAdapter(docsAdapter);

        //Listeners
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.child("serviceRequests").child(request.getRequestID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successfully approved request.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable to approve request...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.child("serviceRequests").child(request.getRequestID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successfully denied request.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable to deny request...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}