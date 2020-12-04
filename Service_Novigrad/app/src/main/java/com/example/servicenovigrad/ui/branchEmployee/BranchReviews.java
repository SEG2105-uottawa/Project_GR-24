package com.example.servicenovigrad.ui.branchEmployee;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.BranchReview;
import com.example.servicenovigrad.ui.UserPage;
import com.example.servicenovigrad.ui.admin.AdminEditBranchAccounts;
import com.example.servicenovigrad.ui.homepages.BranchEmployeeHomePage;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class BranchReviews extends UserPage {
    ArrayList<BranchReview> branchReviews;
    ArrayAdapter<BranchReview> adapter;
    ListView list;
    Dialog dialog;
    TextView branchName, branchAddress, dialogBranchName, branchRating, branchComment;
    Button deleteReview, back;
    BranchReview selReview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_reviews);

        // Get views
        branchName = findViewById(R.id.branch_name_reviews);
        branchAddress = findViewById(R.id.branch_address_reviews);
        list = findViewById(R.id.branch_reviews_list);

        // Set Views
        branchName.setText(branchObject().getBranchName());
        branchAddress.setText(branchObject().getAddress());

        branchReviews = new ArrayList<>(branchObject().getBranchReviews().values());

        adapter = new ArrayAdapter<>(BranchReviews.this,
                android.R.layout.simple_expandable_list_item_1, branchReviews);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selReview = (BranchReview) list.getItemAtPosition(position);

                dialog = new Dialog(BranchReviews.this);
                dialog.setContentView(R.layout.dialog_display_review);

                // Get views
                dialogBranchName = dialog.findViewById(R.id.branch_name);
                branchRating = dialog.findViewById(R.id.review_rating);
                branchComment = dialog.findViewById(R.id.review_comment);
                deleteReview = dialog.findViewById(R.id.delete_rating);
                back = dialog.findViewById(R.id.go_back);

                // Set text
                dialogBranchName.setText(branchObject().getBranchName());
                branchRating.setText(selReview.getRating()+" out of 5");
                branchComment.setText(selReview.getComment());

                // Listeners
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                deleteReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userRef.child("reviews").child(selReview.getDateCreated()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null){
                                    dialog.dismiss();
                                    Toast.makeText( BranchReviews.this, "Review successfully deleted!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(BranchReviews.this, BranchEmployeeHomePage.class));
                                }
                                else{
                                    Log.e("TAG", error.toString());
                                    Toast.makeText(BranchReviews.this,
                                            "Error deleting review...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                dialog.show();

            }
        });


    }
}
