/**
 *
 */
package com.mindfire.review.web.dto;

import javax.validation.constraints.NotNull;

/**
 * @author pratyasa
 */
public class ReviewBookDto {
    @NotNull(message = " review field cannot be Null")
    private String reviewText;

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

}
