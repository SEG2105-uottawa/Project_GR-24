package com.example.servicenovigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.servicenovigrad.R;

public class RegisterPage extends AppCompatActivity {
    Button registerCustomer, registerBranchEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        registerCustomer = findViewById(R.id.register_customer);
        registerBranchEmployee = findViewById(R.id.register_branch);

        registerCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterCustomer.class));
                finish();
            }
        });

        registerBranchEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterBranchEmployee.class));
                finish();
            }
        });
    }
}