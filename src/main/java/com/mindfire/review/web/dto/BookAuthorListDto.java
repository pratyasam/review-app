/**
 * 
 */
package com.mindfire.review.web.dto;

import java.util.List;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;

/**
 * @author pratyasa
 *
 */
public class BookAuthorListDto {
	private List<Author> authorList;
	private Book book;

	public List<Author> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Author> authorList) {
		this.authorList = authorList;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
