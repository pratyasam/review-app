package com.mindfire.review.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.Review;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.BookAuthorRepository;
import com.mindfire.review.web.repositories.BookRepository;
import com.mindfire.review.web.repositories.BookReviewRepository;
@Service
public class BookService implements BookServiceInterface {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookAuthorRepository bookAuthorRepository;
	@Autowired
	private BookReviewRepository bookReviewRepository;

	public BookService() {
		super();
	}

	/**
	 * 
	 */
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}

	/**
	 * 
	 */
	
	public List<Book> getBookByGenre(String genre) {
		return bookRepository.findByBookGenreContainsIgnoreCase(genre);
	}

	/**
	 * 
	 */

	public List<Book> getBookByRating(int rating) {
		return bookRepository.findByBookRating(rating);
	}

	/**
	 * 
	 */

	public Book getBookByIsbn(String isbn) {
		return bookRepository.findByBookIsbn(isbn);
	}

	/**
	 * 
	 */
	
	public Book getBookByName(String name) {
		return bookRepository.findByBookNameContainsIgnoreCase(name);
	}

	/**
	 * 
	 */
	
	public List<Author> getAuthorByBook(String name) {
		Book book = getBookByName(name);
		return bookAuthorRepository.findByBook(book);
	}

	/**
	 * 
	 */
	
	public List<Review> getReviewByBook(String name) {
		Book book = getBookByName(name);
		return bookReviewRepository.findByBook(book);
	}

	/**
	 * 
	 */
	
	public List<User> getUserByBookReview(String name) {
		List<User> users = new ArrayList<>();
		List<Review> reviews;
		reviews = getReviewByBook(name);
		for(Review r : reviews){
			users.add(r.getUser());
		}
		return users;
	}

}