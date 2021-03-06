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
import com.mindfire.review.exceptions.AuthorExistenceException;
import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookService;
import com.mindfire.review.services.ReviewService;
import com.mindfire.review.web.dto.AuthorDto;
import com.mindfire.review.web.dto.BookAuthorListDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.dto.ReviewAuthorDto;
import com.mindfire.review.web.dto.ReviewAuthorLikesDto;
import com.mindfire.review.web.dto.SearchDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.User;

/**
 * @author 
 * Created by pratyasa on 10/8/16.
 */
@Controller
public class AuthorController {
	
	public static final String REDIRECT = "redirect:/login";
	public static final String USERNAME = "userName";
	public static final String ADMIN = "admin";
	public static final String MODERATOR = "moderator";
	public static final String ADDAUTHOR = "addauthor";
	public static final String REVIEW = "review";
	public static final String AUTHOR = "author";
	public static final String AUTHORUPDATE = "authorupdate";
	public static final String REDIRECTAUTHORAUTHORID = "redirect:/authors/{authorId}";
	public static final String DELETE = "delete";
	public static final String AUTHORPROFILE = "authorprofile";
	public static final String DEFAULT_ERROR_VIEW = "resourcenotfound";
	
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookService bookService;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
	

	/**
	 * This method renders the page to add author.
	 * This route is accessed by the admin or moderator only.
	 * @param httpSession
	 * @return String as a view name
	 */ 
	
