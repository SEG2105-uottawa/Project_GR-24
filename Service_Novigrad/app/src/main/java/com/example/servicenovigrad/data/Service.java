package com.example.servicenovigrad.data;

import java.util.HashMap;

public class Service {

    private String name;
    private HashMap<String, String> form;
    private HashMap<String, Object> documents;

    public Service(String name){
        this.name = name;
        form = new HashMap<>();
        documents = new HashMap<>();
        }

    public void createFormField(String field){
        form.put(field,null);
    }

    public void removeFormField(String field){
        form.remove(field);
    }

    public void createDocType(String type){
        documents.put(type,null);
    }

    public void removeDocType(String type){
        documents.remove(type);
    }

    public void setFormValue(String field, String value){
        if (!form.containsKey(field)){
            throw new IllegalArgumentException("Field " + field + "does not exist in a " + this.name + " service");
        }
        else form.put(field,value);
    }

    public void setDocValue(String type, Object value){
        if (!documents.containsKey(type)){
            throw new IllegalArgumentException("Document type " + type + "does not exist in a " + this.name + " service");
        }
        else documents.put(type,value);
    }

    public HashMap<String, String> getForm(){
        return form;
    }

    public HashMap<String, Object> getDocuments() {
        return documents;
    }

    public String getName() {
        return name;
    }
}

