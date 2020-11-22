package com.example.servicenovigrad.ui.branchEmployee;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.homepages.BranchEmployeeHomePage;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class BranchInfo extends UserPage {

    TextView branchName, branchAddress, branchPhoneNumber;
    Button editBranchName, editBranchAddress, editBranchPhoneNumber, submit_button, cancel_button;
    Dialog dialog;
    TextView dialog_header;
    EditText dialog_new_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_info);

        // Get Views
        branchName = findViewById(R.id.branch_name);
        branchAddress = findViewById(R.id.branch_address);
        branchPhoneNumber = findViewById(R.id.branch_phone_number);

        // Listeners
        editBranchName = findViewById(R.id.edit_branch_name);
        editBranchAddress = findViewById(R.id.edit_branch_address);
        editBranchPhoneNumber = findViewById(R.id.edit_branch_phone_number);

        // Set Texts
        branchName.setText(branchObject().getBranchName());
        branchAddress.setText(branchObject().getAddress());
        branchPhoneNumber.setText(branchObject().getPhoneNumber());

        editBranchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(BranchInfo.this);
                dialog.setContentView(R.layout.dialog_enter_string);

                //Get Views
                dialog_header = dialog.findViewById(R.id.entry_dialog_header);
                dialog_new_value = dialog.findViewById(R.id.entry_dialog_string);
                submit_button = dialog.findViewById(R.id.entry_dialog_submit);
                cancel_button = dialog.findViewById(R.id.entry_dialog_cancel);

                //Set text
                dialog_header.setText("Please enter the new Branch Name...");
                dialog_new_value.setHint("Branch Name");

                //Listeners
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                submit_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newBranchName = dialog_new_value.getText().toString();

                        //Form validation
                        if (TextUtils.isEmpty(newBranchName)) {
                            dialog_new_value.setError("Branch Name required...");
                            return;
                        }

                        //branchObject().setBranchName(newBranchName);
                        userRef.child("branchName").setValue(newBranchName);

                        Toast.makeText(getApplicationContext(), "Branch Name Changed!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(BranchInfo.this, BranchInfo.class));
                        finish();

                    }
                });

                dialog.show();

            }
        });

        editBranchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(BranchInfo.this);
                dialog.setContentView(R.layout.dialog_enter_string);

                //Get Views
                dialog_header = dialog.findViewById(R.id.entry_dialog_header);
                dialog_new_value = dialog.findViewById(R.id.entry_dialog_string);
                submit_button = dialog.findViewById(R.id.entry_dialog_submit);
                cancel_button = dialog.findViewById(R.id.entry_dialog_cancel);

                //Set text
                dialog_header.setText("Please enter the new Branch Address...");
                dialog_new_value.setHint("Branch Address");

                //Listeners
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                submit_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newAddress = dialog_new_value.getText().toString();

                        //Form validation
                        if (TextUtils.isEmpty(newAddress)) {
                            dialog_new_value.setError("Address required...");
                            return;
                        }

                        //branchObject().setBranchName(newBranchName);
                        userRef.child("address").setValue(newAddress);

                        Toast.makeText(getApplicationContext(), "Address Changed!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(BranchInfo.this, BranchInfo.class));
                        finish();

                    }
                });

                dialog.show();

            }
        });

        editBranchPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(BranchInfo.this);
                dialog.setContentView(R.layout.dialog_enter_string);

                //Get Views
                dialog_header = dialog.findViewById(R.id.entry_dialog_header);
                dialog_new_value = dialog.findViewById(R.id.entry_dialog_string);
                submit_button = dialog.findViewById(R.id.entry_dialog_submit);
                cancel_button = dialog.findViewById(R.id.entry_dialog_cancel);

                //Set text
                dialog_header.setText("Please enter the new Branch Phone Number...");
                dialog_new_value.setHint("Branch Phone Number");

                //Listeners
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                submit_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newNumber = dialog_new_value.getText().toString();

                        //Form validation
                        if (TextUtils.isEmpty(newNumber)) {
                            dialog_new_value.setError("Phone Number required...");
                            return;
                        }

                        //branchObject().setBranchName(newBranchName);
                        userRef.child("phoneNumber").setValue(newNumber);

                        Toast.makeText(getApplicationContext(), "Phone Number Changed!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(BranchInfo.this, BranchInfo.class));
                        finish();

                    }
                });

                dialog.show();

            }
        });


    }

}
