package com.example.servicenovigrad.data;

import android.util.Pair;

import androidx.annotation.NonNull;

public enum FormFields {
    FIRST_NAME(Pair.create("First name", "")),
    LAST_NAME(Pair.create("Last name", "")),
    DATE_OF_BIRTH(Pair.create("Date of birth", "")),
    ADDRESS(Pair.create("Address", "")),
    LICENSE_TYPE(Pair.create("License type", ""));

    Pair<String,String> pair;

    FormFields(Pair<String,String> pair){
        this.pair = pair;
    }

    @NonNull
    @Override
    public String toString() {
        return (getField() + ": " + getValue());
    }

    public String getField(){
        return pair.first;
    }

    public String getValue(){
        return pair.second;
    }

}
