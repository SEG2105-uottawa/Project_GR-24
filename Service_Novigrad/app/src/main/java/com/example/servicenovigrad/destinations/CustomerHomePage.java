package com.example.servicenovigrad.destinations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.Customer;
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
    TextView welcome_message;
    DatabaseReference customerRef;
    Customer customer;
    String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        curUser = FirebaseAuth.getInstance().getCurrentUser();
        customerRef = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid());

        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customer = dataSnapshot.getValue(Customer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value. ", databaseError.toException());
            }
        });

        displayWelcomeMessage(customer);

        logout = findViewById(R.id.logout);



    }

    public void displayWelcomeMessage(Customer customer) {
        String message = "Welcome to the Customer home page!\nYou are logged in as " + customer.getUserName();
        welcome_message = findViewById(R.id.welcome_message);
        welcome_message.setText(message);
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); // Logs out current active user
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finish();
    }
}