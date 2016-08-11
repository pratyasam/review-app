package com.mindfire.review.web.models;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the book_authors database table.
 */
@Entity
@Table(name = "book_authors")
@NamedQuery(name = "BookAuthor.findAll", query = "SELECT b FROM BookAuthor b")
public class BookAuthor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_authors_id", unique = true, nullable = false)
    private Long bookAuthorsId;

    //bi-directional many-to-one association to Book
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    //bi-directional many-to-one association to Author
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public BookAuthor() {
    }

    public Long getBookAuthorsId() {
        return this.bookAuthorsId;
    }

    public void setBookAuthorsId(Long bookAuthorsId) {
        this.bookAuthorsId = bookAuthorsId;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}