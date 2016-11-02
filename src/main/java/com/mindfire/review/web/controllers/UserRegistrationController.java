/**
 *
 */
package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.services.UserService;
import com.mindfire.review.web.dto.SignupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author pratyasa
 */
@Controller
public class UserRegistrationController {
	
	public static final String USERNAME = "userName";
	public static final String SIGNUP = "signup";
	
    @Autowired
    private UserService userService;

    /**
     * to get the signup form
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public Object signupGet(HttpSession httpSession) {
        if (httpSession.getAttribute(USERNAME) != null) {
            return "redirect:/profile";
        }

        return new ModelAndView(SIGNUP, SIGNUP, new SignupDto());
    }

    /**
     * to persist the user in the database
     * @param signupDto
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute(SIGNUP) SignupDto signupDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (httpSession.getAttribute(USERNAME) != null) {
            return null;
        }

        if (bindingResult.hasErrors()) {
            return SIGNUP;
        }

        try {
            userService.addUser(signupDto);
            model.addAttribute(USERNAME, signupDto.getFirstName());
            return "thankyou";
        } catch (UserExistException ex) {
            model.addAttribute("userExist", ex);
            return SIGNUP;
        }


    }
}
