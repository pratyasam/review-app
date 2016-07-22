package com.mindfire.review.web.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the author_review_association database table.
 * 
 */
@Entity
@Table(name="author_review_association")
@NamedQuery(name="AuthorReview.findAll", query="SELECT a FROM AuthorReview a")
public class AuthorReview implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="author_review_association_id", unique=true, nullable=false)
	private Long authorReviewAssociationId;

	//bi-directional many-to-one association to ReviewAuthor
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="review_author_id", nullable=false)
	private ReviewAuthor reviewAuthor;

	//bi-directional many-to-one association to Author
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="author_id", nullable=false)
	private Author author;

	public AuthorReview() {
	}

	public Long getAuthorReviewAssociationId() {
		return this.authorReviewAssociationId;
	}

	public void setAuthorReviewAssociationId(Long authorReviewAssociationId) {
		this.authorReviewAssociationId = authorReviewAssociationId;
	}

	public ReviewAuthor getReviewAuthor() {
		return this.reviewAuthor;
	}

	public void setReviewAuthor(ReviewAuthor reviewAuthor) {
		this.reviewAuthor = reviewAuthor;
	}

	public Author getAuthor() {
		return this.author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

}