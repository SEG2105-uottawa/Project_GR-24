package com.example.servicenovigrad.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.BranchReview;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.homepages.CustomerHomePage;
import com.example.servicenovigrad.users.BranchEmployee;

import java.util.Date;

public class RateBranch extends SearchPage {
    RatingBar ratingBar;
    Button submit, back;
    TextView comment, branchName, branchAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_branch);

        // Get views
        branchName = findViewById(R.id.branch_name_to_review);
        branchAddress = findViewById(R.id.branch_address_to_review);
        comment = findViewById(R.id.rating_comment);
        ratingBar = findViewById(R.id.simpleRatingBar);
        submit = findViewById(R.id.submit);
        back = findViewById(R.id.rate_backBtn);

        // Set Values
        branchName.setText(selection.getBranchName());
        branchAddress.setText(selection.getAddress());

        // Listeners
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BranchReview curReview = new BranchReview( new Date().toString(),
                        comment.getText().toString(),
                        String.valueOf(ratingBar.getRating()));

                selection.addBranchReview(curReview);
                branchRef.child("reviews").setValue(selection.getBranchReviews());

                Toast.makeText(getApplicationContext(), "Rating received!", Toast.LENGTH_LONG).show();

                startActivity(new Intent(RateBranch.this, CustomerHomePage.class));
                finishAffinity();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RateBranch.this, CustomerHomePage.class));
                finishAffinity();
            }
        });



    }

}
