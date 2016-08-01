package com.mindfire.review.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.services.UserServiceInterface;

@Controller
public class HomeController {
	
	@Autowired
	private UserServiceInterface userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {

//		Map<String,Object> map = new HashMap<>();
//		map.put("users", userService.getUsers());
//		ModelAndView mv = new ModelAndView("index", map);
//		return mv;
		model.addAttribute("users", userService.getUsers());
		return "login";
	}
}
