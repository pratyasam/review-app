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
		
		//converts the string category to enum
		SearchType searchCategory = SearchType.valueOf(category);
		
		
		// Service call to fetch the results 
		Map<SearchType, Page> searchResult = searchService.search(query, role, searchCategory, page);
		
		
		
		//return result according to the search category
		ModelAndView model = new ModelAndView();
		
		// Check to see if its a XHR
		if(httpServletRequest.getHeader("X-Requested-With") == null){
			model.setViewName("searchresult");
		}
		
		switch(searchCategory){
			case BOOKS:
				model.setViewName("bookpartial");
				model.addObject("books",searchResult.get(SearchType.BOOKS));
				
				break;
				
			case AUTHORS:
				model.setViewName("authorpartial");
				model.addObject("authors",searchResult.get(SearchType.AUTHORS));
				break;
				
			case USERS:
				model.setViewName("userpartial");
				model.addObject("users",searchResult.get(SearchType.USERS));
				break;
		}
		
	
		return model;
	}
	
	/**
	 * 
	 * 
	 * @param httpSession
	 * @param searchParam
	 * @param category
	 * @param page
	 * @return
	 */
//	@RequestMapping(value = "/search", method = RequestMethod.GET)
//	public Object searchPost(HttpSession httpSession, @RequestParam("searchParam") String searchParam,
//			@RequestParam(value = "searchCategory", defaultValue = "BOOKS") String category,
//			@RequestParam(value = "page", defaultValue = "1") int page) {
//		
//		Map<SearchType, Page> map = new HashMap<>(); // it will store the searchResult
//		String role = "normal";
//        
//		// the role is required to access the verified or non verified set of books
//		if (httpSession.getAttribute("role") != null) {
//			role = (String) httpSession.getAttribute("role");
//		}
//		
//		
//		//it accepts the search type , which is books by default
//		SearchType searchCategory = SearchType.valueOf(category);
//		
//		map = searchService.search(searchParam, role,searchCategory, page); // it accepts the search result set
//		
//
//		ModelAndView modelAndView = new ModelAndView("searchresult"); // creates view 
//		
//		
//        // if the search type is of books, it will attach the result set for books only
//		if (SearchType.BOOKS == searchCategory) {
//			
//			Page bookList = map.get(SearchType.BOOKS); // this will contains the book result
//			int totalPagesB = bookList.getTotalPages(); // required for pagination
//			List<BookAuthorListDto> bookAuthorListDto = new ArrayList<>(); //  required to store the author for each book. assuming thr may be more than one author for a book.
//			 
//			// to handle the null pointer exception
//			if(bookList != null){
//				
//				if(bookList.getContent().size() > 0){
//					for(Book b : bookList.getContent()){
//						bookAuthorListDto book
//					}
//					
//					// ok.allow me to chnge
////					for (Book b : bookList.getContent()) {
////						System.out.println(b.getBookName()); 
////	
////						BookAuthorListDto bookAuthorListDto2 = new BookAuthorListDto();
////						
////						List<Author> authors = new ArrayList<>();
////						authors = bookService.getAuthorByBook(b.getBookName());
////						
////						if(authors.size() != 0)
////						bookAuthorListDto2.setAuthorList(authors);
////						
////						bookAuthorListDto2.setBook(b);
////						bookAuthorListDto.add(bookAuthorListDto2);
////					}
//				}
//			}
//
//		
//			modelAndView.addObject("books", bookAuthorListDto); // it contains the book and its author list
//			modelAndView.addObject("totalpagesb", totalPagesB); // req for pagination
//			modelAndView.addObject("searchparam", searchParam); // the search query is required in the view to display along side the result
//		} 
//        
//		//if the search type is author
//		else if (SearchType.AUTHORS == searchCategory) {
//			
//			Page<Author> authorList = (Page<Author>) map.get(searchCategory.AUTHORS); // accepts the result set for authors
//			int totalPagesA = authorList.getTotalPages(); // required for pagination
//			modelAndView.addObject("authors", authorList.getContent());
//			modelAndView.addObject("totalpagesa", totalPagesA);
//
//		}
//        
//		// if the search type is user
//		else if (SearchType.USERS == searchCategory) {
//			
//			Page<User> userList = (Page<User>) map.get(searchCategory.USERS); // accepts the result set for users
//			int totalPagesU = userList.getTotalPages(); // required for pagination
//			modelAndView.addObject("users", userList.getContent());
//			modelAndView.addObject("totalpagesu", totalPagesU);
//		}
//
//		return modelAndView; // done!
//
//	}

}
