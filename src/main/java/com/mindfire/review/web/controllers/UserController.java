/**
 *
 * Created by pratyasa on 11/8/16.
 */
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

import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.services.BookService;
import com.mindfire.review.services.EmailService;
import com.mindfire.review.services.ReviewService;
import com.mindfire.review.services.UserService;
import com.mindfire.review.web.dto.BookAuthorListDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.dto.SearchDto;
import com.mindfire.review.web.dto.UserUpdateDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.User;

/**
 * 
 */
@Controller
public class UserController {

	public static final String USERNAME = "userName";
	public static final String USERUPDATE = "userupdate";
	public static final String ADMIN = "admin";
	public static final String USERID = "userId";
	public static final String NORMAL = "normal";
	public static final String DEFAULT_ERROR_VIEW = "resourcenotfound";

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private EmailService emailService;

	/**
	 * to get the all user list only by the admin
	 * 
	 * @param pageno
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Object getUsers(@RequestParam(value = "pageno", defaultValue = "1") int pageno, HttpSession httpSession) {

		if (httpSession.getAttribute(USERNAME) != null && (ADMIN).equals(httpSession.getAttribute("role"))) {
			Page<User> users = userService.getUsers(pageno, 6);
			ModelAndView modelAndView = new ModelAndView("users");
			modelAndView.addObject("userslist", users.getContent());
			modelAndView.addObject("totalpages", users.getTotalPages());
			modelAndView.addObject("search", new SearchDto());
			return modelAndView;
		}
		return "redirect:/profile";
	}

	/**
	 * to get the user profile page
	 * 
	 * @param pagenob
	 * @param pagenoa
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public Object getUser(@RequestParam(value = "pagenob", defaultValue = "1") int pagenob,
			@RequestParam(value = "pagenoa", defaultValue = "1") int pagenoa, @PathVariable(USERID) Long userId,
			HttpSession httpSession) throws UserDoesNotExistException {

		if (userService.getUserById(userId) == null) {
			throw new UserDoesNotExistException("User doesnot exist or unauthorised access");
		}

		ModelAndView modelAndView = null;
		if ((httpSession.getAttribute(USERNAME) != null && httpSession.getAttribute("role").equals(ADMIN))
				|| (httpSession.getAttribute(USERNAME) != null && httpSession.getAttribute(USERID).equals(userId))) {
			Page<Book> bookList = userService.getBookReviewedByUser(userService.getUserById(userId).getUserName(),
					pagenob, 4);
			Page<Author> authorList = userService.getAuthorReviewedByUser(userService.getUserById(userId).getUserName(),
					pagenoa, 4);
			int totalpagesb = bookList.getTotalPages();
			int totalpagesa = authorList.getTotalPages();
			List<BookAuthorListDto> ba = new ArrayList<>();
			for (Book b : bookList.getContent()) {
				BookAuthorListDto bookAuthorListDto = new BookAuthorListDto();
				List<Author> a = bookService.getAuthorByBook(b.getBookName());
				bookAuthorListDto.setAuthorList(a);
				bookAuthorListDto.setBook(b);
				ba.add(bookAuthorListDto);
			}
			if ((httpSession.getAttribute(USERNAME) != null && httpSession.getAttribute("role").equals(ADMIN))
					|| (httpSession.getAttribute(USERNAME) != null
							&& ("moderator").equals(httpSession.getAttribute("role")))) {
				modelAndView = new ModelAndView(ADMIN);
				modelAndView.addObject("user", userService.getUser((String) httpSession.getAttribute(USERNAME)));
			} else if (httpSession.getAttribute(USERNAME) != null
					&& (NORMAL).equals(httpSession.getAttribute("role"))) {
				String password = userService.getUserById(userId).getUserPassword();
				System.out.print(password);
				modelAndView = new ModelAndView("userprofile");
				modelAndView.addObject("user", userService.getUserById(userId));
			}

			modelAndView.addObject("books", ba);
			modelAndView.addObject("authors", authorList.getContent());
			modelAndView.addObject("totalpagesb", totalpagesb);
			modelAndView.addObject("totalpagesa", totalpagesa);
			modelAndView.addObject("totalreviewlikes",
					reviewService.getNumberOfReviewLikesByUser(userService.getUserById(userId)));
			modelAndView.addObject("totallikes", userService.totalLikesByUser(userService.getUserById(userId)));
			modelAndView.addObject("delete", new ChoiceDto());
			modelAndView.addObject("totalreviewsmade",
					userService.totalReviewsMadeByTheUser(userService.getUserById(userId)));
			return modelAndView;
		}

		return null;

	}

	/**
	 * to update the user details (not used)
	 * 
	 * @param userId
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/users/{userId}/update", method = RequestMethod.GET)
	public Object updateUserGet(@PathVariable(USERID) Long userId, HttpSession httpSession)
			throws UserDoesNotExistException {

		if (httpSession.getAttribute(USERNAME) != null && httpSession.getAttribute(USERID).equals(userId)) {

			User user = userService.getUserById(userId);
			ModelAndView modelAndView = new ModelAndView(USERUPDATE);
			modelAndView.addObject(USERUPDATE, new UserUpdateDto());
			modelAndView.addObject("user", user);
			return modelAndView;
		}
		throw new UserDoesNotExistException("User doesnot exist or unauthorised access");
	}

	/**
	 * to update user details
	 * 
	 * @param userId
	 * @param signupDto
	 * @param bindingResult
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "users/{userId}", method = RequestMethod.PUT)
	public Object updateUserPost(@PathVariable(USERID) Long userId,
			@Valid @ModelAttribute(USERUPDATE) UserUpdateDto userUpdateDto, BindingResult bindingResult, Model model,
			HttpSession httpSession) {
		if (bindingResult.hasErrors()) {
			return USERUPDATE;
		}
		if (httpSession.getAttribute(USERNAME) != null && httpSession.getAttribute(USERID).equals(userId)) {
			try {
				userService.updateUser(userId, userUpdateDto);
				logger.warn("success");
				httpSession.invalidate();
				return "redirect:/login";
			} catch (UserDoesNotExistException e) {

				logger.warn(e.getLocalizedMessage());
				model.addAttribute("userdoesnotexist", e);
				return "redirect:/users/{userId}";
			}
		}
		return null;

	}

	/**
	 * to delete an account by the admin or the user
	 * 
	 * @param userId
	 * @param httpSession
	 * @param model
	 * @param choiceDto
	 * @return
	 */
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public String removeAccout(@PathVariable(USERID) Long userId, HttpSession httpSession, Model model,
			@ModelAttribute("delete") ChoiceDto choiceDto) throws UserDoesNotExistException {
		if (httpSession.getAttribute(USERNAME) == null) {
			return "redirect:/login";
		}
		if ((ADMIN).equals(httpSession.getAttribute("role")) || ((NORMAL).equals(httpSession.getAttribute("role"))
				&& httpSession.getAttribute(USERID).equals(userId))) {

			try {
				userService.removeUser(userId);
				httpSession.invalidate();
				return "redirect:/";
			} catch (UserDoesNotExistException e) {
				throw e;
			}

		}
		return null;

	}

