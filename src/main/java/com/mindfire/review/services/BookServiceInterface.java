package com.mindfire.review.services;

import java.util.List;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.Review;
import com.mindfire.review.web.models.User;

public interface BookServiceInterface {

	/**
	 * 
	 * @return
	 */
	List<Book> getBooks();

	/**
	 * 
	 * @param genre
	 * @return
	 */
	List<Book> getBookByGenre(String genre);

	/**
	 * 
	 * @param rating
	 * @return
	 */
	List<Book> getBookByRating(int rating);

	/**
	 * 
	 * @param isbn
	 * @return
	 */
	Book getBookByIsbn(String isbn);

	/**
	 * 
	 * @param name
	 * @return
	 */
	Book getBookByName(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	List<Author> getAuthorByBook(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	List<Review> getReviewByBook(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	List<User> getUserByBookReview(String name);

}