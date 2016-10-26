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
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object login(HttpSession httpSession) {
        if (httpSession.getAttribute("userName") != null) {
            return "redirect:/profile";
        }

        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("login", new LoginDto());
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticateUser(@Valid @ModelAttribute("login") LoginDto LoginDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("userName") != null) {
            return null;
        }

        if (bindingResult.hasErrors()) {
            return "login";
        }


        String userName = LoginDto.getUserName();
        String password = LoginDto.getPassword();
        try {

            User user = userService.loginAuthenticate(userName, password);
            httpSession.setAttribute("userName", user.getUserName());
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
            return "login";


        }


    }

}
