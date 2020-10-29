package com.example.servicenovigrad.data;

import androidx.annotation.NonNull;

public enum Documents {
    PROOF_OF_RESIDENCE("Proof of residence", "(An image of a bank statement or hydro bill containing address)"),
    PROOF_OF_STATUS("Proof of status", "(An image of a Canadian permanent resident card or a Canadian passport)"),
    PHOTO("A photo of the customer", "");

    String name, addInfo;

    Documents(String name, String addInfo){
        this.name = name;
        this.addInfo = addInfo;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public String getAddInfo(){
        return addInfo;
    }
}