	/**
	 * to redirect towards the user account
	 * 
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public Object getProfile(HttpSession httpSession) {
		if (httpSession.getAttribute(USERNAME) != null) {
			if ((ADMIN).equals(httpSession.getAttribute("role"))
					|| ("moderator").equals(httpSession.getAttribute("role"))) {

				Long userId = (Long) httpSession.getAttribute(USERID);
				return "redirect:/users/" + userId;
			}
			if ((NORMAL).equals(httpSession.getAttribute("role"))) {

				Long userId = (Long) httpSession.getAttribute(USERID);
				return "redirect:/users/" + userId;
			}
		}

		return "redirect:/";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/verification", method = RequestMethod.GET)
	public Object getUpdateForm() {

		ModelAndView modelAndView = new ModelAndView("usernameform");
		modelAndView.addObject("verification", new ChoiceDto());

		return modelAndView;
	}

	/**
	 * 
	 * @param choiceDto
	 * @param bindingResult
	 * @return
	 * @throws UserDoesNotExistException
	 */
	@RequestMapping(value = "/verification", method = RequestMethod.POST)
	public String getUpdateForm(@ModelAttribute("verification") ChoiceDto choiceDto, BindingResult bindingResult)
			throws UserDoesNotExistException {

		User user = userService.getUser(choiceDto.getChoice());

		if (user == null) {
			logger.error("invalid user name.");
			throw new UserDoesNotExistException("The user name is invalid");
		}

		Long id = user.getUserId();

		// Invokes the method to send email to the user containing a random
		// verification code.
		emailService.forgotPasswordEmail(id);

		return "redirect:/verificationprocess/" + id;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/verificationprocess/{userId}", method = RequestMethod.GET)
	public Object getForm(@PathVariable("userId") Long userId) {

		ModelAndView modelAndView = new ModelAndView("forgotpassword");
		modelAndView.addObject("verificationcode", new ChoiceDto());
		modelAndView.addObject("id", userId);
		return modelAndView;
	}

	/**
	 * 
	 * @param choiceDto
	 * @param bindingResult
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/verificationprocess/{userId}", method = RequestMethod.POST)
	public Object sendForm(@ModelAttribute("verificationcode") ChoiceDto choiceDto, BindingResult bindingResult,
			@PathVariable("userId") Long userId, HttpSession httpSession) {

		if (userService.forgotPasswordService(choiceDto, userId)) {

			User user = userService.getUserById(userId);
			httpSession.setAttribute(USERNAME, user.getUserName());
			httpSession.setAttribute(USERID, userId);
			ModelAndView modelAndView = new ModelAndView(USERUPDATE);
			modelAndView.addObject(USERUPDATE, new UserUpdateDto());
			modelAndView.addObject("user", user);

			return modelAndView;
		}

		logger.info("Authentication failed.");

		return "redirect:/verificationprocess/{userId}";
	}

	/**
	 * Exception handler method for the controller
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ FileNotFoundException.class, BookDoesNotExistException.class, UserDoesNotExistException.class })
	public String defaultErrorHandler(HttpServletRequest request, Exception ex) {

		logger.error("Resource not found" + " Error Occured:: URL=" + request.getRequestURL());
		// Send the user to a resource not found error-view.
		return DEFAULT_ERROR_VIEW;
	}

}
