package com.mindfire.review.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.BookExistException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
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
	
	@Autowired
	private BookService bookService;
	@Autowired
	private BookAuthorService bookAuthorService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private ReviewService reviewService;

	/**
	 * get book add page for moderator and admin
	 *
	 * @param httpSession
	 * @return
	 */

	@RequestMapping(value = "/addbook", method = RequestMethod.GET)
	public Object bookController(HttpSession httpSession) {
		if (((ADMIN).equals(httpSession.getAttribute("role")) && httpSession.getAttribute(USERNAME) != null )
				|| (MODERATOR).equals(httpSession.getAttribute("role"))) {

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
	
	/**
	 *to mark the book verified by the admin
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
		if (httpSession.getAttribute(USERNAME) != null && ((httpSession.getAttribute("role").equals(ADMIN))
				|| httpSession.getAttribute("role").equals(MODERATOR))) {
			bookService.verifyBook(bookId, choiceDto);
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
		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN)
				|| httpSession.getAttribute("role").equals(MODERATOR))) {
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
	public Object getBook(@RequestParam(value="pagenou", defaultValue="1") int pagenou,@RequestParam(value="pagenor", defaultValue="1") int pagenor,@PathVariable("bookId") Long bookId, HttpSession httpSession) throws BookDoesNotExistException{
		
		if(bookService.getBookById(bookId) == null)
			throw new BookDoesNotExistException("book doesnt exist");
		String name = bookService.getBookById(bookId).getBookName();
		List<Author> author = bookService.getAuthorByBook(name);
		Page<ReviewBook> reviewBooks = bookService.getBookReviewByBook(name,pagenor,6);
		Page<User> users = bookService.getUserByBookReview(name, pagenou,6);
		int totalPagesr =reviewBooks.getTotalPages();
		int totalPagesu = users.getTotalPages();
		ReviewBookDto reviewBookDto = new ReviewBookDto();
		
		List<ReviewBookLikesDto> reviewBookLikesDtos = new ArrayList<>();
		
		for(ReviewBook rb : reviewBooks.getContent()){
			 ReviewBookLikesDto reviewBookLikesDto2 = new ReviewBookLikesDto();
			 reviewBookLikesDto2.setDislikes(reviewService.getNumberOfReviewDislikesByBook(rb));
			 reviewBookLikesDto2.setLikes(reviewService.getNumberOfReviewLikesByBook(rb));
			 reviewBookLikesDto2.setReviewBook(rb);
			 reviewBookLikesDtos.add(reviewBookLikesDto2);
		}
		
		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN)
				|| httpSession.getAttribute("role").equals(MODERATOR))) {
			if(httpSession.getAttribute("review") != null){
				reviewBookDto = (ReviewBookDto) httpSession.getAttribute("review");
			}
			ModelAndView modelAndView = new ModelAndView("adminbookprofile");
			modelAndView.addObject("book", bookService.getBookById(bookId));
			modelAndView.addObject("authors", author);
			modelAndView.addObject("users", users);
			modelAndView.addObject("reviews",reviewBookLikesDtos);
			modelAndView.addObject("totalpagesr", totalPagesr);
			modelAndView.addObject("totalpagesu", totalPagesu);
			modelAndView.addObject("updatebook", new BookDto());
			modelAndView.addObject("delete", new ChoiceDto());
			modelAndView.addObject("verify", new ChoiceDto());
			modelAndView.addObject("bookprofile", reviewBookDto);
            modelAndView.addObject("totallikes", bookService.getNumberOfBookLikesByUsers(bookId));
            modelAndView.addObject("totalreviews", bookService.getTotalBookReviewByBook(name));
			return modelAndView;
		}

		ModelAndView modelAndView = new ModelAndView("bookprofile");
		modelAndView.addObject("reviews",reviewBooks.getContent());
		modelAndView.addObject("book", bookService.getBookById(bookId));
		modelAndView.addObject("users", users);
		modelAndView.addObject("totalpagesr", totalPagesr);
		modelAndView.addObject("totalpagesu", totalPagesu);
		modelAndView.addObject("authors", author);
		modelAndView.addObject("totallikes", bookService.getNumberOfBookLikesByUsers(bookId));
		modelAndView.addObject("totalreviews", bookService.getTotalBookReviewByBook(name));
		modelAndView.addObject("reviews",reviewBookLikesDtos);
		
		if(httpSession.getAttribute("review") != null){
			reviewBookDto = (ReviewBookDto) httpSession.getAttribute("review");
		}
		
		modelAndView.addObject("bookprofile", reviewBookDto);
		modelAndView.addObject("delete", new ChoiceDto());
		return modelAndView;
	}
	
	/**
	 * to like a book by the user who is logged in
	 * @param httpSession
	 * @param bookId
	 * @param httpServletRequest
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/books/{bookId}/addlike", method = RequestMethod.GET)
	public Object addLike(HttpSession httpSession, @PathVariable("bookId") Long bookId, HttpServletRequest httpServletRequest, Model model){
		
		String userName = (String) httpSession.getAttribute(USERNAME);
		if(userName==null){
			String url = httpServletRequest.getRequestURI();
    		if(url != null)
    		httpSession.setAttribute("url", url);
    		 return "redirect:/login";
		}
		try{
		bookService.addBookLikeByUser(userName, bookId);
		return "redirect:/books/{bookId}";
		}
		catch (AlreadyReviewedException e) {
			model.addAttribute("exception", e);
			return "redirect:/books/{bookId}";
		}
	}
	
	/**
	 * to dislike
	 * @param httpSession
	 * @param bookId
	 * @param httpServletRequest
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/books/{bookId}/deletelike", method = RequestMethod.GET)
	public Object deleteLike(HttpSession httpSession, @PathVariable("bookId") Long bookId, HttpServletRequest httpServletRequest, Model model){
		String userName = (String) httpSession.getAttribute(USERNAME);
		if(userName == (null)){
			String url = httpServletRequest.getRequestURI();
    		if(url != null)
    		httpSession.setAttribute("url", url);
    		 return "redirect:/login";
		}
		
		try{
			bookService.removeBookLikeByUser(userName, bookId);
			return "redirect:/books/{bookId}";
			}
			catch (ReviewDoesnotExistException e) {
				model.addAttribute("exception", e);
				return "redirect:/books/{bookId}";
			}
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
			HttpSession httpSession, Model model) throws BookDoesNotExistException{
		if (httpSession.getAttribute(USERNAME) != null && ADMIN.equals(httpSession.getAttribute("role"))) {
			try {
				bookService.removeBook(bookId);
				return "redirect:/books";
			} catch (BookDoesNotExistException b) {
				throw b;
			}
		}
		return "null";

	}
	
    /**
     * to get the view to set authors for the book 
     * @param httpSession
     * @return
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
			modelAndView.addObject("booklist",bookList);
			modelAndView.addObject("authorlist", authorList);
			return modelAndView;
		}
		return "redirect:/profile";
	}
    
	/**
	 * to set authors for the book 
	 * @param bookAuthorLinkDto
	 * @return
	 */
	@RequestMapping(value = "/linkBookAndAuthor", method = RequestMethod.POST)
	public String postBookAuthorLinkPage(@ModelAttribute("bookauthorlink") BookAuthorLinkDto bookAuthorLinkDto) {
		try{
		bookAuthorService.linkBookAndAuthor(bookAuthorLinkDto);
		return "redirect:/linkBookAndAuthor";
		}
		finally{
			
			return "redirect:/linkBookAndAuthor";
		}
	}

}
