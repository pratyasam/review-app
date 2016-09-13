package com.mindfire.review.services;

import com.mindfire.review.web.dto.BookAuthorLinkDto;

public interface BookAuthorService {
	/**
	 * 
	 * @param bookAuthorLinkDto
	 */

	void linkBookAndAuthor(BookAuthorLinkDto bookAuthorLinkDto);

}