package com.mindfire.review.services;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.mindfire.review.enums.SearchType;
import com.mindfire.review.web.dto.SearchDto;

/**
 * ${}
 * Created by pratyasa on 18/8/16.
 */
public interface SearchService {
	
	
	/**
	 * This method returns a map of search types and their corresponding results as the key value pair.
	 * The search type can be authors, books or users.
	 * @param query The search query
	 * @param searchType The category
	 * @param page The nth page
	 * @return {@link Map} of search results
	 */
	Map<SearchType, Page> search(String query, String role, SearchType searchType, int page);
	
	/**
	 * This method returns a map of search results by searching for the genre.
	 * @param searchDto
	 * @return {@link Map} of search results
	 */
    Map<String, Object> searchByGenre(SearchDto searchDto);

    /**
     * This method returns a map of search results by searching for the rating.
     * @param rating
     * @return {@link Map} of search results
     */
    Map<String, Object> searchByRating(float rating);
}
