package com.example.servicenovigrad.data;

import java.io.Serializable;

public class BranchReview implements Serializable {
    private static final String TOTAL_STARS = "5";

    private String dateCreated;
    private String comment;
    private String rating;

    public BranchReview(String dateCreated, String comment, String rating) {
        this.dateCreated = dateCreated;
        this.comment = comment;
        this.rating = rating;

    }

    public String getDateCreated() {
        return dateCreated.toString();

    }

    public String getComment() {
        return comment;

    }

    public String getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Date: " + dateCreated + "\nRating: "+ rating +" out of 5";

    }

}
