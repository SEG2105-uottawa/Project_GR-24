package com.example.servicenovigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.servicenovigrad.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainPage extends AppCompatActivity {
    Button signInButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        FirebaseAuth.getInstance().signOut(); // Ensures that no user is currently signed in

        signInButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(MainPage.this, LoginPage.class));
            finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(MainPage.this, RegisterPage.class));
            finish();
            }
        });
    }


}