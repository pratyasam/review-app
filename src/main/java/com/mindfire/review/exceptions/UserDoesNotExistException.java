/**
 *
 */
package com.mindfire.review.exceptions;


/**
 * @author pratyasa
 */

public class UserDoesNotExistException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDoesNotExistException(String message) {
        super(message);
    }

}
