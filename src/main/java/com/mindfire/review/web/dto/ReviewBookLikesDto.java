/**
 * 
 */
package com.mindfire.review.web.dto;

import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;

/**
 * @author pratyasa
 *
 */
public class ReviewBookLikesDto {
	
	private ReviewBook reviewBook;
	
	private int likes; 
	
	private int dislikes;

	public ReviewBook getReviewBook() {
		return reviewBook;
	}

	public void setReviewBook(ReviewBook reviewBook) {
		this.reviewBook = reviewBook;
	}

	public int getLikes() {
		return likes;
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
