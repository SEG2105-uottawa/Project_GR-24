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
import com.example.servicenovigrad.users.Customer;
import com.example.servicenovigrad.ui.homepages.CustomerHomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterCustomer extends AppCompatActivity {
    EditText fName, lName, iEmail, iUserName, iPassword, iConfPassword;
    Button registerButton;
    FirebaseAuth fAuth;
    FirebaseUser user;
    DatabaseReference mDatabase;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        fName = findViewById(R.id.register_customer_firstName);
        lName = findViewById(R.id.register_customer_lastName);
        iEmail = findViewById(R.id.register_customer_email);
        iUserName = findViewById(R.id.register_customer_username);
        iPassword = findViewById(R.id.register_customer_password);
        iConfPassword = findViewById(R.id.register_customer_confirm);
        registerButton = findViewById(R.id.register_customer_btn);

        fAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressBar = findViewById(R.id.register_customer_progressBar);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterCustomer.this, MainPage.class));
            finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String first = fName.getText().toString().trim();
                final String last = lName.getText().toString().trim();
                final String uName = iUserName.getText().toString().trim();
                String email = iEmail.getText().toString().trim();
                String password = iPassword.getText().toString().trim();
                String confPassword = iConfPassword.getText().toString().trim();

                // Validating fields
                boolean invalid = false;

                if (TextUtils.isEmpty(email)) {
                    iEmail.setError("Email is required...");
                    invalid = true;
                }

                if (TextUtils.isEmpty(password)) {
                    iPassword.setError("Password is required...");
                    invalid = true;
                }

                if (password.length() < 6) {
                    iPassword.setError("Password must be atleast 6 characters...");
                    invalid = true;
                }

                if (!password.equals(confPassword)) {
                    iPassword.setError("Make sure passwords match...");
                    iConfPassword.setError("Make sure passwords match...");
                    invalid = true;
                }

                if (!(first.matches("[a-zA-Z]+") || first.matches("[a-zA-Z]+-[a-zA-Z]+"))){
                    fName.setError("Names may only contain letters (hyphenated names are acceptable)...");
                    invalid = true;
                }

                if (!(last.matches("[a-zA-Z]+") || last.matches("[a-zA-Z]+-[a-zA-Z]+"))){
                    lName.setError("Names may only contain letters (hyphenated names are acceptable)...");
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
                            writeNewCustomer(first, last, uName, user.getUid());
                            Toast.makeText(RegisterCustomer.this, "Successfully created a customer account!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterCustomer.this, CustomerHomePage.class));
                            finishAffinity();
                        }
                        else {
                            Toast.makeText(RegisterCustomer.this, "ERROR! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });


    }

    private void writeNewCustomer(String first, String last, String uName, String userID) {
        Customer customer = new Customer(first, last, uName);

        mDatabase.child("users").child(userID).setValue(customer);

    }
}