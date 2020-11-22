package com.example.servicenovigrad.ui.branchEmployee;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.annotation.Nullable;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.services.Service;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.admin.AdminEditAllServices;
import com.example.servicenovigrad.ui.admin.AdminEditService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> selectedItems;
    HashMap<String, Service> servicesOffered;


    @Override
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
                openAddDialog();
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

    private void openAddDialog(){

        servicesOffered = branchObject().getServicesOffered();
        listItems = new String[allServices.size()];
        selectedItems = new ArrayList<>();

        int i = 0;
        for (Service service : allServices){
            listItems[i] = service.getName();
            i++;
        }
        checkedItems = new boolean[listItems.length];

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ServicesOffered.this);
        mBuilder.setTitle("Select Services to Add");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    if(!selectedItems.contains(which)){
                        selectedItems.add(which);
                    }else{
                        selectedItems.remove(which);
                    }
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Add Selected", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i=0; i <selectedItems.size(); i++){
                    Service service = allServicesMap.get(listItems[selectedItems.get(i)]);
                    userRef.child("servicesOffered").child(service.getName()).setValue(service);
                }
            }
        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mBuilder.setNeutralButton("Delete all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userRef.child("servicesOffered").removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getApplicationContext(), "All services removed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
