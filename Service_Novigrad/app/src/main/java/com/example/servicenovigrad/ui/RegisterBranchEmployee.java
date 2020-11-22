package com.example.servicenovigrad.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.users.BranchEmployee;
import com.example.servicenovigrad.ui.homepages.BranchEmployeeHomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterBranchEmployee extends AppCompatActivity {
    EditText register_branch_branchName, register_branch_firstName, register_branch_lastName, register_branch_email,
            register_branch_username, register_branch_password, register_branch_confirm;
    Button register_branch_btn;
    FirebaseAuth fAuth;
    FirebaseUser user;
    DatabaseReference mDatabase;
    ProgressBar progressBar;
    String branchName, firstName, lastName, email, userName, password, confPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_branch_employee);

        register_branch_branchName = findViewById(R.id.register_branch_branchName);
        register_branch_firstName = findViewById(R.id.register_branch_firstName);
        register_branch_lastName = findViewById(R.id.register_branch_lastName);
        register_branch_email = findViewById(R.id.register_branch_email);
        register_branch_username = findViewById(R.id.register_branch_username);
        register_branch_password = findViewById(R.id.register_branch_password);
        register_branch_confirm = findViewById(R.id.register_branch_confirm);
        register_branch_btn = findViewById(R.id.register_branch_btn);

        fAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressBar = findViewById(R.id.register_branch_progressBar);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterBranchEmployee.this, MainPage.class));
            finish();
        }

        register_branch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branchName = register_branch_branchName.getText().toString();
                firstName = register_branch_firstName.getText().toString().trim();
                lastName = register_branch_lastName.getText().toString().trim();
                userName = register_branch_username.getText().toString().trim();
                email = register_branch_email.getText().toString().trim();
                password = register_branch_password.getText().toString().trim();
                confPassword = register_branch_confirm.getText().toString().trim();

                // Validating fields
                boolean invalid = false;

                if (TextUtils.isEmpty(email)) {
                    register_branch_email.setError("Email is required...");
                    invalid = true;
                }

                if (TextUtils.isEmpty(password)) {
                    register_branch_password.setError("Password is required...");
                    invalid = true;
                }

                if (password.length() < 6) {
                    register_branch_password.setError("Password must be atleast 6 characters...");
                    invalid = true;
                }

                if (!password.equals(confPassword)) {
                    register_branch_password.setError("Make sure passwords match...");
                    register_branch_confirm.setError("Make sure passwords match...");
                    invalid = true;
                }

                if (!(firstName.matches("[a-zA-Z]+") || firstName.matches("[a-zA-Z]+-[a-zA-Z]+"))){
                    register_branch_firstName.setError("Names may only contain letters (hyphenated names are acceptable)...");
                    invalid = true;
                }

                if (!(lastName.matches("[a-zA-Z]+") || lastName.matches("[a-zA-Z]+-[a-zA-Z]+"))){
                    register_branch_lastName.setError("Names may only contain letters (hyphenated names are acceptable)...");
                    invalid = true;
                }

                if (invalid) return;

                progressBar.setVisibility(View.VISIBLE);

                // Registering user in Firebase Auth

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = fAuth.getCurrentUser();
                            writeNewBranchEmployee(firstName, lastName, userName, branchName, user.getUid());
                            Toast.makeText(RegisterBranchEmployee.this, "Successfully created a branch employee account!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterBranchEmployee.this,  BranchEmployeeHomePage.class));
                            finishAffinity();
                        }
                        else {
                            Toast.makeText(RegisterBranchEmployee.this, "ERROR! Employee creation unsuccessful...", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });


    }

    private void writeNewBranchEmployee(String firstName, String lastName, String userName, String branchName, String userID) {
        BranchEmployee branch = new BranchEmployee(firstName, lastName, userName, branchName);

        mDatabase.child("users").child(userID).setValue(branch);

        for (int i=0; i<7; i++) {
            mDatabase.child("users").child(userID).child("hours").setValue(branch.getHours());
        }

    }
}