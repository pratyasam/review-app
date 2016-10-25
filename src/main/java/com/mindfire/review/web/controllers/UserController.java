/**
 *
 * Created by pratyasa on 11/8/16.
 */
package com.mindfire.review.web.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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

import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.services.BookService;
import com.mindfire.review.services.ReviewService;
import com.mindfire.review.services.UserService;
import com.mindfire.review.web.dto.BookAuthorListDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.dto.SearchDto;
import com.mindfire.review.web.dto.SignupDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.User;

/**
 * 
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;
	@Autowired
	private ReviewService reviewService;
	
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Object getUsers(@RequestParam(value = "pageno", defaultValue="1") int pageno,HttpSession httpSession) {

		if (httpSession.getAttribute("userName") != null && httpSession.getAttribute("role").equals("admin")) {
			Page<User> users = userService.getUsers(pageno, 6);
			ModelAndView modelAndView = new ModelAndView("users");
			modelAndView.addObject("userslist", users.getContent());
			modelAndView.addObject("totalpages",users.getTotalPages());
			modelAndView.addObject("search", new SearchDto());
			return modelAndView;
		}
		return "redirect:/profile";
	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public Object getUser(@RequestParam(value = "pagenob", defaultValue="1") int pagenob, @RequestParam(value = "pagenoa", defaultValue="1") int pagenoa, @PathVariable("userId") Long userId, HttpSession httpSession) {
		if ((httpSession.getAttribute("userName") != null && httpSession.getAttribute("role").equals("admin")) || (httpSession.getAttribute("userName") != null && httpSession.getAttribute("userId").equals(userId))) {
			Page<Book> bookList = userService.getBookReviewedByUser(userService.getUserById(userId).getUserName(), pagenob, 4);
			Page<Author> authorList = userService.getAuthorReviewedByUser(userService.getUserById(userId).getUserName(), pagenoa, 4);
			int totalpagesb = bookList.getTotalPages();
			int totalpagesa = authorList.getTotalPages();
			List<BookAuthorListDto> ba = new ArrayList<>();
			for (Book b:bookList.getContent()){
				BookAuthorListDto bookAuthorListDto = new BookAuthorListDto();
				List<Author> a = bookService.getAuthorByBook(b.getBookName());
				bookAuthorListDto.setAuthorList(a);
				bookAuthorListDto.setBook(b);
				ba.add(bookAuthorListDto);
			}
			ModelAndView modelAndView = new ModelAndView("userprofile");
			modelAndView.addObject("books", ba);
			modelAndView.addObject("authors", authorList.getContent());
		    modelAndView.addObject("totalpagesb", totalpagesb);
		    modelAndView.addObject("totalpagesa", totalpagesa);
		    modelAndView.addObject("totalreviewlikes",reviewService.getNumberOfReviewLikesByUser(userService.getUserById(userId)));
            modelAndView.addObject("totallikes",userService.totalLikesByUser(userService.getUserById(userId)));
			modelAndView.addObject("user", userService.getUserById(userId));
			modelAndView.addObject("delete", new ChoiceDto());
			modelAndView.addObject("totalreviewsmade", userService.totalReviewsMadeByTheUser(userService.getUserById(userId)));
			return modelAndView;
		}
		return null;
	}

	@RequestMapping(value = "/users/{userId}/update", method = RequestMethod.GET)
	public Object updateUserGet(@PathVariable("userId") Long userId, HttpSession httpSession) {
		
		if(httpSession.getAttribute("userName") != null && httpSession.getAttribute("userId")== userId){
		ModelAndView modelAndView = new ModelAndView("userupdate");
		modelAndView.addObject("user",userService.getUserById(userId));
		modelAndView.addObject("userupdate", new SignupDto());
		return modelAndView;
		}
		return null;
	}

	@RequestMapping(value = "users/{userId}", method = RequestMethod.PUT)
	public Object updateUserPost(@PathVariable("userId") Long userId,
			@Valid @ModelAttribute("userupdate") SignupDto signupDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
		if (bindingResult.hasErrors()) {
			return "userupdate";
		}
		if(httpSession.getAttribute("userName") != null && httpSession.getAttribute("userId")== userId){
		try {
			userService.updateUser(userId, signupDto);
			return "userprofile";
		} catch (UserDoesNotExistException e) {
			model.addAttribute("userdoesnotexist", e);
			return "redirect:/users/{userId}";
		}
		}
		return null;

	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public String removeAccout(@PathVariable("userId") Long userId, HttpSession httpSession, Model model, @ModelAttribute("delete") ChoiceDto choiceDto) {
		if (httpSession.getAttribute("userName") == null) {
			return "redirect:/login";
		}
		if ((httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("normal")) && httpSession.getAttribute("userId")== userId) {
			if (httpSession.getAttribute("role").equals("normal") && httpSession.getAttribute("userId") == userId) {
				try {
					userService.removeUser(userId);
					httpSession.invalidate();
					return "redirect:/login";
				} catch (UserDoesNotExistException e) {
					model.addAttribute("userdoesnotexist", e);
					return null;
				}

			}
		}

		return null;

	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public Object getProfile(HttpSession httpSession) {
		if(httpSession.getAttribute("userName") != null){
		if (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator")) {
		    User user = userService.getUserById((Long)httpSession.getAttribute("userId"));
            ModelAndView modelAndView = new ModelAndView("admin");
            modelAndView.addObject("user",user);
            Long userId = (Long)httpSession.getAttribute("userId");
			return "redirect:/users/" + userId;
		}
		if (httpSession.getAttribute("role").equals("normal")) {

			Long userId = (Long)httpSession.getAttribute("userId");
			return "redirect:/users/" + userId;
		}
		}

		return "redirect:/";
	}
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public Object postProfile(HttpSession httpSession) {
		if(httpSession.getAttribute("userName") != null){
			if (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator")) {
			    User user = userService.getUserById((Long)httpSession.getAttribute("userId"));
	            ModelAndView modelAndView = new ModelAndView("admin");
	            modelAndView.addObject("user",user);
	            modelAndView.addObject("totalreviewlikes",reviewService.getNumberOfReviewLikesByUser(user));
	            modelAndView.addObject("totallikes",userService.totalLikesByUser(user));
	            modelAndView.addObject("totalreviewsmade", userService.totalReviewsMadeByTheUser(user));
	            Long userId = (Long)httpSession.getAttribute("userId");
				return "redirect:/users/" + userId;
			}
			if (httpSession.getAttribute("role").equals("normal")) {

				Long userId = (Long)httpSession.getAttribute("userId");
				return "redirect:/users/" + userId;
			}
			}

			return "redirect:/";
		
	}
	

}
