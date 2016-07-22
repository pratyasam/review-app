package com.mindfire.review.web.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the author database table.
 * 
 */
@Entity
@Table(name="author")
@NamedQuery(name="Author.findAll", query="SELECT a FROM Author a")
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="author_id", unique=true, nullable=false)
	private Long authorId;

	@Column(name="author_description", nullable=false, length=4005)
	private String authorDescription;

	@Column(name="author_genre", nullable=false, length=255)
	private String authorGenre;

	@Column(name="author_name", nullable=false, length=255)
	private String authorName;

	@Column(name="author_rating")
	private int authorRating;

	//bi-directional many-to-one association to AuthorReview
	@OneToMany(mappedBy="author")
	private List<AuthorReview> authorReviewAssociations;

	//bi-directional many-to-one association to BookAuthor
	@OneToMany(mappedBy="author")
	private List<BookAuthor> bookAuthors;

	public Author() {
	}

	public Long getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorDescription() {
		return this.authorDescription;
	}

	public void setAuthorDescription(String authorDescription) {
		this.authorDescription = authorDescription;
	}

	public String getAuthorGenre() {
		return this.authorGenre;
	}

	public void setAuthorGenre(String authorGenre) {
		this.authorGenre = authorGenre;
	}

	public String getAuthorName() {
		return this.authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getAuthorRating() {
		return this.authorRating;
	}

	public void setAuthorRating(int authorRating) {
		this.authorRating = authorRating;
	}

	public List<AuthorReview> getAuthorReviewAssociations() {
		return this.authorReviewAssociations;
	}

	public void setAuthorReviewAssociations(List<AuthorReview> authorReviewAssociations) {
		this.authorReviewAssociations = authorReviewAssociations;
	}

	public AuthorReview addAuthorReviewAssociation(AuthorReview authorReviewAssociation) {
		getAuthorReviewAssociations().add(authorReviewAssociation);
		authorReviewAssociation.setAuthor(this);

		return authorReviewAssociation;
	}

	public AuthorReview removeAuthorReviewAssociation(AuthorReview authorReviewAssociation) {
		getAuthorReviewAssociations().remove(authorReviewAssociation);
		authorReviewAssociation.setAuthor(null);

		return authorReviewAssociation;
	}

	public List<BookAuthor> getBookAuthors() {
		return this.bookAuthors;
	}

	public void setBookAuthors(List<BookAuthor> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	public BookAuthor addBookAuthor(BookAuthor bookAuthor) {
		getBookAuthors().add(bookAuthor);
		bookAuthor.setAuthor(this);

		return bookAuthor;
	}

	public BookAuthor removeBookAuthor(BookAuthor bookAuthor) {
		getBookAuthors().remove(bookAuthor);
		bookAuthor.setAuthor(null);

		return bookAuthor;
	}

}