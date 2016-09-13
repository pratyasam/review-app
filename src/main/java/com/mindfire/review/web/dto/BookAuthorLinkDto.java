/**
 * 
 */
package com.mindfire.review.web.dto;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;

/**
 * @author pratyasa
 *
 */
public class BookAuthorLinkDto {
	private String authorName;
	private String bookName;

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

}
