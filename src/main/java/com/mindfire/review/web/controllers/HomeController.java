package com.mindfire.review.web.controllers;

import com.mindfire.review.services.UserService;
import com.mindfire.review.web.dto.SearchDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public Object home(HttpSession httpSession) {
         ModelAndView modelAndView = new ModelAndView("homepage");
         return modelAndView;
    }

}
