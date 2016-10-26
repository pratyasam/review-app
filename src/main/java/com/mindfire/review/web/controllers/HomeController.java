package com.mindfire.review.web.controllers;

import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookService;
import com.mindfire.review.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public Object home(HttpSession httpSession) {
         ModelAndView modelAndView = new ModelAndView("homepage");
         modelAndView.addObject("authorlist", authorService.getTop10Authors());
         modelAndView.addObject("booklist", bookService.getTop10Books());
         return modelAndView;
    }

}
