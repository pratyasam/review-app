package com.mindfire.review.services;

import com.mindfire.review.exceptions.LoginFailException;
import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.ReviewAuthorRepository;
import com.mindfire.review.web.repositories.ReviewBookRepository;
import com.mindfire.review.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pratyasa
 */
@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewAuthorRepository reviewAuthorRepository;

    @Autowired
    private ReviewBookRepository reviewBookRepository;

    /**
     *
     */
    public User getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    /**
     *
     */

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     *
     */

    public void deleteUser(User user) {

    }

    /**
     *
     */

    public List<User> getUserFirstName(String firstName) {
        return userRepository.findByFirstNameIgnoreCase(firstName);
    }

    /**
     *
     */

    public List<User> getUserlastName(String lastName) {
        return userRepository.findByLastNameIgnoreCase(lastName);
    }

    /**
     *
     */

    public List<User> getUserByType(String type) {
        return userRepository.findByRoleIgnoreCase(type);
    }

    /**
     *
     */

    public List<ReviewAuthor> getAuthorReviewByUser(String user) {
        User userName = getUser(user);
        return reviewAuthorRepository.findByUser(userName);

    }

    /**
     *
     */
    public List<ReviewBook> getBookReviewByUser(String user) {
        User userName = getUser(user);
        return reviewBookRepository.findByUser(userName);
    }

    /**
     *
     */

    public Boolean doesUserNameExist(String userName) {
        if (getUser(userName) != null)
            return true;
        else
            return false;
    }

    /**
     *
     */

    public void addUser(User user) throws UserExistException {
        if (user == null)
            throw new RuntimeException("");

        if (doesUserNameExist(user.getUserName())) {
            throw new UserExistException("User already exists");
        }

        user = userRepository.save(user);
        if (user == null) {
            throw new RuntimeException("");
        }
    }

    /**
     *
     */
    public void removeUser(User user) throws UserDoesNotExistException {
        if (user == null) {
            throw new RuntimeException("");
        }
        if (!doesUserNameExist(user.getUserName())) {
            throw new UserDoesNotExistException("User does not exist");
        }
        userRepository.delete(user);

    }

    /**
     *
     */

    public void updateUser(User user) throws UserDoesNotExistException {
        if (user == null) {
            throw new RuntimeException("");
        }
        if (!doesUserNameExist(user.getUserName())) {
            throw new UserDoesNotExistException("User does not exist");
        }
        user = userRepository.save(user);
    }

    /**
     *
     */
    public User loginAuthenticate(String userName, String password) throws LoginFailException {
        User user = userRepository.findByUserNameAndUserPassword(userName, password);
        if (user != null)
            return user;
        else
            throw new LoginFailException("Invalid User Name or Password");
    }
}
