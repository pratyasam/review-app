/**
 * 
 */
package com.mindfire.review.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author pratyasa
 *
 */
@Controller
public class ExceptionController {
	
	private static final String errorInternal = "internalerror";
	
	@RequestMapping(value="/error", method = RequestMethod.GET)
	public String internalError(){
		return errorInternal;
	}

}
