package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.LoginFailException;
import com.mindfire.review.services.UserService;
import com.mindfire.review.web.dto.LoginDto;
import com.mindfire.review.web.models.User;
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
 * Created by pratyasa on 1/8/16.
 */
@Controller
public class LoginController {
	
	public static final String LOGIN = "login";
	public static final String USERNAME = "userName";
	
    @Autowired
    private UserService userService;

    /**
     * to get the login page
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object loginGet(HttpSession httpSession) {
		if (httpSession.getAttribute(USERNAME) != null) {
            return "redirect:/profile";
        }

        ModelAndView modelAndView = new ModelAndView(LOGIN);
        modelAndView.addObject(LOGIN, new LoginDto());
        return modelAndView;
    }

    /**
     * to login
     * @param LoginDto
     * @param bindingResult
     * @param model
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticateUser(@Valid @ModelAttribute(LOGIN) LoginDto loginDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (httpSession.getAttribute(USERNAME) != null) {
            return null;
        }

        if (bindingResult.hasErrors()) {
            return LOGIN;
        }


        String userName = loginDto.getUserName();
        String password = loginDto.getPassword();
        try {

            User user = userService.loginAuthenticate(userName, password);
            httpSession.setAttribute(USERNAME, user.getUserName());
            httpSession.setAttribute("userFirstName", user.getFirstName());
            httpSession.setAttribute("userLastName", user.getLastName());
            httpSession.setAttribute("role", user.getRole());
            httpSession.setAttribute("userId", user.getUserId());
            httpSession.setAttribute("userImage", user.getUserImage());
            
            if(httpSession.getAttribute("url") != null){
            	String url = (String)httpSession.getAttribute("url");
            	url = url.replaceFirst("/reviewBook", "");
            	return "redirect:/" + url;
            }
            return "redirect:/";

        } catch (LoginFailException lex) {
            model.addAttribute("exception", lex);
            return LOGIN;


        }


    }

}
