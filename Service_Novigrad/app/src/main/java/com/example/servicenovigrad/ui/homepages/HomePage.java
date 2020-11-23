package com.example.servicenovigrad.ui.homepages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ui.MainPage;
import com.example.servicenovigrad.ui.UserPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public abstract class HomePage extends UserPage {
    protected Button logout;
    protected TextView message;


    public abstract void updateHomePage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linkAllServices();

        logout = findViewById(R.id.logout);
        curUser = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid());
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); // Logs out current active user
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finishAffinity();
    }
}
