package com.example.servicenovigrad.destinations;

import com.example.servicenovigrad.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.ui.MainPage;

public class AdminHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        TextView message = findViewById(R.id.message_admin_HP);
        message.setText("Welcome Administrator");
    }

    public void logout(View view) {
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finish();
    }
}