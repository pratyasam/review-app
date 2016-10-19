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
	//Map<String, Object> search(SearchDto searchDto, String session,int pagenoa,int pagenob, int pagenou);
	
	/**
	 * Comment
	 * 
	 * @param query The search query
	 * @param searchType The category
	 * @param page The nth page
	 * @return {@link Map} of search results
	 */
	Map<SearchType, Page> search(String query, String role, SearchType searchType, int page);
	
	/**
	 * 
	 * @param searchDto
	 * @return
	 */
    Map<String, Object> searchByGenre(SearchDto searchDto);

    /**
     * 
     * @param rating
     * @return
     */
    Map<String, Object> searchByRating(float rating);
}
