package com.example.servicenovigrad.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Map;

public class SearchResults extends SearchPage {
    ArrayList<BranchEmployee> branchEmployees;
    ArrayAdapter<BranchEmployee> adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        list = findViewById(R.id.search_results_list);

        branchEmployees = new ArrayList<>();

        for (Map.Entry<BranchEmployee,DatabaseReference> entry : searchResults.entrySet()){
            branchEmployees.add(entry.getKey());
        }

        adapter = new ArrayAdapter<>(SearchResults.this,
                android.R.layout.simple_expandable_list_item_1, branchEmployees);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selection = (BranchEmployee) list.getItemAtPosition(position);
                startActivity(new Intent(SearchResults.this, BookService.class));
            }
        });
    }
}