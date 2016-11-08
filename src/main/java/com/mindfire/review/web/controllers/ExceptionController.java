/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.io.FileNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.UserDoesNotExistException;

/**
 * @author pratyasa
 *
 */
@ControllerAdvice(basePackages = {"com.mindfire.controllers"})
@EnableWebMvc
public class ExceptionController {

	public static final String ERRORINTERNAL = "internalerror";
	public static final String DEFAULT_ERROR_VIEW = "resourcenotfound";
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	/**
	 * Handles the  Runtime errors, FileuploadExceptions
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({RuntimeException.class, FileUploadException.class})
	public String internalError(HttpServletRequest request, Exception ex) {
		
		logger.info("Internal Occured:: URL="+request.getRequestURL());
		return ERRORINTERNAL;
	}
	
	/**
	 * Handles the FileNotFoundException, BookDoesNotExistException, UserDoesNotExistException. 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({FileNotFoundException.class,BookDoesNotExistException.class, UserDoesNotExistException.class})
	public String defaultErrorHandler(HttpServletRequest request, Exception ex) {
		
		logger.error("Resource not found"+" Error Occured:: URL="+request.getRequestURL());
		// Send the user to a resource not found error-view.
		return DEFAULT_ERROR_VIEW;
	}

}
