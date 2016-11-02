package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.services.ReviewService;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.dto.ReviewAuthorDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by pratyasa on 10/8/16.
 */
@Controller
public class ReviewController {

	public static final String REDIRECTLOGIN = "redirect:/login";
	public static final String REVIEW = "review";
	public static final String USERNAME = "userName";
	public static final String REDIRECTBOOKSBOOKID = "redirect:/books/{bookId}";
	public static final String EXCEPTION = "exception";
	public static final String REDIRECTAUTHORSAUTHORID = "redirect:/authors/{authorId}";
	
	@Autowired
	private ReviewService reviewService;
	
	private void urlCheckAndReviewPreserveBook(String url, ReviewBookDto reviewBookDto, HttpSession httpSession){
		
		if (url != null){
			httpSession.setAttribute("url", url);
		}

		if (httpSession.getAttribute(REVIEW) != null) {
			httpSession.removeAttribute(REVIEW);
		}

		if (reviewBookDto != null){
			httpSession.setAttribute(REVIEW, reviewBookDto);
		}
	}
	
private void urlCheckAndReviewPreserveAuthor(String url, ReviewAuthorDto reviewAuthorDto, HttpSession httpSession){
		
		if (url != null){
			httpSession.setAttribute("url", url);
		}

		if (httpSession.getAttribute(REVIEW) != null) {
			httpSession.removeAttribute(REVIEW);
		}

		if (reviewAuthorDto != null){
			httpSession.setAttribute(REVIEW, reviewAuthorDto);
		}
	}

