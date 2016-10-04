package com.mindfire.review.web.models;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the reviews database table.
 */
@Entity
@Table(name = "review_book")
@NamedQuery(name = "ReviewBook.findAll", query = "SELECT r FROM ReviewBook r")
public class ReviewBook implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_book_id", unique = true, nullable = false)
    private Long reviewBookId;

    @Column(name = "review_text", nullable = false, length = 4005)
    private String reviewText;


    //bi-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //bi-directional many-to-one association to Book
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;


    public ReviewBook() {
    }

    public Long getReviewId() {
        return this.reviewBookId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewBookId = reviewId;
    }

    public String getReviewText() {
        return this.reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


}