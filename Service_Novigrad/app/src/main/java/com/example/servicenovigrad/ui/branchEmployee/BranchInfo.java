package com.example.servicenovigrad.ui.branchEmployee;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.WorkingHours;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.homepages.BranchEmployeeHomePage;
import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.connection.ListenHashProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BranchInfo extends UserPage {

    TextView branchName, branchAddress, branchPhoneNumber;
    TextView sundayOpen, sundayClose, mondayOpen, mondayClose, tuesdayOpen, tuesdayClose,
        wednesdayOpen, wednesdayClose, thursdayOpen, thursdayClose, fridayOpen,
        fridayClose, saturdayOpen, saturdayClose;
    TextView newSundayOpen, newSundayClose, newMondayOpen, newMondayClose, newTuesdayOpen, newTuesdayClose,
            newWednesdayOpen, newWednesdayClose, newThursdayOpen, newThursdayClose, newFridayOpen,
            newFridayClose, newSaturdayOpen, newSaturdayClose;
    Button editBranchName, editBranchAddress, editBranchPhoneNumber, submit_button, cancel_button, editBranchWorkingHours;
    Dialog dialog;
    TextView dialog_header;
    EditText dialog_new_value;
    ArrayList<WorkingHours> workingHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_info);

        // Get Views
        branchName = findViewById(R.id.branch_name);
        branchAddress = findViewById(R.id.branch_address);
        branchPhoneNumber = findViewById(R.id.branch_phone_number);

        editBranchName = findViewById(R.id.edit_branch_name);
        editBranchAddress = findViewById(R.id.edit_branch_address);
        editBranchPhoneNumber = findViewById(R.id.edit_branch_phone_number);
        editBranchWorkingHours = findViewById(R.id.edit_branch_working_hours);

        // Set Texts
        branchName.setText(branchObject().getBranchName());
        branchAddress.setText(branchObject().getAddress() == null ?
                "Enter address here" : branchObject().getAddress());
        branchPhoneNumber.setText(branchObject().getPhoneNumber() == null ?
                "Enter phone number here" : branchObject().getPhoneNumber());
        updateBranchHours();

        // Listeners
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

                        if (!(newNumber.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$"))) {
                            dialog_new_value.setError("Format: (123) 456 7890");
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

        editBranchWorkingHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(BranchInfo.this);
                dialog.setContentView(R.layout.dialog_edit_working_hours);

                // Get Views
                submit_button = dialog.findViewById(R.id.entry_dialog_submit);
                cancel_button = dialog.findViewById(R.id.entry_dialog_cancel);

                newSundayOpen = dialog.findViewById(R.id.new_sunday_open);
                newSundayClose = dialog.findViewById(R.id.new_sunday_close);

                newMondayOpen = dialog.findViewById(R.id.new_monday_open);
                newMondayClose = dialog.findViewById(R.id.new_monday_close);

                newTuesdayOpen = dialog.findViewById(R.id.new_tuesday_open);
                newTuesdayClose = dialog.findViewById(R.id.new_tuesday_close);

                newWednesdayOpen = dialog.findViewById(R.id.new_wednesday_open);
                newWednesdayClose = dialog.findViewById(R.id.new_wednesday_close);

                newThursdayOpen = dialog.findViewById(R.id.new_thursday_open);
                newThursdayClose = dialog.findViewById(R.id.new_thursday_close);

                newFridayOpen = dialog.findViewById(R.id.new_friday_open);
                newFridayClose = dialog.findViewById(R.id.new_friday_close);

                newSaturdayOpen = dialog.findViewById(R.id.new_saturday_open);
                newSaturdayClose = dialog.findViewById(R.id.new_saturday_close);

                // Listeners
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                submit_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference workingHoursRef = databaseRef.child("users").child(curUser.getUid()).child("hours");

                        if (!(isValidTime(newSundayOpen)) || !(isValidTime(newSundayClose))
                                || !(isValidTime(newMondayOpen)) ||!(isValidTime(newMondayClose))
                                || !(isValidTime(newTuesdayOpen)) ||!(isValidTime(newTuesdayClose))
                                || !(isValidTime(newWednesdayOpen)) ||!(isValidTime(newWednesdayClose))
                                || !(isValidTime(newThursdayOpen)) ||!(isValidTime(newThursdayClose))
                                || !(isValidTime(newFridayOpen)) ||!(isValidTime(newFridayClose))
                                || !(isValidTime(newSaturdayOpen)) ||!(isValidTime(newSaturdayClose))) {
                            return;
                        }


                        workingHoursRef.child("0").child("openTime").setValue(newSundayOpen.getText().toString());
                        workingHoursRef.child("0").child("closeTime").setValue(newSundayClose.getText().toString());

                        workingHoursRef.child("1").child("openTime").setValue(newMondayOpen.getText().toString());
                        workingHoursRef.child("1").child("closeTime").setValue(newMondayClose.getText().toString());

                        workingHoursRef.child("2").child("openTime").setValue(newTuesdayOpen.getText().toString());
                        workingHoursRef.child("2").child("closeTime").setValue(newTuesdayClose.getText().toString());

                        workingHoursRef.child("3").child("openTime").setValue(newWednesdayOpen.getText().toString());
                        workingHoursRef.child("3").child("closeTime").setValue(newWednesdayClose.getText().toString());

                        workingHoursRef.child("4").child("openTime").setValue(newThursdayOpen.getText().toString());
                        workingHoursRef.child("4").child("closeTime").setValue(newThursdayClose.getText().toString());

                        workingHoursRef.child("5").child("openTime").setValue(newFridayOpen.getText().toString());
                        workingHoursRef.child("5").child("closeTime").setValue(newFridayClose.getText().toString());

                        workingHoursRef.child("6").child("openTime").setValue(newSaturdayOpen.getText().toString());
                        workingHoursRef.child("6").child("closeTime").setValue(newSaturdayClose.getText().toString());

                        Toast.makeText(getApplicationContext(), "Hours updated!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(BranchInfo.this, BranchInfo.class));
                        finish();
                    }
                });

                dialog.show();

            }
        });


    }

    protected void updateBranchHours() {
        workingHours = branchObject().getHours();

        // Get Working Hours Views
        sundayOpen = findViewById(R.id.sunday_open);
        sundayClose = findViewById(R.id.sunday_close);

        mondayOpen = findViewById(R.id.monday_open);
        mondayClose = findViewById(R.id.monday_close);

        tuesdayOpen = findViewById(R.id.tuesday_open);
        tuesdayClose = findViewById(R.id.tuesday_close);

        wednesdayOpen = findViewById(R.id.wednesday_open);
        wednesdayClose = findViewById(R.id.wednesday_close);

        thursdayOpen = findViewById(R.id.thursday_open);
        thursdayClose = findViewById(R.id.thursday_close);

        fridayOpen = findViewById(R.id.friday_open);
        fridayClose = findViewById(R.id.friday_close);

        saturdayOpen = findViewById(R.id.saturday_open);
        saturdayClose = findViewById(R.id.saturday_close);


        sundayOpen.setText(workingHours.get(0).getOpenTime());
        sundayClose.setText(workingHours.get(0).getCloseTime());

        mondayOpen.setText(workingHours.get(1).getOpenTime());
        mondayClose.setText(workingHours.get(1).getCloseTime());

        tuesdayOpen.setText(workingHours.get(2).getOpenTime());
        tuesdayClose.setText(workingHours.get(2).getCloseTime());

        wednesdayOpen.setText(workingHours.get(3).getOpenTime());
        wednesdayClose.setText(workingHours.get(3).getCloseTime());

        thursdayOpen.setText(workingHours.get(4).getOpenTime());
        thursdayClose.setText(workingHours.get(4).getCloseTime());

        fridayOpen.setText(workingHours.get(5).getOpenTime());
        fridayClose.setText(workingHours.get(5).getCloseTime());

        saturdayOpen.setText(workingHours.get(6).getOpenTime());
        saturdayClose.setText(workingHours.get(6).getCloseTime());

    }

    protected boolean isValidTime(TextView newTime) {
        if (TextUtils.isEmpty(newTime.getText().toString())
                                || !(newTime.getText().toString().matches("(((0[1-9])|(1[0-2])):([0-5])(0|5)\\s(A|P|a|p)(M|m))"))) {
            newTime.setError("Example Format: 01:05 PM");
            return false;
        }

        return true;
    }

}
