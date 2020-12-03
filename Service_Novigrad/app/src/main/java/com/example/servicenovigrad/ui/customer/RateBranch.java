package com.example.servicenovigrad.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.homepages.CustomerHomePage;

public class RateBranch extends UserPage {
    RatingBar ratingBar;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_branch);

        ratingBar = findViewById(R.id.simpleRatingBar);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalStars = ratingBar.getNumStars();
                float rating = ratingBar.getRating();

                Toast.makeText(getApplicationContext(), "Total Stars:"+totalStars+" Rating:"+rating, Toast.LENGTH_LONG).show();

                startActivity(new Intent(RateBranch.this, CustomerHomePage.class));
                finish();

            }
        });



    }

}
