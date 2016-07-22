package com.mindfire.review.web.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the book_reviews database table.
 * 
 */
@Entity
@Table(name="book_reviews")
@NamedQuery(name="BookReview.findAll", query="SELECT b FROM BookReview b")
public class BookReview implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="book_reviews_id", unique=true, nullable=false)
	private Long bookReviewsId;

	//bi-directional many-to-one association to Book
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="book_id", nullable=false)
	private Book book;

	//bi-directional many-to-one association to Review
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="review_id", nullable=false)
	private Review review;

	public BookReview() {
	}

	public Long getBookReviewsId() {
		return this.bookReviewsId;
	}

	public void setBookReviewsId(Long bookReviewsId) {
		this.bookReviewsId = bookReviewsId;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Review getReview() {
		return this.review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

}