package com.example.servicenovigrad.data;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.LinkedList;

public enum Services {
    DRIVERS_LICENSE("Driver's License",
        new LinkedList<>(Arrays.asList(Documents.PROOF_OF_RESIDENCE))),
    HEALTH_CARD("Health Card",
        new LinkedList<>(Arrays.asList(Documents.PROOF_OF_RESIDENCE, Documents.PROOF_OF_STATUS))),
    PHOTO_ID("Photo I.D.",
        new LinkedList<>(Arrays.asList(Documents.PROOF_OF_RESIDENCE, Documents.PHOTO)));

    String string;
    LinkedList<Documents> documents;

    Services(String string, LinkedList<Documents> documents){
        this.string = string;
        this.documents = documents;
    }

    @NonNull
    @Override
    public String toString(){
        return string;
    }

    public LinkedList<Documents> getDocuments() {
        return documents;
    }
}
