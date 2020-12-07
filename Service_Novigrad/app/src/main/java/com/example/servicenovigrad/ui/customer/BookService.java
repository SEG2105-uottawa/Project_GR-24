package com.example.servicenovigrad.ui.customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import com.example.servicenovigrad.ui.homepages.CustomerHomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BookService extends SearchPage {

    private final static int IMAGE_PICK_CODE = 1000;
    private final static int PERMISSION_CODE = 1001;

    TextView header, subheader, dialog_header;
    Button submit, dialog_submit, dialog_cancel, dialog_upload;
    ArrayList<String> allForms, allDocs;
    ArrayAdapter<String> formsAdapter, docsAdapter;
    ListView formList, docList;
    Dialog dialog;
    EditText dialog_input;
    ImageView dialog_image;
    Uri imageUri;
    boolean imageUploaded = false;
    LinkedHashMap<String,Uri> uriMap;
    HashMap<String, Uri> uploadedImages;

    HashMap <Integer, String> formFields;
    HashMap <Integer, String> docTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);

        uriMap = new LinkedHashMap<>();
        uploadedImages = new HashMap<>();

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check that everything is filled
                boolean complete = true;
                for (Map.Entry<String, String> formField : request.getFormFields().entrySet()){
                    if (formField.getValue().equals(Service.EMPTY)) complete = false;
                }
                for (Map.Entry<String, Object> docType : request.getDocumentTypes().entrySet()){
                    if (docType.getValue().equals(Service.EMPTY)) complete = false;
                }
                if (!complete){
                    Toast.makeText(getApplicationContext(), "Not all fields are complete...\nYou may have to scroll down...", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Map.Entry<String,Uri> uriEntry : uriMap.entrySet()){
                    StorageReference ref = storageRef.child(uriEntry.getKey());
                    ref.putFile(uriEntry.getValue()).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error uploading images...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                }
                branchRef.child("serviceRequests").child(request.getRequestID())
                        .setValue(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Request Submitted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BookService.this, RateBranch.class));
                    }
                });
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
            String formString = formField.getKey() + ": " + formField.getValue();
            allForms.add(formString);
            formFields.put(i++, formField.getKey());
        }
        i = 0;
        for (Map.Entry<String, Object> docType : request.getDocumentTypes().entrySet()){
            String docString = docType.getKey() + ": " + (docType.getValue().equals(Service.EMPTY) ?
                    Service.EMPTY : Service.UPLOADED);
            allDocs.add(docString);
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

        //Set text & image
        dialog_header.setText(docType);
        if (uploadedImages.containsKey(docType) && uploadedImages.get(docType) != null){
            dialog_image.setImageURI(uploadedImages.get(docType));
        };

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        pickImageFromGallery();
                    }
                }
                else {
                    pickImageFromGallery();
                }
            }
        });

        dialog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null){
                    String ext = getExtension(imageUri);
                    String path = createPath(docType, ext);
                    request.addDocument(docType, path);
                    uriMap.put(path, imageUri);
                    uploadedImages.put(docType, imageUri);
                    imageUri = null;
                    updateLists();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please upload an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data.getData();
            dialog_image.setImageURI(imageUri);
            imageUploaded = true;
        }
    }

    protected String getExtension(Uri imageUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(imageUri));
    }

    private String createPath(String docType, String imageExt){
        return request.getRequestID() + "/" + docType + "." + imageExt;
    }
}