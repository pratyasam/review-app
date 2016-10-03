package com.mindfire.review.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.services.SearchService;
import com.mindfire.review.web.dto.SearchDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.User;

/**
 * ${} Created by pratyasa on 18/8/16.
 */
@SessionAttributes("search")
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Object searchPost(HttpSession httpSession,@RequestParam(value = "query") String searchParam, @RequestParam(value="pagenoa", defaultValue="1") int pagenoa, @RequestParam(value="pagenou", defaultValue="1") int pagenou, @RequestParam(value="pagenob", defaultValue="1") int pagenob) {
		Map<String, Object> map = new HashMap<>();
		String role = "normal";
		
		if(httpSession.getAttribute("role") != null){
			role = (String)httpSession.getAttribute("role");
		}
		SearchDto searchDto = new SearchDto();
		searchDto.setSearchParam(searchParam);
		map = searchService.search(searchDto, role,pagenoa, pagenob, pagenou);
		Page<Author> authorList = (Page<Author>) map.get("authors");
		Page<Book> bookList = (Page<Book>) map.get("books");
		Page<User> userList = (Page<User>) map.get("users");
		int totalPagesA = authorList.getTotalPages();
		int totalPagesB = bookList.getTotalPages();
		int totalPagesU = userList.getTotalPages();
		ModelAndView modelAndView = new ModelAndView("searchresult");
		modelAndView.addObject("searchparam", searchDto.getSearchParam());
		modelAndView.addObject("users", userList.getContent());
		modelAndView.addObject("authors", authorList.getContent());
		modelAndView.addObject("books", bookList.getContent());
		modelAndView.addObject("totalpagesa", totalPagesA);
		modelAndView.addObject("totalpagesb", totalPagesB);
		modelAndView.addObject("totalpagesu", totalPagesU);
		return modelAndView;

	}
	
}
