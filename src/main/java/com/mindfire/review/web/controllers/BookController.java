package com.mindfire.review.web.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.BookExistException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookAuthorService;
import com.mindfire.review.services.BookService;
import com.mindfire.review.services.ReviewService;
import com.mindfire.review.web.dto.BookAuthorLinkDto;
import com.mindfire.review.web.dto.BookAuthorListDto;
import com.mindfire.review.web.dto.BookDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import com.mindfire.review.web.dto.ReviewBookLikesDto;
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

	public static final String USERNAME = "userName";
	public static final String MODERATOR = "moderator";
	public static final String ADMIN = "admin";
	public static final String ADDBOOK = "addbook";
	public static final String REDIRECTLOGIN = "redirect:/login";
	public static final String REDIRECTBOOKSBOOKID = "redirect:/books/{bookId}";
	public static final String REVIEW = "review";
	public static final String BOOKPROFILE = "bookprofile";
	public static final String DEFAULT_ERROR_VIEW = "resourcenotfound";

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	@Autowired
	private BookAuthorService bookAuthorService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private ReviewService reviewService;

	/**
	 * This method renders the page to add a book and its information to the
	 * database by the admin or the moderator.
	 * 
	 * @param httpSession
	 * @return Object
	 */

	@RequestMapping(value = "/addbook", method = RequestMethod.GET)
	public Object bookController(HttpSession httpSession) {

		if (((ADMIN).equals(httpSession.getAttribute("role")) && httpSession.getAttribute(USERNAME) != null)
				|| (MODERATOR).equals(httpSession.getAttribute("role"))) {

			return new ModelAndView(ADDBOOK, "book", new BookDto());

		} else {

			return REDIRECTLOGIN;
		}
	}

	/**
	 * This method will persist the book along with its information passed by
	 * the admin or moderator, into the database.
	 * 
	 * @param bookDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @return String as a view name
	 */
	@RequestMapping(value = "/addbook", method = RequestMethod.POST)
	public String addBookPost(@Valid @ModelAttribute("book") BookDto bookDto, BindingResult bindingResult, Model model,
			HttpSession httpSession) {

		if (bindingResult.hasErrors()) {
			return ADDBOOK;
		}
		try {

			bookService.addBook(bookDto);
			model.addAttribute("bookName", bookDto.getBookName());
			return "bookadded";

		} catch (BookExistException bex) {
			model.addAttribute("bookExist", bex);
			return ADDBOOK;
		}
	}

	/**
	 * This method is used to mark a book as verified, searched by the book id,
	 * by the admin or moderator.
	 * 
	 * @param bookId
	 * @param choiceDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @return String as a view name
	 */

	@RequestMapping(value = "/books/{bookId}/verifybooks", method = RequestMethod.POST)
	public String verifyBookPost(@PathVariable("bookId") Long bookId,
			@Valid @ModelAttribute("verify") ChoiceDto choiceDto, BindingResult bindingResult, Model model,
			HttpSession httpSession) {
		if (httpSession.getAttribute(USERNAME) != null && ((httpSession.getAttribute("role").equals(ADMIN))
				|| httpSession.getAttribute("role").equals(MODERATOR))) {
			bookService.verifyBook(bookId, choiceDto);
			return REDIRECTBOOKSBOOKID;
		}

		return "null";
	}

	/**
	 * This method render the page displaying all the verified books for the
	 * normal user.
	 * 
	 * @param httpSession
	 * @return Object
	 */
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public Object getAllBooks(@RequestParam(value = "pageno", defaultValue = "1") int pageno, HttpSession httpSession) {

		Page<Book> books = bookService.getVerifiedBook(true, pageno, 6);
		int totalPages = books.getTotalPages();
		List<BookAuthorListDto> bookAuthorListDto = new ArrayList<>();

		for (Book b : books.getContent()) {

			BookAuthorListDto bookAuthorListDto2 = new BookAuthorListDto();
			List<Author> authors = bookService.getAuthorByBook(b.getBookName());
			bookAuthorListDto2.setAuthorList(authors);
			bookAuthorListDto2.setBook(b);
			bookAuthorListDto.add(bookAuthorListDto2);
		}

		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN)
				|| httpSession.getAttribute("role").equals(MODERATOR))) {

			List<Book> bookList = bookService.getBooks(pageno, 6).getContent();

			List<BookAuthorListDto> bookAuthorListDto3 = new ArrayList<>();

			for (Book b : bookList) {
				BookAuthorListDto bookAuthorListDto4 = new BookAuthorListDto();
				List<Author> authorList = bookService.getAuthorByBook(b.getBookName());
				bookAuthorListDto4.setAuthorList(authorList);
				bookAuthorListDto4.setBook(b);
				bookAuthorListDto3.add(bookAuthorListDto4);
			}
			ModelAndView modelAndView = new ModelAndView("adminbooks");
			modelAndView.addObject("totalpages", totalPages);
			modelAndView.addObject("bookauthorlist", bookAuthorListDto3);
			modelAndView.addObject("search", new SearchDto());
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("books");
		modelAndView.addObject("bookauthorlist", bookAuthorListDto);
		modelAndView.addObject("totalpages", totalPages);
		modelAndView.addObject("search", new SearchDto());
		return modelAndView;
	}

	/**
	 * This method renders the book profile page of a book.
	 * 
	 * @param httpSession
	 * @param bookId
	 * @return Object
	 */
	@RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET)
	public Object getBook(@RequestParam(value = "pagenou", defaultValue = "1") int pagenou,
			@RequestParam(value = "pagenor", defaultValue = "1") int pagenor, @PathVariable("bookId") Long bookId,
			HttpSession httpSession) throws BookDoesNotExistException {

		ModelAndView modelAndView = new ModelAndView();

		if (bookService.getBookById(bookId) == null)
			throw new BookDoesNotExistException("book doesnt exist");

		String name = bookService.getBookById(bookId).getBookName();
		List<Author> author = bookService.getAuthorByBook(name);
		Page<ReviewBook> reviewBooks = bookService.getBookReviewByBook(name, pagenor, 6);
		Page<User> users = bookService.getUserByBookReview(name, pagenou, 6);
		int totalPagesr = reviewBooks.getTotalPages();
		int totalPagesu = users.getTotalPages();
		ReviewBookDto reviewBookDto = new ReviewBookDto();

		if (httpSession.getAttribute(REVIEW) != null) {
			reviewBookDto = (ReviewBookDto) httpSession.getAttribute(REVIEW);
		}

		List<ReviewBookLikesDto> reviewBookLikesDtos = new ArrayList<>();

		for (ReviewBook rb : reviewBooks.getContent()) {
			ReviewBookLikesDto reviewBookLikesDto2 = new ReviewBookLikesDto();
			reviewBookLikesDto2.setDislikes(reviewService.getNumberOfReviewDislikesByBook(rb));
			reviewBookLikesDto2.setLikes(reviewService.getNumberOfReviewLikesByBook(rb));
			reviewBookLikesDto2.setReviewBook(rb);
			reviewBookLikesDtos.add(reviewBookLikesDto2);
		}

		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN)
				|| httpSession.getAttribute("role").equals(MODERATOR))) {

			modelAndView.setViewName("adminbookprofile");
			modelAndView.addObject("updatebook", new BookDto());
			modelAndView.addObject("verify", new ChoiceDto());
		}

		else if (bookService.getBookById(bookId).getBookVerified()) {
			modelAndView.setViewName(BOOKPROFILE);
		}

		else {
			throw new BookDoesNotExistException("unauthorised access");
		}

		modelAndView.addObject("reviews", reviewBooks.getContent());
		modelAndView.addObject("book", bookService.getBookById(bookId));
		modelAndView.addObject("users", users);
		modelAndView.addObject("totalpagesr", totalPagesr);
		modelAndView.addObject("totalpagesu", totalPagesu);
		modelAndView.addObject("authors", author);
		modelAndView.addObject("totallikes", bookService.getNumberOfBookLikesByUsers(bookId));
		modelAndView.addObject("totalreviews", bookService.getTotalBookReviewByBook(name));
		modelAndView.addObject(REVIEW, reviewBookLikesDtos);
		modelAndView.addObject(BOOKPROFILE, reviewBookDto);
		modelAndView.addObject("delete", new ChoiceDto());
		return modelAndView;
	}

	/**
	 * This method will increase the likes of a book by a count of 1.
	 * 
	 * @param httpSession
	 * @param bookId
	 * @param httpServletRequest
	 * @param model
	 * @return Object
	 */

	@RequestMapping(value = "/books/{bookId}/addlike", method = RequestMethod.GET)
	public Object addLike(HttpSession httpSession, @PathVariable("bookId") Long bookId,
			HttpServletRequest httpServletRequest, Model model) {

		String userName = (String) httpSession.getAttribute(USERNAME);

		if (userName == null) {

			String url = httpServletRequest.getRequestURI();

			if (url != null) {

				httpSession.setAttribute("url", url);
			}

			return REDIRECTLOGIN;
		}

		try {

			bookService.addBookLikeByUser(userName, bookId);
			return REDIRECTBOOKSBOOKID;

		} catch (AlreadyReviewedException e) {

			logger.error("Already the user has reviewed " + e.getMessage());
			model.addAttribute("exception", e);
			return REDIRECTBOOKSBOOKID;
		}
	}

	/**
	 * This method will decrease the book likes for a book by a count of 1.
	 * 
	 * @param httpSession
	 * @param bookId
	 * @param httpServletRequest
	 * @param model
	 * @return Object
	 */

	@RequestMapping(value = "/books/{bookId}/deletelike", method = RequestMethod.GET)
	public Object deleteLike(HttpSession httpSession, @PathVariable("bookId") Long bookId,
			HttpServletRequest httpServletRequest, Model model) {

		String userName = (String) httpSession.getAttribute(USERNAME);

		if (userName == (null)) {

			String url = httpServletRequest.getRequestURI();

			if (url != null) {
				httpSession.setAttribute("url", url);
			}

			return REDIRECTLOGIN;
		}

		try {
			bookService.removeBookLikeByUser(userName, bookId);
			return REDIRECTBOOKSBOOKID;

		} catch (ReviewDoesnotExistException e) {
			
			logger.warn("The review like does not exist " + e.getMessage());
			model.addAttribute("exception", e);
			return REDIRECTBOOKSBOOKID;
		}
	}

	/**
	 * This method will render the page to update the information for a book by
	 * the admin or moderator
	 * 
	 * @param bookId
	 * @param httpSession
	 * @return Object
	 */
	@RequestMapping(value = "/books/{bookId}/update", method = RequestMethod.GET)
	public Object updateBookGetView(@PathVariable("bookId") Long bookId, HttpSession httpSession) {

		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN)
				|| httpSession.getAttribute("role").equals(MODERATOR))) {

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
			modelAndView.addObject("bookupdatedto", bookDto);
			return modelAndView;

		} else {
			return REDIRECTLOGIN;
		}

	}

	/**
	 * This method persists the book updates entered by the admin or
	 * moderator,in the database.
	 * 
	 * @param bookId
	 * @param bookDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @return String as a view name
	 */
	@RequestMapping(value = "/books/{bookId}", method = RequestMethod.PUT)
	public String updateBook(@PathVariable("bookId") Long bookId,
			@Valid @ModelAttribute("bookupdatedto") BookDto bookDto, BindingResult bindingResult, Model model,
			HttpSession httpSession) throws BookDoesNotExistException{

		if (bindingResult.hasErrors()) {
			return "authorupdate";
		}

		try {
			bookService.updateBook(bookId, bookDto);
			return REDIRECTBOOKSBOOKID;
		} catch (BookDoesNotExistException b) {

			logger.warn("The book to be updated does not exist " + b.getMessage());
			throw b;
		}

	}

	/**
	 * This method deletes a book and all its records from the database and can
	 * be accessed by the admin or moderator.
	 * 
	 * @param bookId
	 * @param httpSession
	 * @param model
	 * @return Object
	 */
	@RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
	public Object removeBook(@PathVariable("bookId") Long bookId, @ModelAttribute("delete") ChoiceDto choiceDto,
			HttpSession httpSession, Model model) throws BookDoesNotExistException {

		if (httpSession.getAttribute(USERNAME) != null && ADMIN.equals(httpSession.getAttribute("role"))) {
			try {
				bookService.removeBook(bookId);
				return "redirect:/books";
			} catch (BookDoesNotExistException b) {
				
				logger.warn("The book to be deleted does not exist " + b.getMessage());
				throw b;
			}
		}
		
		return "null";

	}

	/**
	 * This method renders the page listing all the books and authors and allows
	 * the admin or moderator to link them.
	 * 
	 * @param httpSession
	 * @return Object
	 */
	@RequestMapping(value = "/linkBookAndAuthor", method = RequestMethod.GET)
	public Object getBookAuthorLinkPage(HttpSession httpSession) {

		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN)
				|| httpSession.getAttribute("role").equals(MODERATOR))) {

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

	/**
	 * This method will link the author and the book and persist the change in
	 * the database.
	 * @param bookAuthorLinkDto
	 * @return String as a view name
	 */
	@RequestMapping(value = "/linkBookAndAuthor", method = RequestMethod.POST)
	public String postBookAuthorLinkPage(@ModelAttribute("bookauthorlink") BookAuthorLinkDto bookAuthorLinkDto) {
		try {
			bookAuthorService.linkBookAndAuthor(bookAuthorLinkDto);
			return "redirect:/linkBookAndAuthor";
		} finally {

			return "redirect:/linkBookAndAuthor";
		}
	}

	/**
	 * Exception handling method for book controller
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ FileNotFoundException.class, BookDoesNotExistException.class, UserDoesNotExistException.class })
	public String defaultErrorHandler(HttpServletRequest request, Exception ex) {

		logger.info("Resource not found" + "Error Occured:: URL=" + request.getRequestURL());
		logger.error(ex.getMessage());
		// Send the user to a resource not found error-view.
		return DEFAULT_ERROR_VIEW;
	}
}
