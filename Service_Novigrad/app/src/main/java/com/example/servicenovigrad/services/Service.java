package com.example.servicenovigrad.services;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;

//Serializable interface allows objects to be passed between activities
public class Service implements Serializable {

    private static final String EMPTY = "<EMPTY>";
    private String name;
    private HashMap<String,String> formFields;
    private HashMap<String,Object> documentTypes;

    public Service(String name){
        this.name = name;
        formFields = new HashMap<>();
        documentTypes = new HashMap<>();

    }

    public void addFormField(String field){
        formFields.put(field,EMPTY);
    }

    public void removeFormField(String field){
        formFields.remove(field);
    }

    public void addDocType(String type){
        documentTypes.put(type,EMPTY);
    }

    public void removeDocType(String type){
        documentTypes.remove(type);
    }

    public HashMap<String, String> getFormFields() {
        return formFields;
    }

    public HashMap<String, Object> getDocumentTypes() {
        return documentTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @NonNull
    @Override
    public String toString() {
        return (name + "\nForm fields: " + formFields.size() + "\nDocuments: " + documentTypes.size());
    }
}

