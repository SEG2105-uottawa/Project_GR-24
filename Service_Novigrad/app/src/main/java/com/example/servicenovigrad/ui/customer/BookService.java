package com.example.servicenovigrad.ui.customer;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.data.ServiceRequest;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.admin.AdminEditAllServices;
import com.example.servicenovigrad.ui.admin.AdminEditService;
import com.example.servicenovigrad.ui.customer.SearchPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookService extends SearchPage {
    TextView header, subheader, dialog_header;
    Button submit, dialog_submit, dialog_cancel, dialog_upload;
    ArrayList<String> allForms, allDocs;
    ArrayAdapter<String> formsAdapter, docsAdapter;
    ListView formList, docList;
    Dialog dialog;
    EditText dialog_input;
    ImageView dialog_image;

    HashMap <Integer, String> formFields;
    HashMap <Integer, String> docTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);

        //get views
        header = findViewById(R.id.book_request_header);
        subheader = findViewById(R.id.book_request_subheader);
        submit = findViewById(R.id.book_request_submitBtn);
        formList = findViewById(R.id.book_request_form_list);
        docList = findViewById(R.id.book_request_doc_list);

        //set text
        header.setText("Branch: " + selection.getBranchName());
        subheader.setText("Request: " + request.getName() + "\nPrice: " + request.getPrice());

        //populate lists
        updateLists();

        //Listeners
        formList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openFormDialog(position);
            }
        });

        docList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDocDialog(position);
            }
        });
    }

    private void updateLists(){
        allForms = new ArrayList<>();
        allDocs = new ArrayList<>();
        formFields = new HashMap<>();
        docTypes = new HashMap<>();

        int i = 0;
        for (Map.Entry<String, String> formField : request.getFormFields().entrySet()){
            String string = formField.getKey() + ": " + formField.getValue();
            allForms.add(string);
            formFields.put(i++, formField.getKey());
        }
        i = 0;
        for (Map.Entry<String, Object> docType : request.getDocumentTypes().entrySet()){
            String string = docType.getKey() + ": " + docType.getValue();
            allDocs.add(string);
            docTypes.put(i++, docType.getKey());
        }

        formsAdapter = new ArrayAdapter<>(BookService.this, android.R.layout.simple_expandable_list_item_1, allForms);
        formList.setAdapter(formsAdapter);

        docsAdapter = new ArrayAdapter<>(BookService.this, android.R.layout.simple_expandable_list_item_1, allDocs);
        docList.setAdapter(docsAdapter);
    }

    private void openFormDialog(int position){
        final String formField = formFields.get(position);
        dialog = new Dialog(BookService.this);
        dialog.setContentView(R.layout.dialog_enter_string);

        //Get views
        dialog_header = dialog.findViewById(R.id.entry_dialog_header);
        dialog_input = dialog.findViewById(R.id.entry_dialog_string);
        dialog_submit = dialog.findViewById(R.id.entry_dialog_submit);
        dialog_cancel = dialog.findViewById(R.id.entry_dialog_cancel);

        //Set text
        dialog_header.setText(formField);

        //Listeners
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = dialog_input.getText().toString();

                if (TextUtils.isEmpty(input)){
                    dialog_input.setError(formField + " is required");
                }

                else{
                    request.fillFormField(formField, input);
                    updateLists();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void openDocDialog(int position){
        final String docType = docTypes.get(position);
        dialog = new Dialog(BookService.this);
        dialog.setContentView(R.layout.dialog_enter_image);

        //Get views
        dialog_header = dialog.findViewById(R.id.image_dialog_header);
        dialog_submit = dialog.findViewById(R.id.image_dialog_submit);
        dialog_cancel = dialog.findViewById(R.id.image_dialog_cancel);
        dialog_upload = dialog.findViewById(R.id.image_dialog_upload);
        dialog_image = dialog.findViewById(R.id.image_dialog_image);

        //Set text
        dialog_header.setText(docType);

        //Listeners
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String input = dialog_input.getText().toString();
//
//                if (TextUtils.isEmpty(input)){
//                    dialog_input.setError(formField + " is required");
//                }
//
//                else{
//                    request.fillFormField(formField, input);
//                    updateLists();
//                    dialog.dismiss();
//                }
            }
        });

        dialog.show();
    }
}