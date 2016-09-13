/**
 * 
 */
package com.mindfire.review.web.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookService;
import com.mindfire.review.web.dto.BookAuthorListDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.BookAuthor;

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
	public Object getGenre(@PathVariable("genreName") String genreName){
		List<Book> bookList = bookService.getBookByGenre(genreName);
		List<Author> authorList = authorService.getAuthorByGenre(genreName);
		List<BookAuthorListDto> ba = new ArrayList<>();
		for (Book b:bookList){
			BookAuthorListDto bookAuthorListDto = new BookAuthorListDto();
			List<Author> a = bookService.getAuthorByBook(b.getBookName());
			bookAuthorListDto.setAuthorList(a);
			bookAuthorListDto.setBook(b);
			ba.add(bookAuthorListDto);
		}
		ModelAndView modelAndView = new ModelAndView("genreprofile");
		modelAndView.addObject("booklist", ba);
		modelAndView.addObject("authors",authorList);
		modelAndView.addObject("name",genreName);
		return modelAndView;
	}

}
