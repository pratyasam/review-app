package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.LoginFailException;
import com.mindfire.review.services.UserServiceInterface;
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
    private UserServiceInterface userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object login(HttpSession httpSession) {
        if (httpSession.getAttribute("userName") != null) {
            return "redirect:/";
        }

        return new ModelAndView("login", "login", new LoginDto());
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticateUser(@Valid @ModelAttribute("login") LoginDto LoginDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("userName") != null) {
            return null;
        }

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            System.out.println("has errors");
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


            if (user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("moderator")) {

                return "admin";
            } else {

                return "redirect:/";
            }
        } catch (LoginFailException lex) {


            model.addAttribute("exception", lex);
            return "login";


        }


    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdmin(HttpSession httpSession) {
        if (httpSession.getAttribute("role").equals("admin")) {

            return "admin";
        }
        return "login";

    }

}
