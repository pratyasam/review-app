/**
 * 
 */
package com.mindfire.review.web.dto;

import com.mindfire.review.web.models.ReviewAuthor;

/**
 * @author pratyasa
 *
 */
public class ReviewAuthorLikesDto {
	
	private ReviewAuthor reviewAuthor;
	
	private int likes;
	
	private int dislikes;

	public ReviewAuthor getReviewAuthor() {
		return this.reviewAuthor;
	}

	public void setReviewAuthor(ReviewAuthor reviewAuthor) {
		this.reviewAuthor = reviewAuthor;
	}

	public int getLikes() {
		return this.likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
	
	

}
