package com.example.servicenovigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.destinations.*;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    public void authenticateUser(View view) {
        EditText login_username = findViewById(R.id.login_username);
        EditText login_password = findViewById(R.id.login_password);

        String username = login_username.getText().toString();
        String password = login_password.getText().toString();

        //debugging
        Log.i(null, "Username: " + username);
        Log.i(null, "Password: " + password);

        // TBD: Authenticate username & password
        // if authenticated:

        openWelcomePage(view);
    }

    public void openWelcomePage(View view) {
        Intent intent = new Intent(this, CustomerHomePage.class);
        startActivity(intent);
    }
}