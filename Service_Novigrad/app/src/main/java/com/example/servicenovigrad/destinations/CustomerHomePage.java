package com.example.servicenovigrad.destinations;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CustomerHomePage extends HomePage {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);

        message = findViewById(R.id.message_customer_HP);
        updateHomePage(userRef);
    }

    public void updateHomePage(final DatabaseReference userRef) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstName = dataSnapshot.child("firstName").getValue().toString();
                String role = dataSnapshot.child("role").getValue().toString();
                String welcomeMessage = "Welcome, "+ firstName +", to the customer homepage!\nYou are logged in as: " + role;
                message.setText(welcomeMessage);
                //Should remove listener if it's a one time thing
                //Else it gets called whenever data is changed - can crash
                userRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CustomerHomePage.this, "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}