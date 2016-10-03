package com.mindfire.review.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.BookExistException;
import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookAuthorService;
import com.mindfire.review.services.BookService;
import com.mindfire.review.web.dto.BookAuthorLinkDto;
import com.mindfire.review.web.dto.BookAuthorListDto;
import com.mindfire.review.web.dto.BookDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import com.mindfire.review.web.dto.SearchDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;

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
	public Object getAllBooks(@RequestParam(value = "pageno", defaultValue="1") int pageno,HttpSession httpSession) {
		Page<Book> books = bookService.getVerifiedBook(true, pageno,6);
        int totalPages = books.getTotalPages();
		List<BookAuthorListDto> bookAuthorListDto = new ArrayList<>();

		for (Book b : books.getContent()) {

			BookAuthorListDto bookAuthorListDto2 = new BookAuthorListDto();
			List<Author> authors = bookService.getAuthorByBook(b.getBookName());
			bookAuthorListDto2.setAuthorList(authors);
			bookAuthorListDto2.setBook(b);
			bookAuthorListDto.add(bookAuthorListDto2);
		}
		if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin")
				|| httpSession.getAttribute("role").equals("moderator"))) {
			System.out.println("entered admin page");
			List<Book> bookList = bookService.getBooks(pageno,6).getContent(); 
															
			List<BookAuthorListDto> bookAuthorListDto3 = new ArrayList<>();
			for (Book b : bookList) {
				BookAuthorListDto bookAuthorListDto4 = new BookAuthorListDto();
				List<Author> authorList = bookService.getAuthorByBook(b.getBookName());
				bookAuthorListDto4.setAuthorList(authorList);
				bookAuthorListDto4.setBook(b);
				bookAuthorListDto3.add(bookAuthorListDto4);
			}
			ModelAndView modelAndView = new ModelAndView("adminbooks");
			modelAndView.addObject("totalpages",totalPages);
			modelAndView.addObject("bookauthorlist",bookAuthorListDto3);
			modelAndView.addObject("search", new SearchDto());
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("books");
		modelAndView.addObject("bookauthorlist", bookAuthorListDto);
		modelAndView.addObject("totalpages",totalPages);
		modelAndView.addObject("search", new SearchDto());
		return modelAndView;
	}

	/**
	 * returns single book page for all
	 *
	 * @param bookId
	 * @returny
	 */
	@RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET)
	public Object getBook(@RequestParam(value="pagenou", defaultValue="1") int pagenou,@RequestParam(value="pagenor", defaultValue="1") int pagenor,@PathVariable("bookId") Long bookId, HttpSession httpSession) {
		String name = bookService.getBookById(bookId).getBookName();
		List<Author> author = bookService.getAuthorByBook(name);
		Page<ReviewBook> reviewBooks = bookService.getBookReviewByBook(name,pagenor,6);
		Page<User> users = bookService.getUserByBookReview(name, pagenou,6);
		int totalPagesr =reviewBooks.getTotalPages();
		int totalPagesu = users.getTotalPages();
		ReviewBookDto reviewBookDto = new ReviewBookDto();
		
		if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin")
				|| httpSession.getAttribute("role").equals("moderator"))) {
			if(httpSession.getAttribute("review") != null){
				reviewBookDto = (ReviewBookDto) httpSession.getAttribute("review");
			}
			ModelAndView modelAndView = new ModelAndView("adminbookprofile");
			modelAndView.addObject("book", bookService.getBookById(bookId));
			modelAndView.addObject("authors", author);
			modelAndView.addObject("users", users);
			modelAndView.addObject("reviews",reviewBooks.getContent());
			modelAndView.addObject("totalpagesr", totalPagesr);
			modelAndView.addObject("totalpagesu", totalPagesu);
			modelAndView.addObject("updatebook", new BookDto());
			modelAndView.addObject("delete", new ChoiceDto());
			modelAndView.addObject("verify", new ChoiceDto());
			modelAndView.addObject("bookprofile", reviewBookDto);

			return modelAndView;
		}

		ModelAndView modelAndView = new ModelAndView("bookprofile");
		modelAndView.addObject("reviews",reviewBooks.getContent());
		modelAndView.addObject("book", bookService.getBookById(bookId));
		modelAndView.addObject("users", users);
		modelAndView.addObject("totalpagesr", totalPagesr);
		modelAndView.addObject("totalpagesu", totalPagesu);
		modelAndView.addObject("authors", author);
		
		
		if(httpSession.getAttribute("review") != null){
			reviewBookDto = (ReviewBookDto) httpSession.getAttribute("review");
		}
		
		modelAndView.addObject("bookprofile", reviewBookDto);
		modelAndView.addObject("delete", new ChoiceDto());
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
			Book book = bookService.getBookById(bookId);
			modelAndView.addObject("book", book);
			BookDto bookDto = new BookDto();
			bookDto.setBookCost(book.getBookCost());
			bookDto.setBookDescription(book.getBookDescription());
			bookDto.setBookGenre(book.getBookGenre());
			bookDto.setBookIsbn(book.getBookIsbn());
			bookDto.setBookLink(book.getBookLink());
			bookDto.setBookName(book.getBookName());
			bookDto.setBookRating(book.getBookRating());
			bookDto.setBookReview(book.getBookReview());
			modelAndView.addObject("bookupdatedto",bookDto);
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
			return "redirect:/books/{bookId}";
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
	public Object getBookAuthorLinkPage(@RequestParam("pageno") int pageno,HttpSession httpSession) {
		if (httpSession.getAttribute("userName") != null && (httpSession.getAttribute("role").equals("admin")
				|| httpSession.getAttribute("role").equals("moderator"))) {
			ModelAndView modelAndView = new ModelAndView("bookauthorlink", "bookauthorlink", new BookAuthorLinkDto());
			List<Book> books = bookService.getBooks(pageno, 6).getContent();
			List<Author> authors = authorService.getAllAuthor(pageno,6).getContent();
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
		try{
		bookAuthorService.linkBookAndAuthor(bookAuthorLinkDto);
		return "redirect:/linkBookAndAuthor";
		}
		finally{
			System.out.println("caught duplicate enteries");
			return "redirect:/linkBookAndAuthor";
		}
	}

}
