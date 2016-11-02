package com.mindfire.review.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mindfire.review.exceptions.LoginFailException;
import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.web.dto.SignupDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;

public interface UserService {

    /**
     * Service method to fetch a user by username,from the database.
     *
     * @param userName
     * @return {@link User} The user
     */
    User getUser(String userName);

    /**
     * This method returns all the users listed in the database. Can be accessed by the admin.
     * @return List<User>
     */
    List<User> getUsers();
    
    /**
     * This method returns all the users present in the database in the page form, and can be accessed by the admin.
     * @param pageno
     * @param size
     * @return Page<User>
     */
    Page<User> getUsers(int pageno, int size);

     /**
     * This method returns a list of all the users having their first name similar to the parameter, in page format.
     * @param firstName
     * @param page
     * @return Page<User>
     */
    
    Page<User> getUserFirstName(String firstName, int page);

    /**
     * This method returns the list of all users having their last name equal to the parameter.
     * @param lastName
     * @return List<User>
     */
    List<User> getUserlastName(String lastName);

    /**
     * This method returns the list of all the users by their role, can be accessed by the admin.
     * @param type
     * @return  List<User>
     */
    List<User> getUserByType(String type);

    /**
     * This method returns the list of all the reviews for the author made by the user, searched by the user name.
     * @param user
     * @return  List<ReviewAuthor>
     */
    List<ReviewAuthor> getAuthorReviewByUser(String user);
    
    /**
     * This method returns the list of authors that a user has reviewed, searched by the user name.
     * @param user
     * @return  List<Author>
     */
    List<Author> getAuthorReviewedByUser(String user);
    
    /**
     * This method returns the list of all the authors, in the page format, that have been reviewed by the user searched by the user name
     * @param user
     * @param pageno
     * @param size
     * @return Page<Author>
     */
    Page<Author> getAuthorReviewedByUser(String user, int pageno, int size);

    /**
     * This method returns a list of all the reviews made on the books by a user, searched by the user name.
     * @param user
     * @return  List<ReviewBook>
     */

    List<ReviewBook> getBookReviewByUser(String user);
    
    /**
     * This method returns the list of all the books reviewed by a particular user, searched by the user name.
     * @param user
     * @return List<Book>
     */
    List<Book> getBookReviewedByUser(String user);
    
    /**
     * This method returns the list of all the books, in page format, reviewed by a particular user, searched by the user name.
     * @param user
     * @param pageno
     * @param size
     * @return Page<Book>
     */
    Page<Book> getBookReviewedByUser(String user, int pageno, int size);

    /**
     * This method confirms whether the user exists or not, by searching for the user name in the database.
     * @param userName
     * @return Boolean
     */
    Boolean doesUserNameExist(String userName);

    /**
     * This method is called upon persisting the user into the database.
     * This method throws an exception when the user exists with the same user name.
     * @param signupDto
     * @throws UserExistException
     */
    void addUser(SignupDto signupDto) throws UserExistException;

    /**
     * This method is called to remove or delete the user and his data, from the database.
     * This method throws an exception when the requested user is not found in the database.
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
    
    /**
     * 
     * @param user
     * @return
     */
    int totalLikesByUser(User user);
    
    /**
     * 
     * @param user
     * @return
     */
    int totalReviewsMadeByTheUser(User user);


}