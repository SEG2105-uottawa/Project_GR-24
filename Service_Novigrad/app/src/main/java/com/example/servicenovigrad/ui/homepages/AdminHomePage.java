package com.example.servicenovigrad.ui.homepages;

import com.example.servicenovigrad.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.ui.admin.AdminEditBranchAccounts;
import com.example.servicenovigrad.ui.admin.AdminEditCustomerAccounts;
import com.example.servicenovigrad.ui.admin.AdminEditAllServices;
import com.example.servicenovigrad.ui.MainPage;

public class AdminHomePage extends AppCompatActivity {
    Button logout, admin_branch_accounts, admin_customer_accounts, admin_services_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        logout = findViewById(R.id.logout);
        admin_branch_accounts = findViewById(R.id.admin_branch_accounts);
        admin_customer_accounts = findViewById(R.id.admin_customer_accounts);
        admin_services_editor = findViewById(R.id.admin_services_editor);

        TextView message = findViewById(R.id.message_admin_HP);
        message.setText("Welcome Administrator");

        admin_branch_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomePage.this, AdminEditBranchAccounts.class));
            }
        });

        admin_customer_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomePage.this, AdminEditCustomerAccounts.class));
            }
        });

        admin_services_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomePage.this, AdminEditAllServices.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });
    }

    public void logout(View view) {
        startActivity(new Intent(getApplicationContext(), MainPage.class));
        finishAffinity();
    }
}