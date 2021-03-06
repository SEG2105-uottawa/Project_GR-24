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
import com.example.servicenovigrad.ui.homepages.AdminHomePage;
import com.example.servicenovigrad.ui.homepages.BranchEmployeeHomePage;
import com.example.servicenovigrad.ui.homepages.CustomerHomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    FirebaseUser curUser;
    DatabaseReference userRef;
    EditText login_email, login_password;
    Button login_button;
    ProgressBar login_progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        fAuth = FirebaseAuth.getInstance();

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_login);
        login_progressBar = findViewById(R.id.login_progressBar);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    login_email.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    login_password.setError("Password is required");
                    return;
                }

                if (email.equals("admin") && password.equals("admin")){
                    startActivity(new Intent(LoginPage.this, AdminHomePage.class));
                    finishAffinity();
                }

                else {

                    login_progressBar.setVisibility(View.VISIBLE);

                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                curUser = fAuth.getCurrentUser();

                                userRef = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid());

                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        //If userID has been deleted from database (By admin), delete the user
                                        Object test = dataSnapshot.getValue();
                                        if (dataSnapshot.getValue() == null){
                                            deleteUser();
                                            login_progressBar.setVisibility(View.INVISIBLE);
                                            return;
                                        }
                                        else{
                                            Toast.makeText(LoginPage.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                            String type = dataSnapshot.child("role").getValue().toString();
                                            if (type.equals("BRANCH_EMPLOYEE")) {
                                                startActivity(new Intent(LoginPage.this, BranchEmployeeHomePage.class));
                                            } else if (type.equals("CUSTOMER")) {
                                                startActivity(new Intent(LoginPage.this, CustomerHomePage.class));
                                            }
                                        }
                                        //Closes all activities
                                        finishAffinity();
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(LoginPage.this, "Error retrieving data...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(LoginPage.this, "ERROR! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                login_progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });
    }
    public void deleteUser(){
        boolean complete = false;
        curUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginPage.this,
                        "Sorry, but your account has been deleted by an administrator", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
