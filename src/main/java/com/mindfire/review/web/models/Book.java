package com.mindfire.review.web.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@Table(name="book")
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="book_id", unique=true, nullable=false)
	private Long bookId;

	@Column(name="book_cost", nullable=false)
	private float bookCost;

	@Column(name="book_description", nullable=false, length=4005)
	private String bookDescription;

	@Column(name="book_genre", nullable=false, length=255)
	private String bookGenre;

	@Column(name="book_isbn", nullable=false, length=255)
	private String bookIsbn;

	@Column(name="book_link", length=255)
	private String bookLink;

	@Column(name="book_name", nullable=false, length=255)
	private String bookName;

	@Column(name="book_rating", nullable=false)
	private int bookRating;

	@Column(name="book_review", nullable=false, length=4005)
	private String bookReview;

	//bi-directional many-to-one association to BookAuthor
	@OneToMany(mappedBy="book")
	private List<BookAuthor> bookAuthors;

	//bi-directional many-to-one association to BookReview
	@OneToMany(mappedBy="book")
	private List<BookReview> bookReviews;

	public Book() {
	}

	public Long getBookId() {
		return this.bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public float getBookCost() {
		return this.bookCost;
	}

	public void setBookCost(float bookCost) {
		this.bookCost = bookCost;
	}

	public String getBookDescription() {
		return this.bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public String getBookGenre() {
		return this.bookGenre;
	}

	public void setBookGenre(String bookGenre) {
		this.bookGenre = bookGenre;
	}

	public String getBookIsbn() {
		return this.bookIsbn;
	}

	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}

	public String getBookLink() {
		return this.bookLink;
	}

	public void setBookLink(String bookLink) {
		this.bookLink = bookLink;
	}

	public String getBookName() {
		return this.bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getBookRating() {
		return this.bookRating;
	}

	public void setBookRating(int bookRating) {
		this.bookRating = bookRating;
	}

	public String getBookReview() {
		return this.bookReview;
	}

	public void setBookReview(String bookReview) {
		this.bookReview = bookReview;
	}

	public List<BookAuthor> getBookAuthors() {
		return this.bookAuthors;
	}

	public void setBookAuthors(List<BookAuthor> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	public BookAuthor addBookAuthor(BookAuthor bookAuthor) {
		getBookAuthors().add(bookAuthor);
		bookAuthor.setBook(this);

		return bookAuthor;
	}

	public BookAuthor removeBookAuthor(BookAuthor bookAuthor) {
		getBookAuthors().remove(bookAuthor);
		bookAuthor.setBook(null);

		return bookAuthor;
	}

	public List<BookReview> getBookReviews() {
		return this.bookReviews;
	}

	public void setBookReviews(List<BookReview> bookReviews) {
		this.bookReviews = bookReviews;
	}

	public BookReview addBookReview(BookReview bookReview) {
		getBookReviews().add(bookReview);
		bookReview.setBook(this);

		return bookReview;
	}

	public BookReview removeBookReview(BookReview bookReview) {
		getBookReviews().remove(bookReview);
		bookReview.setBook(null);

		return bookReview;
	}

}