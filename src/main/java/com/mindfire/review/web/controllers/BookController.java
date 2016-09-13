package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.BookExistException;
import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookAuthorService;
import com.mindfire.review.services.BookService;
import com.mindfire.review.web.dto.AuthorDto;
import com.mindfire.review.web.dto.BookAuthorLinkDto;
import com.mindfire.review.web.dto.BookAuthorListDto;
import com.mindfire.review.web.dto.BookDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pratyasa on 3/8/16.
 */
@Controller
public class BookController {
	@Autowired
	private BookService bookService;
	@Autowired
	private BookAuthorService bookAuthorService;
	@Autowired
	private AuthorService authorService;

	/**
	 * get book add page for moderator and admin
	 *
	 * @param httpSession
	 * @return
	 */

	@RequestMapping(value = "/addbook", method = RequestMethod.GET)
	public Object bookController(HttpSession httpSession) {
		if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin")
				|| httpSession.getAttribute("role").equals("moderator"))) {

			return new ModelAndView("addbook", "book", new BookDto());
		} else {

			return "redirect:/login";
		}
	}

	/**
	 * the book add page info to be persisted by admin or moderator
	 *
	 * @param bookDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String addBook(@Valid @ModelAttribute("book") BookDto bookDto, BindingResult bindingResult, Model model,
			HttpSession httpSession) {
		if (bindingResult.hasErrors()) {
			return "addbook";
		}
		try {

			bookService.addBook(bookDto);
			model.addAttribute("bookName", bookDto.getBookName());
			return "bookadded";

		} catch (BookExistException bex) {
			model.addAttribute("bookExist", bex);
			return "addbook";
		}
	}
	// @RequestMapping(value = "/books/{bookId}/verifybooks", method =
	// RequestMethod.GET)
	// public Object verifyBookGet(@PathVariable("bookId") Long bookId,
	// HttpSession httpSession, Model model) {
	// if (httpSession.getAttribute("userName") != null &&
	// ((httpSession.getAttribute("role").equals("admin")) ||
	// httpSession.getAttribute("role").equals("moderator"))) {
	// model.addAttribute("choice", new ChoiceDto());
	// return model;
	// }
	//
	// return "null";
	// }

	/**
	 *
	 * @param bookId
	 * @param choiceDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @return
	 */

	@RequestMapping(value = "/books/{bookId}/verifybooks", method = RequestMethod.POST)
	public String verifyBookPost(@PathVariable("bookId") Long bookId,
			@Valid @ModelAttribute("verify") ChoiceDto choiceDto, BindingResult bindingResult, Model model,
			HttpSession httpSession) {
		if (httpSession.getAttribute("userName") != null && ((httpSession.getAttribute("role").equals("admin"))
				|| httpSession.getAttribute("role").equals("moderator"))) {
			bookService.verifyBook(bookId, choiceDto);
			System.out.println("controller executed");
			return "redirect:/books/{bookId}";
		}

		return "null";
	}

	/**
	 * to get all verified books
	 * 
	 * @return
	 */
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public Object getAllBooks(HttpSession httpSession) {
		List<Book> books = bookService.getVerifiedBook(true);

		List<BookAuthorListDto> bookAuthorListDto = new ArrayList<>();

		for (Book b : books) {

			BookAuthorListDto bookAuthorListDto2 = new BookAuthorListDto();
			List<Author> authors = bookService.getAuthorByBook(b.getBookName());
			bookAuthorListDto2.setAuthorList(authors);
			bookAuthorListDto2.setBook(b);
			bookAuthorListDto.add(bookAuthorListDto2);
		}
		if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin")
				|| httpSession.getAttribute("role").equals("moderator"))) {
			System.out.println("entered admin page");
			List<Book> bookList = bookService.getBooks(); // TODO Add
															// pagination
			List<BookAuthorListDto> bookAuthorListDto3 = new ArrayList<>();
			System.out.println(bookList.get(0).getBookName());
			System.out.println(bookList.get(1).getBookName());
			System.out.println(bookList.get(2).getBookName());
			for (Book b : bookList) {
				BookAuthorListDto bookAuthorListDto4 = new BookAuthorListDto();
				List<Author> authorList = bookService.getAuthorByBook(b.getBookName());
				bookAuthorListDto4.setAuthorList(authorList);
				bookAuthorListDto4.setBook(b);
				bookAuthorListDto3.add(bookAuthorListDto4);
			}
			ModelAndView modelAndView = new ModelAndView("adminbooks");
			modelAndView.addObject("bookauthorlist",bookAuthorListDto3);

			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("books");
		modelAndView.addObject("bookauthorlist", bookAuthorListDto);
		return modelAndView;
	}

	/**
	 * returns single book page for all
	 *
	 * @param bookId
	 * @return
	 */
	@RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET)
	public Object getBook(@PathVariable("bookId") Long bookId, HttpSession httpSession) {
		List<Author> author = bookService.getAuthorByBook(bookService.getBookById(bookId).getBookName());

		if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin")
				|| httpSession.getAttribute("role").equals("moderator"))) {

			ModelAndView modelAndView = new ModelAndView("adminbookprofile");
			modelAndView.addObject("book", bookService.getBookById(bookId));
			modelAndView.addObject("authors", author);
			modelAndView.addObject("updatebook", new BookDto());
			modelAndView.addObject("delete", new ChoiceDto());
			modelAndView.addObject("verify", new ChoiceDto());
			modelAndView.addObject("bookprofile", new ReviewBookDto());

			return modelAndView;
		}

		ModelAndView modelAndView = new ModelAndView("bookprofile");
		modelAndView.addObject("book", bookService.getBookById(bookId));
		modelAndView.addObject("authors", author);
		modelAndView.addObject("bookprofile", new ReviewBookDto());
		return modelAndView;
	}

	/**
	 * the book update page can be requested by the admin or the moderator
	 *
	 * @param bookId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/books/{bookId}/update", method = RequestMethod.GET)
	public Object updateBookGetView(@PathVariable("bookId") Long bookId, HttpSession httpSession) {
		if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin")
				|| httpSession.getAttribute("role").equals("moderator"))) {
			ModelAndView modelAndView = new ModelAndView("bookupdate");
			modelAndView.addObject("bookupdate", bookService.getBookById(bookId));
			modelAndView.addObject("bookupdatedto", new BookDto());
			return modelAndView;
		} else {
			return "redirect:/login";
		}

	}

	/**
	 * the updated info can be persisted by the admin or the moderator
	 *
	 * @param bookId
	 * @param bookDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/books/{bookId}", method = RequestMethod.PUT)
	public String updateBook(@PathVariable("bookId") Long bookId,
			@Valid @ModelAttribute("bookupdatedto") BookDto bookDto, BindingResult bindingResult, Model model,
			HttpSession httpSession) {
		if (bindingResult.hasErrors()) {
			return "authorupdate";
		}
		try {
			bookService.updateBook(bookId, bookDto);
			return "redirect:/books";
		} catch (BookDoesNotExistException b) {
			model.addAttribute("BookDoesNotExist", b);
			return "authorupdate";
		}

	}

	/**
	 * Book can be deleted by the admin
	 * 
	 * @param bookId
	 * @param httpSession
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
	public Object removeBook(@PathVariable("bookId") Long bookId, @ModelAttribute("delete") ChoiceDto choiceDto,
			HttpSession httpSession, Model model) {
		if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin"))) {
			try {
				bookService.removeBook(bookId);
				return "redirect:/books";
			} catch (BookDoesNotExistException b) {
				model.addAttribute("BookDoesNotExist", b);
				return "redirect:/books/{bookId}";
			}
		}
		return "null";

	}

	@RequestMapping(value = "/linkBookAndAuthor", method = RequestMethod.GET)
	public Object getBookAuthorLinkPage(HttpSession httpSession) {
		if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin")
				|| httpSession.getAttribute("role").equals("moderator"))) {
			ModelAndView modelAndView = new ModelAndView("bookauthorlink", "bookauthorlink", new BookAuthorLinkDto());
			List<Book> books = bookService.getBooks();
			List<Author> authors = authorService.getAllAuthor();
			List<String> bookList = new ArrayList<>();
			List<String> authorList = new ArrayList<>();
			for (Book b : books) {
				bookList.add(b.getBookName());
			}
			for (Author a : authors) {
				authorList.add(a.getAuthorName());
			}
			modelAndView.addObject("booklist", bookList);
			modelAndView.addObject("authorlist", authorList);
			return modelAndView;
		}
		return "redirect:/profile";
	}

	@RequestMapping(value = "/linkBookAndAuthor", method = RequestMethod.POST)
	public String postBookAuthorLinkPage(@ModelAttribute("bookauthorlink") BookAuthorLinkDto bookAuthorLinkDto) {
		bookAuthorService.linkBookAndAuthor(bookAuthorLinkDto);
		return "redirect:/linkBookAndAuthor";
	}

}
