/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.services.EmailService;
import com.mindfire.review.services.UserService;
import com.mindfire.review.web.dto.EmailDto;
import com.mindfire.review.web.models.User;

/**
 * @author pratyasa
 *
 */

@Controller
public class EmailController {
	
	public static final String MODERATOR = "moderator";
	public static final String ADMIN = "admin";
	public static final String USERNAME = "userName";
	
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	public static final String ERRORINTERNAL = "internalerror";
	
	/**
	 * 
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
	public ModelAndView getForm(HttpSession httpSession){
		
		if (((ADMIN).equals(httpSession.getAttribute("role")) && httpSession.getAttribute(USERNAME) != null)
				|| (MODERATOR).equals(httpSession.getAttribute("role"))){
		
		ModelAndView modelAndView = new ModelAndView("emailform");
		modelAndView.addObject("email",new EmailDto());
		
		return modelAndView;
		}
		
		return null;
		
	}
	
	/**
	 * This is a method which invokes the send email method of email service and sends the email.
	 * @param emailDto
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "/sendEmail.do", method = RequestMethod.POST)
	public String postEmail(@Valid @ModelAttribute("email") EmailDto emailDto, BindingResult bindingResult, HttpSession httpSession){
		
		if(bindingResult.hasErrors()){
			return "redirect:/sendEmail";
		}
		
		if (((ADMIN).equals(httpSession.getAttribute("role")) && httpSession.getAttribute(USERNAME) != null)
				|| (MODERATOR).equals(httpSession.getAttribute("role"))){
			
			List<User> users = userService.getUsers();
			
			for (User u : users){
				
				if(u.getUserEmail() != null){
					
					emailDto.setRecipient(u.getUserEmail());
					emailService.sendEmailNewsLetter(emailDto);
				}
				
			}
			
		}
		
         
        // forwards to the profile
        return "redirect:/profile";
		
	}
	
	/**
	 * This is an exceptional handler to catch all types of exceptions and redirect to the internal server error page.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public String internalError(HttpServletRequest request, Exception ex) {
		
		logger.info("Internal Occured:: URL="+request.getRequestURL() + "message : "+ex.getMessage());
		return ERRORINTERNAL;
	}

}
