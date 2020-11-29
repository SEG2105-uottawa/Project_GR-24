package com.example.servicenovigrad.ui.customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.branchEmployee.ServicesOffered;
import com.example.servicenovigrad.ui.homepages.BranchEmployeeHomePage;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchByServices extends SearchPage {

    Button edit, search, clear;
    ListView list;
    ArrayList<DataSnapshot> branchEmployees;
    ArrayList<String> servicesIncluded;
    ArrayAdapter<String> arrayAdapter;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_services);

        servicesIncluded = new ArrayList<>();
        branchEmployees = new ArrayList<>();

        //Get views
        edit = findViewById(R.id.search_by_services_edit);
        search = findViewById(R.id.search_by_services_searchBtn);
        list = findViewById(R.id.search_by_services_listView);
        clear = findViewById(R.id.search_by_services_clear);

        //Listeners
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByServices();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servicesIncluded.clear();
                refreshList();
            }
        });

        //Populate List
        refreshList();
    }

    private void openDialog(){
        listItems = new String[allServices.size()];
        selectedItems = new ArrayList<>();

        int i = 0;
        for (Service service : allServices){
            listItems[i] = service.getName();
            i++;
        }
        checkedItems = new boolean[listItems.length];

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SearchByServices.this);
        mBuilder.setTitle("Select Services to Add");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    if(!selectedItems.contains(which)){
                        selectedItems.add(which);
                    }
                }else{
                    selectedItems.remove(which);
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Add Selected", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i=0; i <selectedItems.size(); i++){
                    String serviceName = listItems[selectedItems.get(i)];
                    if (!servicesIncluded.contains(serviceName)) {
                        servicesIncluded.add(serviceName);
                    }
                }
                refreshList();
            }
        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void refreshList(){
        arrayAdapter = new ArrayAdapter<>(SearchByServices.this,
                android.R.layout.simple_expandable_list_item_1, servicesIncluded);
        list.setAdapter(arrayAdapter);
    }

    private void searchByServices(){
        if (servicesIncluded.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please choose at least one service", Toast.LENGTH_SHORT).show();
            return;
        }

        searchResults = new HashMap<>();

        allUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.getChildren()){
                    if (isBranchEmployee(user)){
                        DataSnapshot servicesOfferedRef = user.child("servicesOffered");
                        if (servicesOfferedRef.exists()) {
                            for (String serviceName : servicesIncluded){
                                if (servicesOfferedRef.child(serviceName).exists()){
                                    searchResults.put(getBranchEmployee(user), user.getRef());
                                    break;
                                }
                            }
                        }
                    }
                }
                startActivity(new Intent(SearchByServices.this, SearchResults.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}