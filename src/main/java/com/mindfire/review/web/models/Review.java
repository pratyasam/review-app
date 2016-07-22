package com.mindfire.review.web.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the reviews database table.
 * 
 */
@Entity
@Table(name="reviews")
@NamedQuery(name="Review.findAll", query="SELECT r FROM Review r")
public class Review implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="review_id", unique=true, nullable=false)
	private Long reviewId;

	@Column(name="review_text", nullable=false, length=4005)
	private String reviewText;

	//bi-directional many-to-one association to BookReview
	@OneToMany(mappedBy="review")
	private List<BookReview> bookReviews;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	public Review() {
	}

	public Long getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewText() {
		return this.reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public List<BookReview> getBookReviews() {
		return this.bookReviews;
	}

	public void setBookReviews(List<BookReview> bookReviews) {
		this.bookReviews = bookReviews;
	}

	public BookReview addBookReview(BookReview bookReview) {
		getBookReviews().add(bookReview);
		bookReview.setReview(this);

		return bookReview;
	}

	public BookReview removeBookReview(BookReview bookReview) {
		getBookReviews().remove(bookReview);
		bookReview.setReview(null);

		return bookReview;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}