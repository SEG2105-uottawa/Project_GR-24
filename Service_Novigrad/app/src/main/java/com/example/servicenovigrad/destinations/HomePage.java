package com.example.servicenovigrad.destinations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ui.MainPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class HomePage extends AppCompatActivity {
    Button logout;
    FirebaseUser curUser;
    TextView message;
    DatabaseReference userRef;

    public abstract void updateHomePage(DatabaseReference userRef);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logout = findViewById(R.id.logout);

        curUser = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid());

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); // Logs out current active user
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finish();
    }
}
