package com.mindfire.review.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.services.BookService;
import com.mindfire.review.services.SearchService;
import com.mindfire.review.web.dto.BookAuthorListDto;
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
	private BookService bookService;
	
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Object searchPost(HttpSession httpSession, @RequestParam("searchParam") String searchParam, @RequestParam("searchCategory") String searchCategory, @RequestParam(value = "pagenob", defaultValue="1") int pagenob, @RequestParam(value="pagenoa", defaultValue="1") int pagenoa, @RequestParam(value="pagenou", defaultValue="1") int pagenou) {
		Map<String, Object> map = new HashMap<>();
		String role = "normal";
		
		if(httpSession.getAttribute("role") != null){
			role = (String)httpSession.getAttribute("role");
		}
		SearchDto searchDto = new SearchDto();
		searchDto.setSearchParam(searchParam);
		map = searchService.search(searchDto, role, pagenoa, pagenob, pagenou);
		
		ModelAndView modelAndView = new ModelAndView("searchresult");
		modelAndView.addObject("searchparam", searchDto.getSearchParam());
		
		if(searchCategory.equalsIgnoreCase("books")){
			Page<Book> bookList = (Page<Book>) map.get("books");
			int totalPagesB = bookList.getTotalPages();
			List<BookAuthorListDto> bookAuthorListDto = new ArrayList<>();

			for (Book b : bookList.getContent()) {

				BookAuthorListDto bookAuthorListDto2 = new BookAuthorListDto();
				List<Author> authors = bookService.getAuthorByBook(b.getBookName());
				bookAuthorListDto2.setAuthorList(authors);
				bookAuthorListDto2.setBook(b);
				bookAuthorListDto.add(bookAuthorListDto2);
			}
			modelAndView.addObject("books", bookAuthorListDto);
			modelAndView.addObject("totalpagesb", totalPagesB);
		}
		
		if(searchCategory.equalsIgnoreCase("authors")){
			Page<Author> authorList = (Page<Author>) map.get("authors");
			int totalPagesA = authorList.getTotalPages();
			modelAndView.addObject("authors", authorList.getContent());
			modelAndView.addObject("totalpagesa", totalPagesA);
			
		}
		
		if(searchCategory.equalsIgnoreCase("users")){
			Page<User> userList = (Page<User>) map.get("users");
			int totalPagesU = userList.getTotalPages();
			modelAndView.addObject("users", userList.getContent());
			modelAndView.addObject("totalpagesu", totalPagesU);
		}
		
		return modelAndView;

	}
	
}
