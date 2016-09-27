package com.mindfire.review.services;

import com.mindfire.review.web.dto.SearchDto;

import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * ${}
 * Created by pratyasa on 18/8/16.
 */
public interface SearchService {
	Map<String, Object> search(SearchDto searchDto, String session,int pagenoa,int pagenob, int pagenou);

    Map<String, Object> searchByGenre(SearchDto searchDto);

    Map<String, Object> searchByRating(float rating);
}
