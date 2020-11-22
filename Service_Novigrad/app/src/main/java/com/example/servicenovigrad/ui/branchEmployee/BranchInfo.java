package com.example.servicenovigrad.ui.branchEmployee;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ui.homepages.UserPage;

public class BranchInfo extends UserPage {

    TextView branchName, branchAddress, branchPhoneNumber;
    Button editBranchName, editBranchAddress, editBranchPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_info);

        updateBranchInfo();

    }

    public void updateBranchInfo() {

    }

}
