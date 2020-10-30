package com.example.servicenovigrad.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.servicenovigrad.R;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.servicenovigrad.users.BranchEmployee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminEditBranchAccounts extends AppCompatActivity {

    ListView admin_branch_list;
    Button admin_delete_branch_btn, admin_edit_cancel_btn,
                confirm_yes_btn, confirm_no_btn;
    TextView edit_branch_branchName, edit_branch_employeeName,
                edit_branch_userName, are_you_sure;
    Dialog dialog, confirmDelete;
    DatabaseReference allUsersReference;
    ArrayList<BranchEmployee> allBranchEmployees;
    ArrayAdapter<BranchEmployee> arrayAdapter;

    //This HashMap lets you get the database ID of a user
    HashMap<BranchEmployee, String> employeeIDs;

    //Used when dealing with a specific employee
    BranchEmployee currBranchEmployee;
    String currEmployeeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_branch_accounts);

        allUsersReference = FirebaseDatabase.getInstance().getReference().child("users");
        admin_branch_list = findViewById(R.id.admin_branch_list);

        allUsersReference.addValueEventListener(new ValueEventListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allBranchEmployees = new ArrayList<>();
                employeeIDs = new HashMap<>();

                //Get JSON tree of all users (Firebase sends it as nested HashMaps)
                HashMap<String,Object> allUsers = (HashMap<String,Object>) snapshot.getValue();

                //Loop through outer HashMap (user IDs)
                for (Map.Entry<String, Object> user : allUsers.entrySet()){
                    //Convert each value to a HashMap of strings (this is the user object)
                    HashMap<String, String> userData = (HashMap<String,String>) user.getValue();
                    //Find the branch employees and add them to both collections
                    if (userData.get("role").equals("BRANCH_EMPLOYEE")){
                        BranchEmployee branchEmployee = new BranchEmployee(
                            userData.get("firstName"), userData.get("lastName"),
                            userData.get("userName"), userData.get("branchName"));
                        allBranchEmployees.add(branchEmployee);
                        employeeIDs.put(branchEmployee, user.getKey());
                    }
                }
                //Put the data into the list view
                arrayAdapter = new ArrayAdapter<>(AdminEditBranchAccounts.this,
                    android.R.layout.simple_expandable_list_item_1, allBranchEmployees);
                admin_branch_list.setAdapter(arrayAdapter);

                //Set on click listener for all items
                admin_branch_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        currBranchEmployee = (BranchEmployee) admin_branch_list.getItemAtPosition(position);
                        currEmployeeID = employeeIDs.get(currBranchEmployee);
                        openDialog();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminEditBranchAccounts.this, "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openDialog(){
        //Prevent hidden dialogs from stacking
        if (dialog != null) dialog.dismiss();
        //Display dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_admin_edit_branch);

        //Get views
        edit_branch_branchName = dialog.findViewById(R.id.edit_branch_branchName);
        edit_branch_employeeName = dialog.findViewById(R.id.edit_branch_employeeName);
        edit_branch_userName = dialog.findViewById(R.id.edit_branch_userName);
        admin_delete_branch_btn = dialog.findViewById(R.id.admin_delete_branch_btn);
        admin_edit_cancel_btn = dialog.findViewById(R.id.admin_edit_cancel_btn);

        //Set text
        edit_branch_branchName.setText(currBranchEmployee.getBranchName());
        edit_branch_employeeName.setText(currBranchEmployee.getFullName());
        edit_branch_userName.setText(currBranchEmployee.getUserName());

        //Listeners
        //Delete button
        admin_delete_branch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfirmation();
            }
        });
        //Cancel button
        admin_edit_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void openConfirmation(){
        //Open confirmation dialog
        confirmDelete = new Dialog(this);
        confirmDelete.setContentView(R.layout.activity_admin_confirm_delete);

        //Get views
        are_you_sure = confirmDelete.findViewById(R.id.are_you_sure);
        confirm_yes_btn = confirmDelete.findViewById(R.id.confirm_yes_btn);
        confirm_no_btn = confirmDelete.findViewById(R.id.confirm_no_btn);
        //Set text
        are_you_sure.setText(getString(R.string.are_you_sure_branch, currBranchEmployee.getBranchName()));

        //Listeners
        //yes button
        confirm_yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
        //no button
        confirm_no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete.dismiss();
                dialog.show();
            }
        });
        dialog.hide();
        confirmDelete.show();
    }

    public void deleteUser(){
        //Firebase calls OnDataChange when a value is deleted
        //So the ListView will auto update
        allUsersReference.child(currEmployeeID).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null){
                    confirmDelete.dismiss();
                    dialog.dismiss();
                    Toast.makeText(AdminEditBranchAccounts.this,
                        getString(R.string.branch_deleted, currBranchEmployee.getBranchName()),
                        Toast.LENGTH_SHORT).show();
                    currBranchEmployee = null;
                    currEmployeeID = null;
                }
                else{
                    Log.e("TAG", error.toString());
                    Toast.makeText(AdminEditBranchAccounts.this,
                        "Error deleting user...", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //Close any hidden dialogs
    //User can exit confirmation without dismissing dialog
    //By clicking outside of it
    @Override
    protected void onDestroy() {
        if (dialog != null) dialog.dismiss();
        super.onDestroy();
    }
}