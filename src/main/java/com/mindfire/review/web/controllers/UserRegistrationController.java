/**
 *
 */
package com.mindfire.review.web.controllers;

import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.services.UserServiceInterface;
import com.mindfire.review.web.dto.SignupDto;
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
 * @author pratyasa
 */
@Controller
public class UserRegistrationController {
    @Autowired
    private UserServiceInterface userService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public Object signup(HttpSession httpSession) {
        if (httpSession.getAttribute("userName") != null) {
            return "redirect:/";
        }

        return new ModelAndView("signup", "signUp", new SignupDto());
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("signUp") SignupDto signupDto, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("userName") != null) {
            return null;
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        System.out.println(signupDto);

        try {
            User user = new User();
            user.setFirstName(signupDto.getFirstName());
            user.setLastName(signupDto.getLastName());
            user.setUserName(signupDto.getUserName());
            user.setUserPassword(signupDto.getPassword());
            user.setUserGender(signupDto.getGender());
            userService.addUser(user);
            model.addAttribute("userName", user.getFirstName());
            return "thankyou";
        } catch (UserExistException ex) {
            model.addAttribute("userExist", ex);
            return "signup";
        }


    }
}
