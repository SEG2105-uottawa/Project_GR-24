package com.example.servicenovigrad.ui.admin;

import androidx.annotation.NonNull;

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
import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.ui.UserPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AdminEditAllServices extends UserPage {

    ListView list;
    Button add_button, submit_button, cancel_button;
    ArrayAdapter<Service> arrayAdapter;
    Dialog dialog;
    TextView dialog_header;
    EditText dialog_name, dialog_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_all_services);

        //Get Views
        list = findViewById(R.id.admin_services_list);
        add_button = findViewById(R.id.admin_services_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEntryDialog();
            }
        });

        serviceRef.addValueEventListener(new ValueEventListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
        dialog.setContentView(R.layout.dialog_enter_string2);

        //Get Views
        dialog_header = dialog.findViewById(R.id.entry_dialog_header);
        dialog_name = dialog.findViewById(R.id.entry_dialog_string);
        dialog_price = dialog.findViewById(R.id.entry_dialog_string2);
        submit_button = dialog.findViewById(R.id.entry_dialog_submit);
        cancel_button = dialog.findViewById(R.id.entry_dialog_cancel);

        //Set text
        dialog_header.setText("Please enter the name and price of the new service");
        dialog_name.setHint("Service Name");
        dialog_price.setHint("Price of service");

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
                String serviceName = dialog_name.getText().toString();
                String servicePrice = dialog_price.getText().toString();

                //Form validation
                boolean invalid = false;
                if (TextUtils.isEmpty(serviceName)) {
                    dialog_name.setError("Service Name required...");
                    invalid = true;
                }
                if (TextUtils.isEmpty(servicePrice)) {
                    dialog_price.setError("Price required...");
                    invalid = true;
                }
                if (!servicePrice.matches("^[0-9]+.?[0-9]?[0-9]?$") ){
                    dialog_price.setError("Invalid price format: Digits only, with optional decimal and two decimal places.");
                    invalid = true;
                }

                if(invalid) return;

                Service service = new Service(serviceName, Double.parseDouble(servicePrice));
                serviceRef.child(serviceName).setValue(service);
                Toast.makeText(getApplicationContext(), "New service created", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}