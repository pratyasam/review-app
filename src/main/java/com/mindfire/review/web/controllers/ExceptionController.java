/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.UserDoesNotExistException;

/**
 * @author pratyasa
 *
 */
@ControllerAdvice
public class ExceptionController {

	public static final String errorInternal = "internalerror";
	public static final String DEFAULT_ERROR_VIEW = "resourcenotfound";

	@ExceptionHandler({RuntimeException.class, ArrayIndexOutOfBoundsException.class})
	public String internalError() {
		return errorInternal;
	}
    
	@ExceptionHandler({Exception.class, FileNotFoundException.class,BookDoesNotExistException.class, UserDoesNotExistException.class})
	public String defaultErrorHandler(HttpServletRequest req, Exception e) {
		
		// Send the user to a default error-view.
		return DEFAULT_ERROR_VIEW;
	}

}
