package com.example.servicenovigrad.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Map;

public class SearchResults extends SearchPage {
    ArrayList<BranchEmployee> branchEmployees;
    ArrayAdapter<BranchEmployee> adapter;
    ListView list;
    Dialog dialog;
    TextView branchName, branchAddress;
    Button rateBranch, bookService, cancel;

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
                branchRef = searchResults.get(selection);

                dialog = new Dialog(SearchResults.this);
                dialog.setContentView(R.layout.dialog_branch_result);

                // Get views
                branchName = dialog.findViewById(R.id.chosen_branch_name);
                branchAddress = dialog.findViewById(R.id.chosen_branch_address);
                rateBranch = dialog.findViewById(R.id.rate_branch);
                bookService = dialog.findViewById(R.id.book_service);
                cancel = dialog.findViewById(R.id.cancel);

                // Set Text
                branchName.setText(selection.getBranchName());
                branchAddress.setText(selection.getAddress());

                // Listeners
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                rateBranch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(SearchResults.this, RateBranch.class));
                        finish();

                    }
                });

                bookService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(SearchResults.this, ChooseService.class));
                        finish();

                    }
                });

                dialog.show();
            }
        });
    }
}