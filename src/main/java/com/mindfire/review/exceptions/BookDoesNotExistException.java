package com.mindfire.review.exceptions;

/**
 * Created by pratyasa on 3/8/16.
 */
public class BookDoesNotExistException extends Exception {
    public BookDoesNotExistException(String message) {
        super(message);
    }
}
