package com.example.servicenovigrad.ui.branchEmployee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.data.ServiceRequest;
import com.example.servicenovigrad.ui.UserPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProcessRequest extends UserPage {
    TextView header, date, dialog_header;
    Button approve, deny, dialog_dismiss;
    ServiceRequest request;
    ArrayList<String> allForms, allDocs;
    ArrayAdapter<String> formsAdapter, docsAdapter;
    ListView formList, docList;
    Dialog dialog;
    ImageView dialog_image;
    HashMap<Integer, String> docTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_request);

        request = (ServiceRequest) getIntent().getSerializableExtra("serviceRequest");
        allForms = new ArrayList<>();
        allDocs = new ArrayList<>();
        docTypes = new HashMap<>();

        //get views
        header = findViewById(R.id.process_request_header);
        date = findViewById(R.id.process_request_date);
        approve = findViewById(R.id.process_request_approve);
        deny = findViewById(R.id.process_request_deny);
        formList = findViewById(R.id.process_request_form_list);
        docList = findViewById(R.id.process_request_doc_list);

        //set text
        header.setText(request.getServiceName());
        date.setText(request.getDateCreated());

        //populate lists
        for (Map.Entry<String, String> formField : request.getFormFields().entrySet()){
            String string = formField.getKey() + ": " + formField.getValue();
            allForms.add(string);
        }
        int i = 0;
        for (Map.Entry<String, Object> docType : request.getDocumentTypes().entrySet()){
            String string = docType.getKey() + ": " + (docType.getValue().equals(Service.EMPTY) ?
                    Service.EMPTY : "<CLICK TO SEE>");
            allDocs.add(string);
            docTypes.put(i++, docType.getKey());
        }

        formsAdapter = new ArrayAdapter<>(ProcessRequest.this, android.R.layout.simple_expandable_list_item_1, allForms);
        formList.setAdapter(formsAdapter);

        docsAdapter = new ArrayAdapter<>(ProcessRequest.this, android.R.layout.simple_expandable_list_item_1, allDocs);
        docList.setAdapter(docsAdapter);

        docList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openImageDialog(position);
            }
        });

        //Listeners
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.child("serviceRequests").child(request.getRequestID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successfully approved request.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable to approve request...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.child("serviceRequests").child(request.getRequestID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successfully denied request.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable to deny request...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void openImageDialog(int position){
        dialog = new Dialog(ProcessRequest.this);
        dialog.setContentView(R.layout.dialog_view_image);

        //Get views
        dialog_header = dialog.findViewById(R.id.view_image_header);
        dialog_dismiss = dialog.findViewById(R.id.view_image_dismiss);
        dialog_image = dialog.findViewById(R.id.view_image_image);

        //Set text
        String docName = docTypes.get(position).toString();
        dialog_header.setText(docName);

        dialog_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        String path = request.getDocumentTypes().get(docName).toString();
        StorageReference ref = storageRef.child(path);
        String ext = "";
        int i = path.lastIndexOf('.');
        if (i > 0) {
            ext = path.substring(i);
        }
        try{
            final File tempFile = File.createTempFile(docName, ext);
            ref.getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    dialog_image.setImageURI(Uri.fromFile(tempFile));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error downloading image...", Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error downloading image...", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        dialog.show();
    }
}