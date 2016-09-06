/**
 *
 * Created by pratyasa on 11/8/16.
 */
package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.services.UserService;
import com.mindfire.review.web.dto.SignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 
 */
@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Object getUsers(HttpSession httpSession) {

		if (httpSession.getAttribute("userName") != null && httpSession.getAttribute("role").equals("admin")) {
			ModelAndView modelAndView = new ModelAndView("users");
			modelAndView.addObject("users", userService.getUsers());
			return modelAndView;
		}
		return "redirect:/profile";
	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public Object getUser(@PathVariable("userId") Long userId, HttpSession httpSession) {
		if (httpSession.getAttribute("userName") != null && httpSession.getAttribute("role").equals("admin")) {
			ModelAndView modelAndView = new ModelAndView("userprofile");
			modelAndView.addObject("user", userService.getUserById(userId));
			return modelAndView;
		}
		return null;
	}

	@RequestMapping(value = "/users/{userId}/update", method = RequestMethod.GET)
	public Object updateUserGet(@PathVariable("userId") Long userId, HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView("userupdate");
		modelAndView.addObject("userupdate", new SignupDto());
		return modelAndView;
	}

	@RequestMapping(value = "users/{userId}", method = RequestMethod.PUT)
	public Object updateUserPost(@PathVariable("userId") Long userId,
			@Valid @ModelAttribute("userupdate") SignupDto signupDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "userupdate";
		}
		try {
			userService.updateUser(userId, signupDto);
			return "userprofile";
		} catch (UserDoesNotExistException e) {
			model.addAttribute("userdoesnotexist", e);
			return "redirect:/users/{userId}";
		}

	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public String removeAccout(@PathVariable("userId") Long userId, HttpSession httpSession, Model model) {
		if (httpSession.getAttribute("userName") == null) {
			return "redirect:/login";
		}
		if (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("normal")) {
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
	public String getProfile(HttpSession httpSession) {
		if(httpSession.getAttribute("userName") != null){
		if (httpSession.getAttribute("role").equals("admin") || httpSession.getAttribute("role").equals("moderator")) {

			return "admin";
		}
		if (httpSession.getAttribute("role").equals("normal")) {

			return "userprofile";
		}
		}

		return "redirect:/";
	}

}
