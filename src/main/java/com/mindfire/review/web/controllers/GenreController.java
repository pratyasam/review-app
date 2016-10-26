/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookService;
import com.mindfire.review.web.dto.BookAuthorListDto;
import com.mindfire.review.web.dto.SearchDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;

/**
 * @author pratyasa
 *
 */
@Controller
public class GenreController {
	@Autowired
	BookService bookService;
	@Autowired
	AuthorService authorService;
	@RequestMapping(value="/genre/{genreName}", method = RequestMethod.GET)
	public Object getGenre(@PathVariable("genreName") String genreName, @RequestParam(value = "pagenoa", defaultValue = "1") int pagenoa, @RequestParam(value = "pagenob", defaultValue = "1") int pagenob, HttpSession httpSession){
		Page<Book> bookList;
		if(httpSession.getAttribute("userName") != null &&(httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator")))
		bookList = bookService.getBookByGenreAdmin(genreName,pagenob,6);
		else
		bookList = bookService.getBookByGenre(genreName,true, pagenob,6);
		
		 int totalPagesBook = bookList.getTotalPages();
		Page<Author> authorList = authorService.getAuthorByGenre(genreName,pagenoa, 6);
		List<Author> authors = authorList.getContent();
		int totalPagesAuthor = authorList.getTotalPages();
		List<BookAuthorListDto> ba = new ArrayList<>();
		for (Book b:bookList.getContent()){
			BookAuthorListDto bookAuthorListDto = new BookAuthorListDto();
			List<Author> a = bookService.getAuthorByBook(b.getBookName());
			bookAuthorListDto.setAuthorList(a);
			bookAuthorListDto.setBook(b);
			ba.add(bookAuthorListDto);
		}
		ModelAndView modelAndView = new ModelAndView("genreprofile");
		modelAndView.addObject("booklist", ba);
		modelAndView.addObject("authors",authors);
		modelAndView.addObject("name",genreName);
		modelAndView.addObject("totalpagesb", totalPagesBook);
		modelAndView.addObject("totalpagesa", totalPagesAuthor);
		modelAndView.addObject("genreName", genreName);
		modelAndView.addObject("search", new SearchDto());
		return modelAndView;
	}

}
