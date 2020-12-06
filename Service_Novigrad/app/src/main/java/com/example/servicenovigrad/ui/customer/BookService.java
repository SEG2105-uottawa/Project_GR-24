package com.example.servicenovigrad.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookService extends AppCompatActivity {

    ListView list;
    ArrayList<Service> servicesOfferedList;
    ArrayAdapter<Service> arrayAdapter;
    HashMap<String, Service> servicesOffered;
    protected static HashMap<Service, DatabaseReference> services;
    protected static Service selection;
    protected static DatabaseReference branchRef;
    ArrayAdapter<Service> adapter;
    public static final String EXTRA_SERVICE = "com.example.servicenovigrad.extra.SERVICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);

        //get views
        list = findViewById(R.id.services_offered_list);

        servicesOfferedList = new ArrayList<>();

        for (Map.Entry<Service, DatabaseReference> entry : services.entrySet()){
            servicesOfferedList.add(entry.getKey());
        }

        adapter = new ArrayAdapter<>(BookService.this,
                android.R.layout.simple_expandable_list_item_1, servicesOfferedList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selection = (Service) list.getItemAtPosition(position);
                branchRef = services.get(selection);
                Intent intent = new Intent(BookService.this, SubmitServiceRequest.class);
                intent.putExtra(EXTRA_SERVICE, selection);
                startActivity(intent);
            }
        });
    }
}