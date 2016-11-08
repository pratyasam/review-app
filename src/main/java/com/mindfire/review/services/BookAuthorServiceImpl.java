package com.mindfire.review.services;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mindfire.review.web.dto.BookAuthorLinkDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.BookAuthor;
import com.mindfire.review.web.repositories.AuthorRepository;
import com.mindfire.review.web.repositories.BookAuthorRepository;
import com.mindfire.review.web.repositories.BookRepository;

/**
 * ${}
 * Created by pratyasa on 25/8/16.
 */
@Service
public class BookAuthorServiceImpl implements BookAuthorService {
	@Autowired
	private BookAuthorRepository bookAuthorRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	
	/**
	 * method to link book and author
	 * @param bookAuthorLinkDto
	 * @exception SQLIntegrityConstraintViolationException
	 */
	@Override
	public void linkBookAndAuthor(BookAuthorLinkDto bookAuthorLinkDto) throws SQLIntegrityConstraintViolationException{
		
		Book book = bookRepository.findByBookName(bookAuthorLinkDto.getBookName());
		Author author = authorRepository.findByAuthorNameIgnoreCase(bookAuthorLinkDto.getAuthorName());
		BookAuthor bookAuthor = new BookAuthor();
		bookAuthor.setAuthor(author);
		bookAuthor.setBook(book);
		bookAuthorRepository.save(bookAuthor);
	}

}
