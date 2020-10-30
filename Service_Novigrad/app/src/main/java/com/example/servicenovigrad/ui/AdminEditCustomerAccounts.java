package com.example.servicenovigrad.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.users.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminEditCustomerAccounts extends AppCompatActivity {

    ListView admin_customer_list;
    Button admin_delete_customer_btn, admin_customer_cancel_btn,
                confirm_yes_btn, confirm_no_btn;
    TextView edit_customer_customerName,
                edit_customer_userName, are_you_sure;
    Dialog dialog, confirmDelete;
    DatabaseReference allUsersReference;
    ArrayList<Customer> allCustomers;
    ArrayAdapter<Customer> arrayAdapter;

    //This HashMap lets you get the database ID of a user
    HashMap<Customer, String> customerIDs;

    //Used when dealing with a specific customer
    Customer currCustomer;
    String currCustomerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_customer_accounts);
        allUsersReference = FirebaseDatabase.getInstance().getReference().child("users");
        admin_customer_list = findViewById(R.id.admin_customer_list);

        allUsersReference.addValueEventListener(new ValueEventListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Clear local data after a deletion
                allCustomers = new ArrayList<>();
                customerIDs = new HashMap<>();

                //Get JSON tree of all users (Firebase sends it as nested HashMaps)
                HashMap<String,Object> allUsers = (HashMap<String,Object>) snapshot.getValue();

                //Loop through outer HashMap (user IDs)
                for (Map.Entry<String, Object> user : allUsers.entrySet()){
                    //Convert each value to a HashMap of strings (this is the user object)
                    HashMap<String, String> userData = (HashMap<String,String>) user.getValue();
                    //Find the customers and add them to both collections
                    if (userData.get("role").equals("CUSTOMER")){
                        Customer customer = new Customer(
                                userData.get("firstName"), userData.get("lastName"),
                                userData.get("userName"));
                        allCustomers.add(customer);
                        customerIDs.put(customer, user.getKey());
                    }
                }
                //Put the data into the list view
                arrayAdapter = new ArrayAdapter<>(AdminEditCustomerAccounts.this,
                        android.R.layout.simple_expandable_list_item_1, allCustomers);
                admin_customer_list.setAdapter(arrayAdapter);

                //Set on click listener for all items
                admin_customer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        currCustomer = (Customer) admin_customer_list.getItemAtPosition(position);
                        currCustomerID = customerIDs.get(currCustomer);
                        openDialog();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminEditCustomerAccounts.this, "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openDialog(){
        //Prevent hidden dialogs from stacking
        if (dialog != null) dialog.dismiss();
        //Display dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_admin_edit_customer);

        //Get views
        edit_customer_customerName = dialog.findViewById(R.id.edit_customer_customerName);
        edit_customer_userName = dialog.findViewById(R.id.edit_customer_userName);
        admin_delete_customer_btn = dialog.findViewById(R.id.admin_delete_customer_btn);
        admin_customer_cancel_btn = dialog.findViewById(R.id.admin_customer_cancel_btn);

        //Set text
        edit_customer_customerName.setText(currCustomer.getFullName());
        edit_customer_userName.setText(currCustomer.getUserName());

        //Listeners
        //Delete button
        admin_delete_customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfirmation();
            }
        });
        //Cancel button
        admin_customer_cancel_btn.setOnClickListener(new View.OnClickListener() {
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
        are_you_sure.setText(getString(R.string.are_you_sure_customer, currCustomer.getFullName()));

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
        allUsersReference.child(currCustomerID).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null){
                    confirmDelete.dismiss();
                    dialog.dismiss();
                    Toast.makeText(AdminEditCustomerAccounts.this,
                            getString(R.string.customer_deleted, currCustomer.getFullName()),
                            Toast.LENGTH_SHORT).show();
                    currCustomer = null;
                    currCustomerID = null;
                }
                else{
                    Toast.makeText(AdminEditCustomerAccounts.this, "Error deleting user...", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", error.toString());
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