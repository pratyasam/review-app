/**
 * @author pratyasa
 */
package com.mindfire.review.web.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the reviews database table.
 */
@Entity
@Table(name = "review_book_like")
@NamedQuery(name = "ReviewBookLike.findAll", query = "SELECT r FROM ReviewBookLike r")
public class ReviewBookLike implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_book_like_id", unique = true, nullable = false)
    private Long reviewBookLikeId;
	
	@Column(name = "flag", nullable = false)
    private int reviewBookLikeFlag = 0;
	
	//bi-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    //bi-directional many-to-one association to Book
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_book_id", nullable = false)
    private ReviewBook reviewBook;

	public Long getReviewBookLikeId() {
		return this.reviewBookLikeId;
	}

	public void setReviewBookLikeId(Long reviewBookLikeId) {
		this.reviewBookLikeId = reviewBookLikeId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ReviewBook getReviewBook() {
		return this.reviewBook;
	}

	public void setReviewBook(ReviewBook reviewBook) {
		this.reviewBook = reviewBook;
	}

	public int getReviewBookLikeFlag() {
		return reviewBookLikeFlag;
	}

	public void setReviewBookLikeFlag(int reviewBookLikeFlag) {
		this.reviewBookLikeFlag = reviewBookLikeFlag;
	}

}
