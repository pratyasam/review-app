package com.mindfire.review.web.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by pratyasa on 10/8/16.
 */
public class ReviewAuthorDto {
    @NotNull(message = " review field cannot be Null")
    private String reviewText;

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
