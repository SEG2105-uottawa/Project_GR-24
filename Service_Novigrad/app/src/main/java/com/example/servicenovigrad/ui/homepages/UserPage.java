package com.example.servicenovigrad.ui.homepages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.services.Service;
import com.example.servicenovigrad.ui.admin.AdminEditAllServices;
import com.example.servicenovigrad.ui.admin.AdminEditService;
import com.example.servicenovigrad.users.Account;
import com.example.servicenovigrad.users.BranchEmployee;
import com.example.servicenovigrad.users.Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class UserPage extends AppCompatActivity {
    protected static Account userObject;
    protected static DatabaseReference userRef;
    protected static FirebaseUser curUser;
    protected static final DatabaseReference serviceRef = FirebaseDatabase.getInstance().getReference().child("services");
    protected static ArrayList<Service> allServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linkAllServices();
    }

    protected BranchEmployee branchObject(){
        return (BranchEmployee) userObject;
    }

    protected Customer customerObject(){
        return (Customer) userObject;
    }

    private void linkAllServices(){
        serviceRef.addValueEventListener(new ValueEventListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Clear local data after a change
                allServices = new ArrayList<>();

                //Get JSON tree of all services (Firebase sends it as nested HashMaps)
                HashMap<String,Object> servicesByName = (HashMap<String,Object>) snapshot.getValue();

                //Loop through outer HashMap (Service names)
                for (Map.Entry<String, Object> serviceName : servicesByName.entrySet()){
                    //Convert each value to a HashMap of strings (this is the service object)
                    HashMap<String, Object> serviceData = (HashMap<String,Object>) serviceName.getValue();
                    //Get price, form fields and document types
                    String price;
                    HashMap<String,String> formFields, documentTypes;
                    price = (String) serviceData.get("price");
                    formFields = (HashMap<String,String>) serviceData.get("formFields");
                    documentTypes = (HashMap<String,String>) serviceData.get("documentTypes");

                    //Convert data to objects
                    Service service = new Service(serviceName.getKey(), Double.parseDouble(price));
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}