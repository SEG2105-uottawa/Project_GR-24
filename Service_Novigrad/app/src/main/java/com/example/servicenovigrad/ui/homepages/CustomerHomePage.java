package com.example.servicenovigrad.ui.homepages;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ui.customer.SearchByAddress;
import com.example.servicenovigrad.ui.customer.SearchByHours;
import com.example.servicenovigrad.ui.customer.SearchByServices;
import com.example.servicenovigrad.users.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class CustomerHomePage extends HomePage {
    Button searchByAddress, searchByHours, searchByServices;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_customer_home_page);
        super.onCreate(savedInstanceState);
        linkUserObject();

        //Get views
        message = findViewById(R.id.message_customer_HP);
        searchByAddress = findViewById(R.id.search_by_address);
        searchByHours = findViewById(R.id.search_by_hours);
        searchByServices = findViewById(R.id.search_by_services);

        //Set text
        updateHomePage();

        //Listeners
        searchByAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerHomePage.this, SearchByAddress.class));
            }
        });

        searchByHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerHomePage.this, SearchByHours.class));
            }
        });

        searchByServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerHomePage.this, SearchByServices.class));
            }
        });


    }

    public void updateHomePage() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstName = snapshot.child("firstName").getValue().toString();
                String role = snapshot.child("role").getValue().toString();
                String welcomeMessage = "Welcome, " + firstName + ", to the customer homepage!\nYou are logged in as: " + role;
                message.setText(welcomeMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustomerHomePage.this, "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void linkUserObject() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) return;
                if (!curUser.getUid().equals(snapshot.getKey())) return;
                String firstName = snapshot.child("firstName").getValue().toString();
                String lastName = snapshot.child("lastName").getValue().toString();
                String userName = snapshot.child("userName").getValue().toString();
                userObject = new Customer(firstName, lastName, userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustomerHomePage.this, "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}