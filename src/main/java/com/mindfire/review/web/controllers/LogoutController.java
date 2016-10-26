package com.mindfire.review.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by pratyasa on 3/8/16.
 */
@Controller
public class LogoutController {
	
	/**
	 * to logout and end the session
	 * @param httpSession
	 * @return
	 */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "logout";
    }
}
