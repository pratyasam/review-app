package com.mindfire.review.services;

import com.mindfire.review.exceptions.LoginFailException;
import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.web.dto.SignupDto;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;

import java.util.List;

public interface UserService {

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
     * @param signupDto
     * @throws UserExistException
     */
    void addUser(SignupDto signupDto) throws UserExistException;

    /**
     * @param userId
     * @throws UserDoesNotExistException
     */

    void removeUser(Long userId) throws UserDoesNotExistException;

    /**
     * @param userId
     * @param signupDto
     * @throws UserDoesNotExistException
     */

    void updateUser(Long userId, SignupDto signupDto) throws UserDoesNotExistException;

    /**
     * @param userName
     * @param password
     * @return
     */
    User loginAuthenticate(String userName, String password) throws LoginFailException;

    /**
     * @param userId
     * @throws UserDoesNotExistException
     */

    void changeRoleToAdmin(Long userId) throws UserDoesNotExistException;

    /**
     * @param userId
     * @throws UserDoesNotExistException
     */

    void changeRoleToModerator(Long userId) throws UserDoesNotExistException;

    /**
     *
     * @param userId
     * @return
     */
    public User getUserById(Long userId);


}