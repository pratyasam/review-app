package com.mindfire.review.services;

import com.mindfire.review.web.dto.SearchDto;

import java.util.Map;

/**
 * ${}
 * Created by pratyasa on 18/8/16.
 */
public interface SearchService {
    Map<String, Object> search(SearchDto searchDto);

    Map<String, Object> searchByGenre(SearchDto searchDto);

    Map<String, Object> searchByRating(float rating);
}