	/**
	 * to save the review for the book
	 * 
	 * @param bookId
	 * @param reviewBookDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/books/{bookId}", method = RequestMethod.POST)
	public String addBookReviewByUser(@PathVariable("bookId") Long bookId,
			@Valid @ModelAttribute("bookProfile") ReviewBookDto reviewBookDto, BindingResult bindingResult, Model model,
			HttpSession httpSession, HttpServletRequest httpServletRequest) {

		if (httpSession.getAttribute(USERNAME) == null) {
			String url = httpServletRequest.getRequestURI();
			urlCheckAndReviewPreserveBook(url, reviewBookDto, httpSession);
			return REDIRECTLOGIN;
		}
		
		if (bindingResult.hasErrors()) {
			return REDIRECTBOOKSBOOKID;
		}
		
		try {
			reviewService.addBookReview(reviewBookDto, (String) httpSession.getAttribute(USERNAME), bookId);
			return REDIRECTBOOKSBOOKID;
		} catch (AlreadyReviewedException e) {
			model.addAttribute("alreadyReviewedException", e);
			return REDIRECTBOOKSBOOKID;
		}
	}

	/**
	 * to save the review for the author
	 * 
	 * @param httpServletRequest
	 * @param authorId
	 * @param reviewAuthorDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/authors/{authorId}", method = RequestMethod.POST)
	public String addAuthorReviewByUser(HttpServletRequest httpServletRequest, @PathVariable("authorId") Long authorId,
			@Valid @ModelAttribute("authorprofile") ReviewAuthorDto reviewAuthorDto, BindingResult bindingResult,
			Model model, HttpSession httpSession) {
		if (httpSession.getAttribute(USERNAME) == null) {
			String url = httpServletRequest.getRequestURI();
			urlCheckAndReviewPreserveAuthor(url, reviewAuthorDto, httpSession);
			return REDIRECTLOGIN;
		}
		if (bindingResult.hasErrors()) {
			return REDIRECTAUTHORSAUTHORID;
		}
		try {
			reviewService.addAuthorReview(reviewAuthorDto, authorId, (String) httpSession.getAttribute(USERNAME));
			return REDIRECTAUTHORSAUTHORID;
		} catch (AlreadyReviewedException e) {
			model.addAttribute("alreadyreviewedexception", e);
			return REDIRECTAUTHORSAUTHORID;
		}
	}

	/**
	 * to delete the review for the author by admin or the user
	 * 
	 * @param reviewAuthorId
	 * @param httpSession
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authors/{authorId}/reviews/{reviewAuthorId}", method = RequestMethod.DELETE)
	public String removeAuthorReviewByUser(HttpServletRequest httpServletRequest,
			@PathVariable("authorId") Long authorId, @PathVariable("reviewAuthorId") Long reviewAuthorId,
			@ModelAttribute("delete") ChoiceDto choiceDto, HttpSession httpSession, Model model) throws ReviewDoesnotExistException{

		if (httpSession.getAttribute(USERNAME) == null) {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECTLOGIN;
		}

		if ((httpSession.getAttribute(USERNAME).toString()
				.equals(reviewService.getReviewAuthorById(reviewAuthorId).getUser().getUserName()))
				|| ("admin").equals(httpSession.getAttribute("role"))) {

			try {
				reviewService.removeAuthorReview(reviewAuthorId);
				return "redirect:/authors/" + authorId;
			} catch (ReviewDoesnotExistException e) {
				throw e;
				
			}
		}
		return "redirect:/authors";
	}

	/**
	 * to delete the book review
	 * 
	 * @param httpServletRequest
	 * @param bookId
	 * @param reviewBookId
	 * @param choiceDto
	 * @param httpSession
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "books/{bookId}/reviews/{reviewBookId}", method = RequestMethod.DELETE)
	public String removeBookReviewByUser(HttpServletRequest httpServletRequest, @PathVariable("bookId") long bookId,
			@PathVariable("reviewBookId") Long reviewBookId, @ModelAttribute("delete") ChoiceDto choiceDto,
			HttpSession httpSession, Model model) {
		if (httpSession.getAttribute(USERNAME) == null) {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECTLOGIN;
		}

		if ((httpSession.getAttribute(USERNAME).toString()
				.equals(reviewService.getReviewBookById(reviewBookId).getUser().getUserName()))
				|| ("admin").equals(httpSession.getAttribute("role"))) {

			try {
				reviewService.removeBookReview(reviewBookId);
				return "redirect:/books/" + bookId;
			} catch (ReviewDoesnotExistException e) {
				model.addAttribute("reviewdoesnotexistexception", e);
				if (bookId > 0) {
					return "redirect:/books/" + bookId;
				}
			}
		}
		return "redirect:/books";
	}

	/**
	 * to get the page to update the author review
	 * 
	 * @param authorId
	 * @param reviewAuthorId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/authors/{authorId}/reviews/{reviewAuthorId}/update", method = RequestMethod.GET)
	public Object updateAuthorReviewByUser(@PathVariable("authorId") Long authorId,
			@PathVariable("reviewAuthorId") Long reviewAuthorId, HttpSession httpSession) {
		if (httpSession.getAttribute(USERNAME) == null) {
			return REDIRECTLOGIN;
		}
		if (httpSession.getAttribute(USERNAME) == reviewService.getReviewAuthorById(reviewAuthorId).getUser()
				.getUserName()) {
			ModelAndView modelAndView = new ModelAndView("authorprofileupdate");
			modelAndView.addObject("authorprofile", new ReviewAuthorDto());
			modelAndView.addObject("reviewauthor", reviewService.getReviewAuthorById(reviewAuthorId));
			return modelAndView;
		}
		return REDIRECTAUTHORSAUTHORID;

	}

	/**
	 * to update the author review
	 * 
	 * @param authorId
	 * @param reviewAuthorId
	 * @param reviewAuthorDto
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authors/{authorId}/reviews/{reviewAuthorId}", method = RequestMethod.PUT)
	public String updateAuthorReviewPost(@PathVariable("authorId") Long authorId,
			@PathVariable("reviewAuthorId") Long reviewAuthorId,
			@Valid @ModelAttribute("authorprofile") ReviewAuthorDto reviewAuthorDto, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/authors/{authorId}/{reviewAuthorId}/update";
		}
		try {
			reviewService.updateAuthorReview(reviewAuthorDto, reviewAuthorId);
			return REDIRECTAUTHORSAUTHORID;
		} catch (ReviewDoesnotExistException e) {
			model.addAttribute("reviewdoesnotexist", e);
			return "redirect:/authors/{authorId}/update";
		}
	}

	/**
	 * to get the page to update the book review
	 * 
	 * @param bookId
	 * @param reviewBookId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/books/{bookId}/reviews/{reviewBookId}/update", method = RequestMethod.GET)
	public Object updateBookReviewByUser(@PathVariable("bookId") Long bookId,
			@PathVariable("reviewBookId") Long reviewBookId, HttpSession httpSession) {
		if (httpSession.getAttribute(USERNAME) == null) {
			return REDIRECTLOGIN;
		}
		if (httpSession.getAttribute(USERNAME) == reviewService.getReviewBookById(reviewBookId).getUser()
				.getUserName()) {
			ModelAndView modelAndView = new ModelAndView("bookprofileupdate");
			modelAndView.addObject("reviewbook", reviewService.getReviewBookById(reviewBookId));
			modelAndView.addObject("bookprofileupdate", new ReviewBookDto());
			return modelAndView;
		}
		return REDIRECTBOOKSBOOKID;
	}

	/**
	 * to update the book review
	 * 
	 * @param bookId
	 * @param reviewBookId
	 * @param reviewBookDto
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/books/{bookId}/reviews/{reviewBookId}", method = RequestMethod.PUT)
	public String updateBookReviewByUserPost(@PathVariable("bookId") Long bookId,
			@PathVariable("reviewBookId") Long reviewBookId,
			@Valid @ModelAttribute("bookprofile") ReviewBookDto reviewBookDto, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/allauthors/{bookId}/{reviewBookId}/update";
		}
		try {
			reviewService.updateBookReview(reviewBookDto, reviewBookId);
			return REDIRECTBOOKSBOOKID;
		} catch (ReviewDoesnotExistException e) {
			model.addAttribute("reviewdoesnotexist", e);
			return "redirect:/books/{bookId}/{reviewBookId}/update";
		}
	}

	/**
	 * to like the book review
	 * 
	 * @param httpSession
	 * @param bookId
	 * @param reviewBookId
	 * @param httpServletRequest
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/books/{bookId}/reviews/{reviewBookId}/addlike", method = RequestMethod.GET)
	public Object addLike(HttpSession httpSession, @PathVariable("bookId") Long bookId,
			@PathVariable("reviewBookId") Long reviewBookId, HttpServletRequest httpServletRequest, Model model) {
		String userName = (String) httpSession.getAttribute(USERNAME);
		if (userName == null) {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECTLOGIN;
		}

		try {
			reviewService.addLikeForBookReview(userName, reviewBookId);
			return REDIRECTBOOKSBOOKID;
		} catch (AlreadyReviewedException e) {
			model.addAttribute(EXCEPTION, e);
			return REDIRECTBOOKSBOOKID;
		}
	}

	/**
	 * to dislike a book review
	 * 
	 * @param httpSession
	 * @param bookId
	 * @param reviewBookId
	 * @param httpServletRequest
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/books/{bookId}/reviews/{reviewBookId}/deletelike", method = RequestMethod.GET)
	public Object deleteLike(HttpSession httpSession, @PathVariable("bookId") Long bookId,
			@PathVariable("reviewBookId") Long reviewBookId, HttpServletRequest httpServletRequest, Model model) {
		String userName = (String) httpSession.getAttribute(USERNAME);
		if (userName == (null)) {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECTLOGIN;
		}

		try {
			reviewService.removeLikeForBookReview(userName, reviewBookId);
			return REDIRECTBOOKSBOOKID;
		} catch (ReviewDoesnotExistException e) {
			model.addAttribute(EXCEPTION, e);
			return REDIRECTBOOKSBOOKID;
		}
	}

	/**
	 * to add author review like
	 * 
	 * @param httpSession
	 * @param authorId
	 * @param reviewAuthorId
	 * @param httpServletRequest
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authors/{authorId}/reviews/{reviewAuthorId}/addlike", method = RequestMethod.GET)
	public Object addAuthorLike(HttpSession httpSession, @PathVariable("authorId") Long authorId,
			@PathVariable("reviewAuthorId") Long reviewAuthorId, HttpServletRequest httpServletRequest, Model model) {
		String userName = (String) httpSession.getAttribute(USERNAME);
		if (userName == null) {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECTLOGIN;
		}
		try {
			reviewService.addLikeForAuthorReview(userName, reviewAuthorId);
			return REDIRECTAUTHORSAUTHORID;
		} catch (AlreadyReviewedException e) {
			model.addAttribute(EXCEPTION, e);
			return REDIRECTAUTHORSAUTHORID;
		}
	}

	/**
	 * to dislike author review
	 * 
	 * @param httpSession
	 * @param authorId
	 * @param reviewAuthorId
	 * @param httpServletRequest
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authors/{authorId}/reviews/{reviewAuthorId}/deletelike", method = RequestMethod.GET)
	public Object deleteAuthorLike(HttpSession httpSession, @PathVariable("authorId") Long authorId,
			@PathVariable("reviewAuthorId") Long reviewAuthorId, HttpServletRequest httpServletRequest, Model model) {
		String userName = (String) httpSession.getAttribute(USERNAME);
		if (userName == null) {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECTLOGIN;
		}

		try {
			reviewService.removeLikeForAuthorReview(userName, reviewAuthorId);
			return REDIRECTAUTHORSAUTHORID;
		} catch (ReviewDoesnotExistException e) {
			model.addAttribute(EXCEPTION, e);
			return "/authors/{authorId}";
		}
	}

}
