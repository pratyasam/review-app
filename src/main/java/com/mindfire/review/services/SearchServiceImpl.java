/**
 * ${}
 * Created by pratyasa on 16/8/16.
 */
package com.mindfire.review.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mindfire.review.enums.SearchType;
import com.mindfire.review.web.dto.SearchDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;

@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;

	/**
	 * Custom search based on category
	 * 
	 */
	@Override
	public Map<SearchType, Page> search(final String query, final String role, final SearchType searchType,
			final int page) {

		Map<SearchType, Page> searchResults = new HashMap();

		// Validate the search query, simple validation
		if (query == null || query.length() == 0) {

			return null;
		}

		if (searchType != null) {
			switch (searchType) {
			case BOOKS:
				searchResults.put(SearchType.BOOKS, bookService.searchForBooks(query, query, query, page));
				break;
			case AUTHORS:
				searchResults.put(SearchType.AUTHORS, authorService.getAuthorByNameLike(query, page));
				break;
			case USERS:
				searchResults.put(SearchType.USERS, userService.getUserFirstName(query, page));
				break;
			default:
				searchResults.put(SearchType.BOOKS, bookService.searchForBooks(query, query, query, page));
				break;
			}
		}

		return searchResults;
	}

	/**
	 * search by genre
	 */
	@Override
	public Map<String, Object> searchByGenre(SearchDto searchDto) {

		String genre = searchDto.getSearchParam();
		List<Author> authors = authorService.getAuthorByGenre(genre);
		List<Book> books = bookService.getBookByGenre(genre, true);
		Map<String, Object> map = new HashMap<>();
		map.put("authors", authors);
		map.put("books", books);

		return map;
	}

	/**
	 * search by rating
	 */
	@Override
	public Map<String, Object> searchByRating(float rating) {

		List<Author> authors = authorService.getAuthorByRating(rating);
		List<Book> books = bookService.getBookByRating(rating);
		Map<String, Object> map = new HashMap<>();
		map.put("authors", authors);
		map.put("book", books);

		return map;
	}
}
