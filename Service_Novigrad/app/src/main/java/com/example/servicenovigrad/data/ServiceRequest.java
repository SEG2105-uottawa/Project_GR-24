package com.example.servicenovigrad.data;

import androidx.annotation.NonNull;

public class ServiceRequest extends Service{
    private String requestID;
    private String dateCreated;

    public ServiceRequest(Service service, String dateCreated, String requestID){
        super(service);
        this.dateCreated = dateCreated;
        this.requestID = requestID;
    }

    public void fillFormField(String field, String value){
        if (formFields.containsKey(field)){
            formFields.put(field,value);
        }
        else throw new IllegalArgumentException("Form field " + field + " does not exist in service: " + getName());
    }

    public void addDocument(String docType, Object document){
        if (documentTypes.containsKey(docType)){
            documentTypes.put(docType,document);
        }
        else throw new IllegalArgumentException("Document type " + docType + " does not exist in service: " + getName());
    }

    public String getServiceName(){
        return getName();
    }

    public String getRequestID() {
        return requestID;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    @Override
    @NonNull
    public String toString(){
        return (dateCreated + "\n" + getName());
    }
}
