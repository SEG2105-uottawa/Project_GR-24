package com.example.servicenovigrad.data;

import java.util.HashSet;

public class Service {

    private String name;
    private HashSet<String> formFields;
    private HashSet<String> documentTypes;

    public Service(String name){
        this.name = name;
        formFields = new HashSet<>();
        documentTypes = new HashSet<>();
        }

    public void createFormField(String field){
        formFields.add(field);
    }

    public void removeFormField(String field){
        formFields.remove(field);
    }

    public void createDocType(String type){
        documentTypes.add(type);
    }

    public void removeDocType(String type){
        documentTypes.remove(type);
    }

    public HashSet<String> getFormFields() {
        return formFields;
    }

    public HashSet<String> getDocumentTypes() {
        return documentTypes;
    }

    public String getName() {
        return name;
    }
}

