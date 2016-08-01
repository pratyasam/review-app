/**
 * 
 */
package com.mindfire.review.web.dto;

/**
 * @author pratyasa
 *
 */
public class ReviewBookDto {
	private String reviewBook;
	private Long userId;
	public String getReviewBook() {
		return reviewBook;
	}
	public void setReviewBook(String reviewBook) {
		this.reviewBook = reviewBook;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
