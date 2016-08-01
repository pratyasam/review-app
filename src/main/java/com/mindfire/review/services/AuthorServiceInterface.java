package com.mindfire.review.services;

import java.util.List;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.Review;
import com.mindfire.review.web.models.User;

public interface AuthorServiceInterface {

	/**
	 * 
	 * @return
	 */

	List<Author> getAllAuthor();

	/**
	 * 
	 * @param name
	 * @return
	 */

	List<Author> getAuthorByNameLike(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */

	Author getAuthorByName(String name);

	/**
	 * 
	 * @param rating
	 * @return
	 */

	List<Author> getAuthorByRating(int rating);

	/**
	 * 
	 * @param genre
	 * @return
	 */

	List<Author> getAuthorByGenre(String genre);

	/**
	 * 
	 * @param name
	 * @return
	 */

	List<Review> getReviewByAuthor(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */

	List<Book> getBookByAuthor(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */

	List<User> getUserByAuthorReview(String name);

}