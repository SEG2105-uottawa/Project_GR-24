package com.example.servicenovigrad.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

public enum Services {

    DRIVERS_LICENSE("Driver's License",
        new Form(new ArrayList<>(Arrays.asList(
            FormFields.FIRST_NAME, FormFields.LAST_NAME, FormFields.DATE_OF_BIRTH,
            FormFields.ADDRESS, FormFields.LICENSE_TYPE))),
        new ArrayList<>(Arrays.asList(Documents.PROOF_OF_RESIDENCE))),

    HEALTH_CARD("Health Card",
        new Form(new ArrayList<>(Arrays.asList(
            FormFields.FIRST_NAME, FormFields.LAST_NAME, FormFields.DATE_OF_BIRTH,
            FormFields.ADDRESS))),
        new ArrayList<>(Arrays.asList(Documents.PROOF_OF_RESIDENCE, Documents.PROOF_OF_STATUS))),

    PHOTO_ID("Photo I.D.",
        new Form(new ArrayList<>(Arrays.asList(
                FormFields.FIRST_NAME, FormFields.LAST_NAME, FormFields.DATE_OF_BIRTH,
                FormFields.ADDRESS))),
        new ArrayList<>(Arrays.asList(Documents.PROOF_OF_RESIDENCE, Documents.PHOTO)));

    String string;
    ArrayList<Documents> documents;
    Form form;

    Services(String string, Form form, ArrayList<Documents> documents){
        this.string = string;
        this.documents = documents;
        this.form = form;
    }

    @NonNull
    @Override
    public String toString(){
        return string;
    }

    public ArrayList<Documents> getDocuments() {
        return documents;
    }
}
