package com.example.servicenovigrad.users;

import androidx.annotation.NonNull;

public enum Roles {ADMIN("Administrator"), CUSTOMER("Customer"),
    BRANCH_EMPLOYEE("Branch Employee");

    String string;

    Roles(String string){
        this.string = string;
    }

    @NonNull
    @Override
    public String toString() {
        return string;
    }
}
