package com.mindfire.review.web.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the author database table.
 */
@Entity
@Table(name = "author")
@NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a")
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id", unique = true, nullable = false)
    private Long authorId;

    @Column(name = "description", nullable = false, length = 4005)
    private String authorDescription;

    @Column(name = "genre", nullable = false, length = 255)
    private String authorGenre;

    @Column(name = "name", nullable = false, length = 255)
    private String authorName;

    @Column(name = "rating")
    private float authorRating;


    //bi-directional many-to-one association to BookAuthor
    @OneToMany(mappedBy = "author")
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

    public float getAuthorRating() {
        return this.authorRating;
    }

    public void setAuthorRating(float authorRating) {
        this.authorRating = authorRating;
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