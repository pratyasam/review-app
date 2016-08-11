package com.mindfire.review.services;

import com.mindfire.review.exceptions.LoginFailException;
import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;

import java.util.List;

public interface UserServiceInterface {

    /**
     * Service method to fetch a user by username.
     *
     * @param userName
     * @return {@link User} The user
     */
    User getUser(String userName);

    /**
     * @return
     */
    List<User> getUsers();

    /**
     * @param user
     */
    void deleteUser(User user);

    /**
     * @param firstName
     * @return
     */
    List<User> getUserFirstName(String firstName);

    /**
     * @param lastName
     * @return
     */
    List<User> getUserlastName(String lastName);

    /**
     * @param type
     * @return
     */

    List<User> getUserByType(String type);

    /**
     * @param user
     * @return
     */

    List<ReviewAuthor> getAuthorReviewByUser(String user);

    /**
     * @param user
     * @return
     */

    List<ReviewBook> getBookReviewByUser(String user);

    /**
     * @param userName
     * @return
     */
    Boolean doesUserNameExist(String userName);

    /**
     * @param user
     * @throws UserExistException
     */
    void addUser(User user) throws UserExistException;

    /**
     * @param user
     * @throws UserDoesNotExistException
     */

    void removeUser(User user) throws UserDoesNotExistException;

    /**
     * @param user
     * @throws UserDoesNotExistException
     */

    void updateUser(User user) throws UserDoesNotExistException;

    /**
     * @param userName
     * @param password
     * @return
     */
    public User loginAuthenticate(String userName, String password) throws LoginFailException;

}