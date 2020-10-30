package com.example.servicenovigrad.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.services.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminEditAllServices extends AppCompatActivity {

    ListView list;
    Button add_button, submit_button, cancel_button;
    DatabaseReference databaseReference, allServicesReference;
    ArrayList<Service> allServices;
    ArrayAdapter<Service> arrayAdapter;
    Dialog dialog;
    TextView dialog_header;
    EditText dialog_input;

    //Test Service
    Service test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_all_services);

        //Get Views
        list = findViewById(R.id.admin_services_list);
        add_button = findViewById(R.id.admin_services_button);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        allServicesReference = databaseReference.child("services");

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEntryDialog();
            }
        });

//        //Add Test Services
//        test = new Service("Test Name");
//        test.addDocType("Test Document Type");
//        test.addFormField("Test Form Field");
//        //Add to database
//        allServicesReference.child(test.getName()).setValue(test);
//        //Add another one
//        test.setName("Test Name 2");
//        test.addDocType("Test Document Type 2");
//        test.addFormField("Test Form Field 2");
//        allServicesReference.child(test.getName()).setValue(test);

        allServicesReference.addValueEventListener(new ValueEventListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Clear local data after a deletion
                allServices = new ArrayList<>();

                //Get JSON tree of all services (Firebase sends it as nested HashMaps)
                HashMap<String,Object> servicesByName = (HashMap<String,Object>) snapshot.getValue();

                //Loop through outer HashMap (Service names)
                for (Map.Entry<String, Object> serviceName : servicesByName.entrySet()){
                    //Convert each value to a HashMap of strings (this is the service object)
                    HashMap<String, Object> serviceData = (HashMap<String,Object>) serviceName.getValue();
                    //Get form fields and document types
                    HashMap<String,String> formFields, documentTypes;
                    formFields = (HashMap<String,String>) serviceData.get("formFields");
                    documentTypes = (HashMap<String,String>) serviceData.get("documentTypes");

                    //Convert data to objects
                    Service service = new Service(serviceName.getKey());
                    //Loop through form fields (if any)
                    if (formFields != null){
                        for (Map.Entry<String,String> formField: formFields.entrySet()){
                            service.addFormField(formField.getKey());
                        }
                    }
                    //Loop through document types (if any)
                    if (documentTypes != null){
                        for (Map.Entry<String,String> documentType : documentTypes.entrySet()){
                            service.addDocType(documentType.getKey());
                        }
                    }

                    allServices.add(service);
                }

                //Put the data into the list view
                arrayAdapter = new ArrayAdapter<>(AdminEditAllServices.this,
                        android.R.layout.simple_expandable_list_item_1, allServices);
                list.setAdapter(arrayAdapter);

                //Set on click listener for all items
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(AdminEditAllServices.this, AdminEditService.class);
                        intent.putExtra("service", (Service) list.getItemAtPosition(position));
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openEntryDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_enter_string);

        //get Views
        dialog_header = dialog.findViewById(R.id.entry_dialog_header);
        dialog_input = dialog.findViewById(R.id.entry_dialog_string);
        submit_button = dialog.findViewById(R.id.entry_dialog_submit);
        cancel_button = dialog.findViewById(R.id.entry_dialog_cancel);

        //Set text
        dialog_header.setText("Please enter the name of the new service");

        //Listeners
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serviceName = dialog_input.getText().toString();

                if (TextUtils.isEmpty(serviceName)) {
                    dialog_input.setError("Service Name required...");
                    return;

                }

                Service service = new Service(serviceName);
                allServicesReference.child(serviceName).setValue(service);
                Toast.makeText(getApplicationContext(), "New service created", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}