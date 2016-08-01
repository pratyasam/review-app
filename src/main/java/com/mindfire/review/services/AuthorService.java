package com.mindfire.review.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.Review;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.AuthorRepository;
import com.mindfire.review.web.repositories.AuthorReviewRepository;
import com.mindfire.review.web.repositories.BookAuthorRepository;

/**
 * 
 * @author pratyasa
 *
 */
@Service
public class AuthorService implements AuthorServiceInterface {

	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private AuthorReviewRepository authorReviewRepository;
	@Autowired
	private BookAuthorRepository bookAuthorRepository;
	@Autowired
	private BookServiceInterface bookService;
	/* (non-Javadoc)
	 * @see com.mindfire.review.services.AuthorServiceInterface#getAllAuthor()
	 */

	@Override
	public List<Author> getAllAuthor() {
		return authorRepository.findAll();
	}
	/* (non-Javadoc)
	 * @see com.mindfire.review.services.AuthorServiceInterface#getAuthorByNameLike(java.lang.String)
	 */

	@Override
	public List<Author> getAuthorByNameLike(String name) {
		return authorRepository.findByAuthorNameContainsIgnoreCase(name);
	}
	/* (non-Javadoc)
	 * @see com.mindfire.review.services.AuthorServiceInterface#getAuthorByName(java.lang.String)
	 */

	@Override
	public Author getAuthorByName(String name) {
		return authorRepository.findByAuthorNameIgnoreCase(name);
	}
	/* (non-Javadoc)
	 * @see com.mindfire.review.services.AuthorServiceInterface#getAuthorByRating(int)
	 */

	@Override
	public List<Author> getAuthorByRating(int rating) {
		return authorRepository.findByAuthorRating(rating);
	}
	/* (non-Javadoc)
	 * @see com.mindfire.review.services.AuthorServiceInterface#getAuthorByGenre(java.lang.String)
	 */

	@Override
	public List<Author> getAuthorByGenre(String genre) {
		return authorRepository.findByAuthorGenreContainsIgnoreCase(genre);
	}
	/* (non-Javadoc)
	 * @see com.mindfire.review.services.AuthorServiceInterface#getReviewByAuthor(java.lang.String)
	 */

	@Override
	public List<Review> getReviewByAuthor(String name) {
		return authorReviewRepository.findByAuthor(getAuthorByName(name));
	}
	/* (non-Javadoc)
	 * @see com.mindfire.review.services.AuthorServiceInterface#getBookByAuthor(java.lang.String)
	 */

	@Override
	public List<Book> getBookByAuthor(String name) {
		return bookAuthorRepository.findByAuthor(getAuthorByName(name));
	}
	/* (non-Javadoc)
	 * @see com.mindfire.review.services.AuthorServiceInterface#getUserByAuthorReview(java.lang.String)
	 */

	@Override
	public List<User> getUserByAuthorReview(String name) {
		List<User> users = new ArrayList<>();
		List<Book> books = getBookByAuthor(name);
		for (Book b : books) {
			users.addAll(bookService.getUserByBookReview(b.getBookName()));
		}
		return users;
	}
}