	@RequestMapping(value = "/addauthor", method = RequestMethod.GET)
	public Object addAuthorGet(HttpSession httpSession) {
		
		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN)
				|| httpSession.getAttribute("role").equals(MODERATOR))) {
			
			ModelAndView modelAndView = new ModelAndView(ADDAUTHOR);
			modelAndView.addObject(ADDAUTHOR, new AuthorDto());
			
			return modelAndView;
		}
		
		return REDIRECT;

	}

	/**
	 * This method is used to add the author to the database.
	 * The post method is accessed by the admin or moderator.
	 * @param authorDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @return String as a view name
	 */

	@RequestMapping(value = "/addauthor", method = RequestMethod.POST)
	public String addAuthorPost(@Valid @ModelAttribute(ADDAUTHOR) AuthorDto authorDto, BindingResult bindingResult,
			Model model, HttpSession httpSession, HttpServletRequest request) {
		
		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN)
				|| httpSession.getAttribute("role").equals(MODERATOR))) {
			
			if (bindingResult.hasErrors()){
				
				model.addAttribute(ADDAUTHOR, new AuthorDto());
				
				return ADDAUTHOR;
			}
			

				try {
					
					authorService.addAuthor(authorDto);
					
				} catch (AuthorExistenceException e) {
					
					logger.warn("Author exists" + e.getMessage());
					model.addAttribute("authorexists", e);
					return ADDAUTHOR;
					
				}
				
				httpSession.setAttribute("authorName", authorDto.getAuthorName());
				return "authoradded";
			
		}
		return REDIRECT;
	}

	/**
	 * This method renders the all authors page for all the users.
	 * @return ModelAndView
	 */

	@RequestMapping(value = "/authors", method = RequestMethod.GET)
	public ModelAndView getAllAuthors(@RequestParam(value = "pageno", defaultValue = "1") int pageno) {
		
		Page<Author> authorpage = authorService.getAllAuthor(pageno, 6);
		List<Author> authors = authorpage.getContent();
		int totalPage = authorpage.getTotalPages();
		ModelAndView modelAndView = new ModelAndView("authors");
		modelAndView.addObject("totalpage", totalPage);
		modelAndView.addObject("authors", authors);
		modelAndView.addObject("search", new SearchDto());
		return modelAndView;
	}

	/**
	 * This method renders two different views of the author profile page.
	 * If the user is an admin, it will return an author admin profile page.
	 * If the user is not logged in or is a normal user, it will return a  normal author profile page. 
	 * @param authorId
	 * @param httpSession
	 * @return Object
	 */

	@RequestMapping(value = "/authors/{authorId}", method = RequestMethod.GET)
	public Object getSingleAuthor(@RequestParam(value = "pagenob", defaultValue = "1") int pagenob,
			@RequestParam(value = "pagenou", defaultValue = "1") int pagenou,
			@RequestParam(value = "pagenor", defaultValue = "1") int pagenor, @PathVariable("authorId") Long authorId,
			HttpSession httpSession) throws AuthorExistenceException {
		
		ModelAndView modelAndView = new ModelAndView();
		String authorName = authorService.getAuthorById(authorId).getAuthorName();
		
		if(authorName == null) {
			throw new AuthorExistenceException("author doesnt exist");
		}
		
		Page<Book> book = authorService.getBookByAuthor(authorName, pagenob, 6);
		Page<ReviewAuthor> reviewAuthors = authorService.getAuthorReviewByAuthorName(authorName, pagenob, 6);
		Page<User> users = authorService.getUserByAuthor(authorName, pagenob, 6);
		int totalpagesr = reviewAuthors.getTotalPages();
		int totalpagesb = book.getTotalPages();
		int totalpagesu = users.getTotalPages();
		List<BookAuthorListDto> bookAuthorListDto = new ArrayList<>();
		ReviewAuthorDto reviewAuthorDto = new ReviewAuthorDto();
		List<ReviewAuthorLikesDto> reviewAuthorLikesDtos = new ArrayList<>();
		
		if (httpSession.getAttribute(REVIEW) != null) {
			reviewAuthorDto = (ReviewAuthorDto) httpSession.getAttribute(REVIEW);
		}

		for (ReviewAuthor ra : reviewAuthors.getContent()) {
			ReviewAuthorLikesDto reviewAuthorLikesDto2 = new ReviewAuthorLikesDto();
			reviewAuthorLikesDto2.setLikes(reviewService.getNumberOfReviewLikesByAuthor(ra));
			reviewAuthorLikesDto2.setDislikes(reviewService.getNumberOfReviewDislikesByAuthor(ra));
			reviewAuthorLikesDto2.setReviewAuthor(ra);
			reviewAuthorLikesDtos.add(reviewAuthorLikesDto2);
		}

		for (Book b : book.getContent()) {

			BookAuthorListDto bookAuthorListDto2 = new BookAuthorListDto();
			List<Author> authors = bookService.getAuthorByBook(b.getBookName());
			bookAuthorListDto2.setAuthorList(authors);
			bookAuthorListDto2.setBook(b);
			bookAuthorListDto.add(bookAuthorListDto2);
		}

		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN)
				|| httpSession.getAttribute("role").equals(MODERATOR))) {
			
			modelAndView .setViewName("adminauthorprofile");
			modelAndView.addObject("authorId", authorId);
		}
		
		else{
			modelAndView .setViewName(AUTHORPROFILE);
		}
		
		modelAndView.addObject(AUTHOR, authorService.getAuthorById(authorId));
		modelAndView.addObject(AUTHORPROFILE, reviewAuthorDto);
		modelAndView.addObject("reviews", reviewAuthorLikesDtos);
		modelAndView.addObject("users", users.getContent());
		modelAndView.addObject("books", bookAuthorListDto);
		modelAndView.addObject("totalpagesr", totalpagesr);
		modelAndView.addObject("totalpagesb", totalpagesb);
		modelAndView.addObject("totalpagesu", totalpagesu);
		modelAndView.addObject(DELETE, new ChoiceDto());
		modelAndView.addObject("totalreviews", authorService.getTotalAuthorReviewByAuthorName(authorName));
		modelAndView.addObject("totallikes", authorService.getNumberOfLikesByUser(authorId));
		return modelAndView;
	}

	/**
	 * This method renders the update page for the user account
	 * @param authorId
	 * @param httpSession
	 * @return Object
	 */

	@RequestMapping(value = "/authors/{authorId}/update", method = RequestMethod.GET)
	public Object updateAuthorGet(@PathVariable("authorId") Long authorId, HttpSession httpSession) {
		
		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN)
				|| httpSession.getAttribute("role").equals(MODERATOR))) {
			
			ModelAndView modelAndView = new ModelAndView(AUTHORUPDATE);
			modelAndView.addObject(AUTHOR, authorService.getAuthorById(authorId));
			Author author = authorService.getAuthorById(authorId);
			AuthorDto authorDto = new AuthorDto();
			authorDto.setAuthorDescription(author.getAuthorDescription());
			authorDto.setAuthorGenre(author.getAuthorGenre());
			authorDto.setAuthorName(author.getAuthorName());
			authorDto.setAuthorRating(author.getAuthorRating());
			modelAndView.addObject("authorupdatedto", authorDto);
			return modelAndView;
		}
		
		return REDIRECTAUTHORAUTHORID;
	}

	/**
	 * This method increases the like of an author by 1.
	 * @param httpSession
	 * @param bookId
	 * @param httpServletRequest
	 * @param model
	 * @return Object
	 */

	@RequestMapping(value = "/authors/{authorId}/addlike", method = RequestMethod.GET)
	public Object addAuthorLike(HttpSession httpSession, @PathVariable("authorId") Long bookId,
			HttpServletRequest httpServletRequest, Model model) {
		
		String userName = (String) httpSession.getAttribute(USERNAME);
		
		if (userName == null) {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECT;
		}
		
		try {
			
			authorService.addAuthorLikeByUser(userName, bookId);
			return REDIRECTAUTHORAUTHORID;
			
		} catch (AlreadyReviewedException e) {
			
			logger.warn("The user has already liked " +e.getMessage());
			model.addAttribute("exception", e);
			return REDIRECTAUTHORAUTHORID;
		}
	}

	/**
	 * This method is used to remove the like of an author by 1.
	 * @param httpSession
	 * @param bookId
	 * @param httpServletRequest
	 * @param model
	 * @return Object
	 */

	@RequestMapping(value = "/authors/{authorId}/deletelike", method = RequestMethod.GET)
	public Object deleteLike(HttpSession httpSession, @PathVariable("authorId") Long bookId,
			HttpServletRequest httpServletRequest, Model model) {
		
		String userName = (String) httpSession.getAttribute(USERNAME);
		if (userName == null) {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECT;
		}

		try {
			authorService.removeAuthorLikeByUser(userName, bookId);
			return REDIRECTAUTHORAUTHORID;
		} catch (ReviewDoesnotExistException e) {
			
			logger.warn("The like doesnt exist to be deleted " +e.getMessage());
			model.addAttribute("exception", e);
			return "/authors/{authorId}";
		}
	}

	/**
	 * This method saves the updated info about the user and updates the database
	 * @param authorId
	 * @param authorDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @return String as a view name.
	 */
	@RequestMapping(value = "/authors/{authorId}", method = RequestMethod.PUT)
	public String updateAuthorPost(@PathVariable("authorId") Long authorId,
			@Valid @ModelAttribute("authorupdatedto") AuthorDto authorDto, BindingResult bindingResult, Model model,
			HttpSession httpSession) {
		
		if (bindingResult.hasErrors()) {
			return AUTHORUPDATE;
		}
		try {
			authorService.updateAuthor(authorDto, authorId);
			return REDIRECTAUTHORAUTHORID;
		} catch (AuthorExistenceException e) {
			
			logger.error("The author to be updated doesnt exist " +e.getMessage());
			model.addAttribute("authordoesnotexist", e);
			return AUTHORUPDATE;
		}
	}

	/**
	 * This method is used to delete the records of an author from the database.
	 * @param authorId
	 * @param choiceDto
	 * @param model
	 * @param httpSession
	 * @return String as a view name
	 */
	@RequestMapping(value = "/authors/{authorId}", method = RequestMethod.DELETE)
	public String removeAuthor(@PathVariable("authorId") Long authorId,
			@Valid @ModelAttribute(DELETE) ChoiceDto choiceDto, Model model, HttpSession httpSession) {
		if (httpSession.getAttribute(USERNAME) != null && (httpSession.getAttribute("role").equals(ADMIN))) {
			try {
				authorService.removeAuthor(authorId);
				return "redirect:/authors";
			} catch (AuthorExistenceException e) {
				
				logger.error("The author to be deleted doesnt exist "+ e.getMessage());
				model.addAttribute("authordoesnotexist", e);
				return REDIRECTAUTHORAUTHORID;
			}
		}
		return "redirect/login";
	}
	
	/**
	 * Exception handling method for author controller
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({FileNotFoundException.class,BookDoesNotExistException.class, UserDoesNotExistException.class})
	public String defaultErrorHandler(HttpServletRequest request, Exception ex) {
		
		logger.error("Resource not found"+" Error Occured:: URL="+request.getRequestURL());
		// Send the user to a resource not found error-view.
		return DEFAULT_ERROR_VIEW;
	}
}
