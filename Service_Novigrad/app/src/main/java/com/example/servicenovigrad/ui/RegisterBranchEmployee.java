package com.example.servicenovigrad.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.BranchEmployee;
import com.example.servicenovigrad.data.Customer;
import com.example.servicenovigrad.destinations.CustomerHomePage;
import com.example.servicenovigrad.destinations.EmployeeHomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterBranchEmployee extends AppCompatActivity {
    EditText fName, lName, iEmail, iUserName, iPassword, iConfPassword;
    Button registerButton;
    FirebaseAuth fAuth;
    FirebaseUser user;
    DatabaseReference mDatabase;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_branch_employee);

        fName = findViewById(R.id.register_employee_firstName);
        lName = findViewById(R.id.register_employee_lastName);
        iEmail = findViewById(R.id.register_employee_email);
        iUserName = findViewById(R.id.register_employee_username);
        iPassword = findViewById(R.id.register_employee_password);
        iConfPassword = findViewById(R.id.register_employee_confirm);
        registerButton = findViewById(R.id.register_branch_btn);

        fAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressBar = findViewById(R.id.register_branch_progressBar);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterBranchEmployee.this, MainPage.class));
            finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String first = fName.getText().toString().trim();
                final String last = lName.getText().toString().trim();
                final String uName = iUserName.getText().toString().trim();
                final String email = iEmail.getText().toString().trim();
                String password = iPassword.getText().toString().trim();
                String confPassword = iConfPassword.getText().toString().trim();

                // Validating fields

                if (TextUtils.isEmpty(email)) {
                    iEmail.setError("Email is required...");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    iPassword.setError("Password is required...");
                    return;
                }

                if (password.length() < 6) {
                    iPassword.setError("Password must be atleast 6 characters...");
                    return;
                }

                if (!password.equals(confPassword)) {
                    iPassword.setError("Make sure passwords match...");
                    iConfPassword.setError("Make sure passwords match...");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Registering user in Firebase Auth

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = fAuth.getCurrentUser();
                            writeNewEmployee(first, last, uName, email, user.getUid());
                            Toast.makeText(RegisterBranchEmployee.this, "Successfully created an employee account!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterBranchEmployee.this, EmployeeHomePage.class));
                            finish();
                        }
                        else {
                            Toast.makeText(RegisterBranchEmployee.this, "ERROR! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });


    }

    private void writeNewEmployee(String first, String last, String uName, String anEmail, String userID) {
        BranchEmployee employee = new BranchEmployee(first, last, uName, anEmail);

        mDatabase.child("users").child(userID).setValue(employee);

    }
}