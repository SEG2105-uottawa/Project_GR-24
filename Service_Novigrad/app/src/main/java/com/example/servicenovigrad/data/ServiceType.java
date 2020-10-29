package com.example.servicenovigrad.data;

import androidx.annotation.NonNull;

public enum ServiceType {
    DRIVERS_LICENSE("Driver's License"), HEALTH_CARD("Health Card"), PHOTO_ID("Photo I.D.");

    String string;

    ServiceType(String string){
        this.string = string;
    }

    @NonNull
    @Override
    public String toString() {
        return string;
    }
}
