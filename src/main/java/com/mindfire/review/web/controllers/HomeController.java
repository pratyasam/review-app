package com.mindfire.review.web.controllers;

import com.mindfire.review.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("userName") == null) {
            return "redirect:/login";
        }


        return "index";
    }

}
