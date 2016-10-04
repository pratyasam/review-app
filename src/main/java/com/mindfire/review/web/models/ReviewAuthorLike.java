/**
 * @author pratyasa
 */
package com.mindfire.review.web.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the reviews database table.
 */
@Entity
@Table(name = "review_author_like")
@NamedQuery(name = "ReviewAuthorLike.findAll", query = "SELECT r FROM ReviewAuthorLike r")
public class ReviewAuthorLike implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_author_like_id", unique = true, nullable = false)
    private Long reviewAuthorLikeId;
	
	@Column(name = "flag", nullable = false)
    private int reviewAuthorLikeFlag = 0;
	
	//bi-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

	//bi-directional many-to-one association to Author
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_author_id", nullable = false)
    private ReviewAuthor reviewAuthor;

	public Long getReviewAuthorLikeId() {
		return this.reviewAuthorLikeId;
	}

	public void setReviewAuthorLikeId(Long reviewAuthorLikeId) {
		this.reviewAuthorLikeId = reviewAuthorLikeId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ReviewAuthor getReviewAuthor() {
		return this.reviewAuthor;
	}

	public void setReviewAuthor(ReviewAuthor reviewAuthor) {
		this.reviewAuthor = reviewAuthor;
	}
	
	public int getReviewAuthorLikeFlag() {
		return reviewAuthorLikeFlag;
	}

	public void setReviewAuthorLikeFlag(int reviewAuthorLikeFlag) {
		this.reviewAuthorLikeFlag = reviewAuthorLikeFlag;
	}

}
