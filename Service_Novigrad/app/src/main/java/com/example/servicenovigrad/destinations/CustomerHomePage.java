package com.example.servicenovigrad.destinations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ui.MainPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerHomePage extends AppCompatActivity {
    Button logout;
    FirebaseUser curUser;
    TextView message;
    DatabaseReference customerRef;
    String TAG, name, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);

        logout = findViewById(R.id.logout);
        message = findViewById(R.id.message_customer_HP);

        curUser = FirebaseAuth.getInstance().getCurrentUser();
        customerRef = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid());

        //updateHomePage(customerRef);
    }

    public void updateHomePage(DatabaseReference customerRef) {
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fName = dataSnapshot.child("firstName").getValue().toString();
                String role = dataSnapshot.child("role").getValue().toString();
                String welcomeMessage = "Welcome, "+ fName +", to the customer homepage!" + role;
                message.setText(welcomeMessage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CustomerHomePage.this, "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); // Logs out current active user
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finish();
    }
}