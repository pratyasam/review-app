package com.mindfire.review.web.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.enums.SearchType;
import com.mindfire.review.services.BookService;
import com.mindfire.review.services.SearchService;

/**
 * ${} Created by pratyasa on 18/8/16.
 */
@SessionAttributes("search")
@Controller
public class SearchController {

	@Autowired
	private BookService bookService;

	@Autowired
	private SearchService searchService;
	
	
	
	/**
	 * 
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Object search(HttpSession httpSession, HttpServletRequest httpServletRequest,
			@RequestParam("searchParam") String query,
			@RequestParam(value = "category", defaultValue = "BOOKS") String category,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		
	
		// Validation
		if(query == null || query.trim().length() == 0){ 
			return "redirect:/";
		}
		
		// Role based authorization
		String role = "normal";
		
		if (httpSession.getAttribute("role") != null) {
			role = (String) httpSession.getAttribute("role");
		}
		
		category = category.toUpperCase();
		SearchType searchCategory = SearchType.BOOKS;
		
		for(SearchType s : SearchType.values()){
			if(s.name().equals(category)){
				//converts the string category to enum
				searchCategory = SearchType.valueOf(category);
			}
		}
		
		// Service call to fetch the results 
		Map<SearchType, Page> searchResult = searchService.search(query, role, searchCategory, page);
		
		
		
		//return result according to the search category
		ModelAndView model = new ModelAndView();
		
		// Check to see if its a XHR
		if(httpServletRequest.getHeader("X-Requested-With") == null){
			model.setViewName("searchresult");
			model.addObject("SEARCH_TYPE", searchCategory);
			switch(searchCategory){
				case BOOKS:
					model.addObject("books",searchResult.get(SearchType.BOOKS));
					break;
					
				case AUTHORS:
					model.addObject("authors",searchResult.get(SearchType.AUTHORS));
					break;
					
				case USERS:
					model.addObject("users",searchResult.get(SearchType.USERS));
					break;
					
				default:
					model.addObject("books",searchResult.get(SearchType.BOOKS));
					break;
			}
			model.addObject("searchParam", query);
			return model;
		}
		
		switch(searchCategory){
			case BOOKS:
				model.setViewName("bookpartial");
				model.addObject("books",searchResult.get(SearchType.BOOKS));
				model.addObject("searchParam", query);
				break;
				
			case AUTHORS:
				model.setViewName("authorpartial");
				model.addObject("authors",searchResult.get(SearchType.AUTHORS));
				model.addObject("searchParam", query);
				break;
				
			case USERS:
				model.setViewName("userpartial");
				model.addObject("users",searchResult.get(SearchType.USERS));
				model.addObject("searchParam", query);
				break;
				
			default:
				model.setViewName("bookpartial");
				model.addObject("books",searchResult.get(SearchType.BOOKS));
				model.addObject("searchParam", query);
				break;
		}
		
	
		return model;
	}

}
