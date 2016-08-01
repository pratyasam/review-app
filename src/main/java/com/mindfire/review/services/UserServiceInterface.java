package com.mindfire.review.services;

import java.util.List;

import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.web.models.Review;
import com.mindfire.review.web.models.User;

public interface UserServiceInterface {

	/**
	 * Service method to fetch a user by username.
	 * 
	 * @param userName 
	 * @return {@link User} The user
	 */
	User getUser(String userName);

	/**
	 * 
	 * @return
	 */
	List<User> getUsers();

	/**
	 * 
	 * @param user
	 */
	void deleteUser(User user);

	/**
	 * 
	 * @param firstName
	 * @return
	 */
	List<User> getUserFirstName(String firstName);

	/**
	 * 
	 * @param lastName
	 * @return
	 */
	List<User> getUserlastName(String lastName);

	/**
	 * 
	 * @param type
	 * @return
	 */

	List<User> getUserByType(String type);

	/**
	 * 
	 * @param user
	 * @return
	 */

	List<Review> getAuthorReviewByUser(String user);

	/**
	 * 
	 * @param user
	 * @return
	 */

	List<Review> getBookReview(String user);

	/**
	 * 
	 * @param userName
	 * @return
	 */
	Boolean doesUserNameExist(String userName);

	/**
	 * 
	 * @param user
	 * @throws UserExistException
	 */
	void addUser(User user) throws UserExistException;

	void removeUser(User user) throws UserDoesNotExistException;

	void updateUser(User user) throws UserDoesNotExistException;

}