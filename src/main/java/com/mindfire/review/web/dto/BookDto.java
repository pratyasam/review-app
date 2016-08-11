package com.mindfire.review.web.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by pratyasa on 3/8/16.
 */
public class BookDto {

    private float bookCost;

    private String bookDescription;

    @NotNull(message = "Genre cannot be Null")
    private String bookGenre;

    @NotNull(message = "ISBN cannot be Null.")
    private String bookIsbn;

    private String bookLink;

    @NotNull(message = "A book must have a name.")
    private String bookName;

    @Min(value = 1, message = "Rating should be atleast 1")
    @Max(value = 5, message = "Rating should be less than equal to 5")
    private float bookRating;

    @NotNull(message = "ReviewBook cannot be Null")
    private String bookReview;
    private boolean bookVerified;

    public boolean isBookVerified() {
        return bookVerified;
    }

    public void setBookVerified(boolean bookVerified) {
        this.bookVerified = bookVerified;
    }

    public float getBookCost() {
        return bookCost;
    }

    public void setBookCost(float bookCost) {
        this.bookCost = bookCost;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookLink() {
        return bookLink;
    }

    public void setBookLink(String bookLink) {
        this.bookLink = bookLink;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getBookRating() {
        return bookRating;
    }

    public void setBookRating(float bookRating) {
        this.bookRating = bookRating;
    }

    public String getBookReview() {
        return bookReview;
    }

    public void setBookReview(String bookReview) {
        this.bookReview = bookReview;
    }


}
