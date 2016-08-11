package com.mindfire.review.web.controllers;

import com.mindfire.review.services.UserServiceInterface;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserServiceInterface userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("userName") == null) {
            return "redirect:/login";
        }
        User user = userService.getUser((String) httpSession.getAttribute("userName"));
        List<ReviewBook> reviewBookList = userService.getBookReviewByUser(user.getUserName());
        model.addAttribute("user", user);
        model.addAttribute("reviews", reviewBookList);

        return "home";
    }

}
