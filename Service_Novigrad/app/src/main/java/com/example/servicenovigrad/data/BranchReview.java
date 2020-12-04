package com.example.servicenovigrad.data;

import java.io.Serializable;

public class BranchReview implements Serializable {
    private static final float TOTAL_STARS = 5;

    private String dateCreated;
    private String comment;
    private float rating;

    public BranchReview(String dateCreated, String comment, float rating) {
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
        return Float.toString(rating);

    }

    @Override
    public String toString() {
        return "BranchReview{" +
                "dateCreated=" + dateCreated +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';

    }

}
