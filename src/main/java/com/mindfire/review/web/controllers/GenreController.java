/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookService;
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
	public String getGenre(@PathVariable("genreName") String genreName, Model model){
		List<Book> bookList = bookService.getBookByGenre(genreName);
		List<Author> authorList = authorService.getAuthorByGenre(genreName);
		model.addAttribute("booklist", bookList);
		model.addAttribute("authorlist",authorList);
		return "genreprofile";
	}

}
