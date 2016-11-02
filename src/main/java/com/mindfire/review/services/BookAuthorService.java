package com.mindfire.review.services;

import java.sql.SQLIntegrityConstraintViolationException;

import com.mindfire.review.web.dto.BookAuthorLinkDto;

public interface BookAuthorService {
	/**
	 * links book with author
	 * @param bookAuthorLinkDto
	 */

	void linkBookAndAuthor(BookAuthorLinkDto bookAuthorLinkDto) throws SQLIntegrityConstraintViolationException;

}