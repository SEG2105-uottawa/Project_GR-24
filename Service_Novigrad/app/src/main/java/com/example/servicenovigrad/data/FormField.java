package com.example.servicenovigrad.data;

import androidx.annotation.NonNull;

public enum FormField {FIRST_NAME("First name"), LAST_NAME("Last name"),
    DATE_OF_BIRTH("Date of birth"), ADDRESS("Address"), LICENSE_TYPE("License type");

    String string;

    FormField(String string){
        this.string = string;
    }

    @NonNull
    @Override
    public String toString() {
        return string;
    }
}
