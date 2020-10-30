package com.example.servicenovigrad.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.services.Service;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminEditService extends AppCompatActivity {

    Service service;
    TextView header;
    ListView reqFormsList, reqDocsList;
    Button delServiceBtn, addFormBtn, addDocBtn, delDialogBtn, cancelDialogBtn, submitDialogBtn;
    DatabaseReference databaseServiceReference;
    ArrayList<String> allForms, allDocs;
    ArrayAdapter<String> formsAdapter, docsAdapter;
    Dialog dialog;
    TextView dialog_header;
    EditText dialog_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_service);

        service = (Service) getIntent().getSerializableExtra("service");

        //Get views
        header = findViewById(R.id.admin_edit_service_header);
        reqFormsList = findViewById(R.id.admin_form_field_list);
        reqDocsList = findViewById(R.id.admin_doc_type_list);
        delServiceBtn = findViewById(R.id.admin_del_service);
        addFormBtn = findViewById(R.id.admin_add_form_field);
        addDocBtn = findViewById(R.id.admin_add_doc_type);

        databaseServiceReference = FirebaseDatabase.getInstance().getReference().child("services").child(service.getName());

        //Set text
        header.setText(service.getName());

        //Populate Lists
        allForms = new ArrayList<>();
        allDocs = new ArrayList<>();

        final HashMap<String, String> formFields = service.getFormFields();
        HashMap<String, Object> docFields = service.getDocumentTypes();

        if (formFields != null) {
            for (Map.Entry<String, String> formField: formFields.entrySet()) {
                allForms.add(formField.getKey());
            }

            formsAdapter = new ArrayAdapter<>(AdminEditService.this, android.R.layout.simple_expandable_list_item_1, allForms);
            reqFormsList.setAdapter(formsAdapter);
        }

        if (docFields != null) {
            for (Map.Entry<String, Object> docField: docFields.entrySet()) {
                allDocs.add(docField.getKey());
            }

            docsAdapter = new ArrayAdapter<>(AdminEditService.this, android.R.layout.simple_expandable_list_item_1, allDocs);
            reqDocsList.setAdapter(docsAdapter);
        }

        //Listeners
        delServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(AdminEditService.this);
                dialog.setContentView(R.layout.dialog_confirm_deletion);

                //Get Views
                dialog_header = dialog.findViewById(R.id.conf_dialog_header);
                delDialogBtn = dialog.findViewById(R.id.conf_dialog_delete);
                cancelDialogBtn = dialog.findViewById(R.id.conf_dialog_cancel);

                dialog_header.setText("Are you sure you want to delete this service?");

                //Listeners
                cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                delDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseServiceReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(AdminEditService.this, "Successfully deleted service.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AdminEditService.this, AdminEditAllServices.class));
                                }
                                else {
                                    Toast.makeText(AdminEditService.this, "Unable to delete service...", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AdminEditService.this, AdminEditAllServices.class));
                                }
                            }
                        });
                    }
                });

                dialog.show();
            }
        });

        addFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(AdminEditService.this);
                dialog.setContentView(R.layout.dialog_enter_string);

                //get Views
                dialog_header = dialog.findViewById(R.id.entry_dialog_header);
                dialog_input = dialog.findViewById(R.id.entry_dialog_string);
                submitDialogBtn = dialog.findViewById(R.id.entry_dialog_submit);
                cancelDialogBtn = dialog.findViewById(R.id.entry_dialog_cancel);

                //Set text
                dialog_header.setText("Please enter the name of the form you wish to add...");

                //Listeners
                cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                submitDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String formName = dialog_input.getText().toString().trim();

                        if (TextUtils.isEmpty(formName)) {
                            dialog_input.setError("Form is required...");
                            return;
                        }

                        databaseServiceReference.child("formFields").child(formName).setValue("<EMPTY>");
                        service.addFormField(formName);
                        Toast.makeText(getApplicationContext(), "New form added to service", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        Intent intent = new Intent(AdminEditService.this, AdminEditService.class);
                        intent.putExtra("service", service);
                        startActivity(intent);
                        finish();
                    }
                });

                dialog.show();
            }
        });

        addDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(AdminEditService.this);
                dialog.setContentView(R.layout.dialog_enter_string);

                //get Views
                dialog_header = dialog.findViewById(R.id.entry_dialog_header);
                dialog_input = dialog.findViewById(R.id.entry_dialog_string);
                submitDialogBtn = dialog.findViewById(R.id.entry_dialog_submit);
                cancelDialogBtn = dialog.findViewById(R.id.entry_dialog_cancel);

                //Set text
                dialog_header.setText("Please enter the name of the document you wish to add...");

                //Listeners
                cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                submitDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String docName = dialog_input.getText().toString().trim();

                        if (TextUtils.isEmpty(docName)) {
                            dialog_input.setError("Document is required...");
                            return;
                        }

                        databaseServiceReference.child("documentTypes").child(docName).setValue("<EMPTY>");
                        service.addDocType(docName);
                        Toast.makeText(getApplicationContext(), "New document added to service", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent intent = new Intent(AdminEditService.this, AdminEditService.class);
                        intent.putExtra("service", service);
                        startActivity(intent);
                        finish();
                    }
                });

                dialog.show();
            }
        });

        reqFormsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                dialog = new Dialog(AdminEditService.this);
                dialog.setContentView(R.layout.dialog_confirm_deletion);

                //Get Views
                dialog_header = dialog.findViewById(R.id.conf_dialog_header);
                delDialogBtn = dialog.findViewById(R.id.conf_dialog_delete);
                cancelDialogBtn = dialog.findViewById(R.id.conf_dialog_cancel);

                dialog_header.setText(reqFormsList.getItemAtPosition(position).toString());

                //Listeners
                cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                delDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseServiceReference.child("formFields").child(reqFormsList.getItemAtPosition(position).toString()).removeValue();
                        service.removeFormField(reqFormsList.getItemAtPosition(position).toString());
                        Toast.makeText(AdminEditService.this, "Successfully deleted form.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent intent = new Intent(AdminEditService.this, AdminEditService.class);
                        intent.putExtra("service", service);
                        startActivity(intent);
                        finish();

                    }
                });

                dialog.show();
            }
        });

        reqDocsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                dialog = new Dialog(AdminEditService.this);
                dialog.setContentView(R.layout.dialog_confirm_deletion);

                //Get Views
                dialog_header = dialog.findViewById(R.id.conf_dialog_header);
                delDialogBtn = dialog.findViewById(R.id.conf_dialog_delete);
                cancelDialogBtn = dialog.findViewById(R.id.conf_dialog_cancel);

                dialog_header.setText(reqDocsList.getItemAtPosition(position).toString());

                //Listeners
                cancelDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                delDialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseServiceReference.child("documentTypes").child(reqDocsList.getItemAtPosition(position).toString()).removeValue();
                        service.removeDocType(reqDocsList.getItemAtPosition(position).toString());
                        Toast.makeText(AdminEditService.this, "Successfully deleted document.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent intent = new Intent(AdminEditService.this, AdminEditService.class);
                        intent.putExtra("service", service);
                        startActivity(intent);
                        finish();
                    }
                });

                dialog.show();
            }
        });

    }
}