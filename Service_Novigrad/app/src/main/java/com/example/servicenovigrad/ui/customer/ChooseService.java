package com.example.servicenovigrad.ui.customer;

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
import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.data.ServiceRequest;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.customer.BookService;
import com.example.servicenovigrad.ui.customer.SearchPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChooseService extends SearchPage {

    TextView branchName_header;
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
        setContentView(R.layout.activity_customer_choose_service);

        //get views
        branchName_header = findViewById(R.id.customer_choose_service_branchName);
        list = findViewById(R.id.customer_choose_service_listView);

        //set Text
        branchName_header.setText(selection.getBranchName());

        //Fill List
        servicesOfferedList = new ArrayList<>();

        //Loop through services
        HashMap<String,Service> servicesOffered = selection.getServicesOffered();
        for (Map.Entry<String, Service> service : servicesOffered.entrySet()){
            servicesOfferedList.add(service.getValue());
        }

        //Put the data into the list view
        arrayAdapter = new ArrayAdapter<>(ChooseService.this,
                android.R.layout.simple_expandable_list_item_1, servicesOfferedList);
        list.setAdapter(arrayAdapter);

        //Set on click listener for all items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = (Service) list.getItemAtPosition(position);
                request =   new ServiceRequest(service,
                            new Date().toString(),
                            UUID.randomUUID().toString());
                startActivity(new Intent(ChooseService.this, BookService.class));
            }
        });
    }
}
