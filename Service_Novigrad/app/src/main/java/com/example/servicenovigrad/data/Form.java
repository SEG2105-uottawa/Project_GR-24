package com.example.servicenovigrad.data;

import java.util.ArrayList;

public class Form {
    ArrayList<FormFields> formFields;

    public Form(ArrayList<FormFields> formFields){
        this.formFields = formFields;
    }

    public ArrayList<FormFields> getFormFields() {
        return formFields;
    }
}
