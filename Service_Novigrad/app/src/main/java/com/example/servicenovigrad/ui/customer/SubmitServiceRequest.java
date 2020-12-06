package com.example.servicenovigrad.ui.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.Service;

import java.util.HashMap;

public class SubmitServiceRequest extends AppCompatActivity {

    Button submit;
    TextView serviceName;
    HashMap<String, String> forms;
    HashMap<String, Object> docs;
    EditText inForm, inDoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_service_request);

        Service theService = (Service) getIntent().getSerializableExtra(BookService.EXTRA_SERVICE);


        serviceName = findViewById(R.id.service_name_header);
        submit = findViewById(R.id.submit_request);

        //TODO: get the edit text inputs from the user to use them when constructing a ServiceRequest object

        //TODO: somehow get the document objects from FileExplorer

//        for ( String key: forms){
//
//        }

        serviceName.setText(theService.getName());


        //TODO: show  the required form to the user
        forms = theService.getFormFields();
        docs = theService.getDocumentTypes();
    }

    public void submitServiceRequest(View view) {
        //TODO send the service request to the BranchEmployee
    }

    public void cancelSubmission(View view) {
        finish(); //not sure if this cancels the activity
    }
}