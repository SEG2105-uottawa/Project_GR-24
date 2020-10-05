package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
    }

    public void openRegisterCustomer(View view) {
        Intent intent = new Intent(this, RegisterCustomer.class);
        startActivity(intent);
    }

    public void openRegisterBranchEmployee(View view) {
        Intent intent = new Intent(this, RegisterBranchEmployee.class);
        startActivity(intent);
    }
}