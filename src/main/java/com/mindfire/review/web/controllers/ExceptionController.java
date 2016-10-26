/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.io.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.UserDoesNotExistException;

/**
 * @author pratyasa
 *
 */
@ControllerAdvice(basePackages = {"com.mindfire.controllers"})
public class ExceptionController {

	public static final String ERRORINTERNAL = "internalerror";
	public static final String DEFAULT_ERROR_VIEW = "resourcenotfound";

	/**
	 * 
	 * @return
	 */
	@ExceptionHandler({RuntimeException.class, FileUploadException.class})
	public String internalError() {
		return ERRORINTERNAL;
	}
	
	/**
	 * 
	 * @return
	 */
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="User Not Found")
	@ExceptionHandler({FileNotFoundException.class,BookDoesNotExistException.class, UserDoesNotExistException.class})
	public String defaultErrorHandler() {
		
		// Send the user to a default error-view.
		return DEFAULT_ERROR_VIEW;
	}

}
