package com.example.servicenovigrad.data;

import java.util.HashMap;

/**
 *  Contains two hashMaps (form and documents)
 *  where the keys are set at constructor
 *  and only values can be changed
 */
public class Service {

    private ServiceType type;
    private HashMap<FormField, String> form;
    private HashMap<DocumentType, Object> documents;

    public Service(ServiceType type){
        this.type = type;
        form = new HashMap<>();
        documents = new HashMap<>();
        switch (type){
            case DRIVERS_LICENSE:
                createStandard();
                form.put(FormField.LICENSE_TYPE,null);
                break;
            case HEALTH_CARD:
                createStandard();
                documents.put(DocumentType.PROOF_OF_STATUS,null);
                break;
            case PHOTO_ID:
                createStandard();
                documents.put(DocumentType.PHOTO,null);
                break;
            default:
                throw new IllegalArgumentException("ServiceType " + type + " not recognized");
        }
    }

    private void createStandard(){
        form.put(FormField.FIRST_NAME,null);
        form.put(FormField.LAST_NAME,null);
        form.put(FormField.DATE_OF_BIRTH,null);
        form.put(FormField.ADDRESS,null);
        documents.put(DocumentType.PROOF_OF_RESIDENCE,null);
    }

    public void setFormValue(FormField field, String value){
        if (!form.containsKey(field)){
            throw new IllegalArgumentException("Field " + field + "does not exist in a " + this.type + " service");
        }
        else form.put(field,value);
    }

    public void setDocValue(DocumentType type, Object value){
        if (!documents.containsKey(type)){
            throw new IllegalArgumentException("Document type " + type + "does not exist in a " + this.type + " service");
        }
        else documents.put(type,value);
    }

    public HashMap<FormField, String> getForm(){
        return form;
    }

    public HashMap<DocumentType, Object> getDocuments() {
        return documents;
    }

    public ServiceType getType() {
        return type;
    }
}

