package com.mindfire.review.web.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the review_author database table.
 * 
 */
@Entity
@Table(name="review_author")
@NamedQuery(name="ReviewAuthor.findAll", query="SELECT r FROM ReviewAuthor r")
public class ReviewAuthor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="review_author_id", unique=true, nullable=false)
	private Long reviewAuthorId;

	@Column(name="review_author_text", nullable=false, length=4005)
	private String reviewAuthorText;

	//bi-directional many-to-one association to AuthorReview
	@OneToMany(mappedBy="reviewAuthor")
	private List<AuthorReview> authorReviewAssociations;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	public ReviewAuthor() {
	}

	public Long getReviewAuthorId() {
		return this.reviewAuthorId;
	}

	public void setReviewAuthorId(Long reviewAuthorId) {
		this.reviewAuthorId = reviewAuthorId;
	}

	public String getReviewAuthorText() {
		return this.reviewAuthorText;
	}

	public void setReviewAuthorText(String reviewAuthorText) {
		this.reviewAuthorText = reviewAuthorText;
	}

	public List<AuthorReview> getAuthorReviewAssociations() {
		return this.authorReviewAssociations;
	}

	public void setAuthorReviewAssociations(List<AuthorReview> authorReviewAssociations) {
		this.authorReviewAssociations = authorReviewAssociations;
	}

	public AuthorReview addAuthorReviewAssociation(AuthorReview authorReviewAssociation) {
		getAuthorReviewAssociations().add(authorReviewAssociation);
		authorReviewAssociation.setReviewAuthor(this);

		return authorReviewAssociation;
	}

	public AuthorReview removeAuthorReviewAssociation(AuthorReview authorReviewAssociation) {
		getAuthorReviewAssociations().remove(authorReviewAssociation);
		authorReviewAssociation.setReviewAuthor(null);

		return authorReviewAssociation;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}