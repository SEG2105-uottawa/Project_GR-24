package com.example.servicenovigrad.data;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//Serializable interface allows objects to be passed between activities
public class Service implements Serializable {

    public static final String EMPTY = "<EMPTY>";
    public static final String UPLOADED = "<UPLOADED>";

    private String name;
    private double price;
    protected HashMap<String,String> formFields;
    protected HashMap<String,Object> documentTypes;

    public Service(String name, double price){
        this.name = name;
        this.price = price;
        formFields = new HashMap<>();
        documentTypes = new HashMap<>();
    }

    public Service(Service service){
        name = service.getName();
        price = service.returnPrice();
        formFields = service.getFormFields();
        documentTypes = service.getDocumentTypes();
        for (Map.Entry<String,String> formField : formFields.entrySet()){
            formFields.put(formField.getKey(), EMPTY);
        }
        for (Map.Entry<String,Object> docType : documentTypes.entrySet()){
            documentTypes.put(docType.getKey(), EMPTY);
        }
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

    //For firebase
    public String getPrice() {
        return Double.toString(price);
    }

    //For normal use
    public double returnPrice(){
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return (name + "\nPrice: $" + String.format("%.2f", price));
    }
}

