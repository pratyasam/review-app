/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.services.UserRegistrationService;
import com.mindfire.review.web.dto.SignupDto;
import com.mindfire.review.web.models.User;

/**
 * @author pratyasa
 *
 */
@Controller
public class UserRegistrationController {
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView addUser(SignupDto signupDto,BindingResult bindingResult){
		
		Map<String,Object> errors = new HashMap<>();
		
		if(bindingResult.hasErrors()){
			errors.put("errors",(List<ObjectError>)bindingResult.getAllErrors());
			return new ModelAndView("signup",errors);
		}
		
		try{
			User user = new User();
			user.setUser_firstName(signupDto.getFirstName());
			user.setUser_lastName(signupDto.getLastName());
			user.setUserName(signupDto.getUserName());
			user.setUserPassword(signupDto.getPassword());
			user.setUserGender(signupDto.getGender());
			userRegistrationService.addUser(user);
			return new ModelAndView("thankyou","user",user);
		}
		catch(UserExistException ex){
			return new ModelAndView("signup","userExp",ex);
		}
		
	
	}
}
