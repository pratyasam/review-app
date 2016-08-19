package com.mindfire.review.services;

import com.mindfire.review.exceptions.LoginFailException;
import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.web.dto.SignupDto;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewAuthorRepository reviewAuthorRepository;

    @Autowired
    private ReviewBookRepository reviewBookRepository;

    /**
     * get user by user name
     *
     * @param userName
     * @return
     */


    public User getUser(String userName) {
        return userRepository.findByUserName(userName);
    }

    /**
     * get the list of all registered users
     *
     * @return
     */


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * get users with the same first name
     *
     * @param firstName
     * @return
     */


    public List<User> getUserFirstName(String firstName) {
        return userRepository.findByFirstNameIgnoreCase(firstName);
    }

    /**
     * get users with the same last name
     *
     * @param lastName
     * @return
     */

    public List<User> getUserlastName(String lastName) {
        return userRepository.findByLastNameIgnoreCase(lastName);
    }

    /**
     * get the list of users according to the role
     *
     * @param type
     * @return
     */


    public List<User> getUserByType(String type) {
        return userRepository.findByRoleIgnoreCase(type);
    }

    /**
     * get all the reviews tmade by a user on the authors
     *
     * @param user
     * @return
     */


    public List<ReviewAuthor> getAuthorReviewByUser(String user) {
        User userName = getUser(user);
        return reviewAuthorRepository.findByUser(userName);

    }

    /**
     * get a user by user Id
     *
     * @param userId
     * @return
     */

    public User getUserById(Long userId) {
        return userRepository.findOne(userId);
    }

    /**
     * get all the reviews made a user on the books
     *
     * @param user
     * @return
     */


    public List<ReviewBook> getBookReviewByUser(String user) {
        User userName = getUser(user);
        return reviewBookRepository.findByUser(userName);
    }

    /**
     * check whether the username is available or not
     *
     * @param userName
     * @return
     */


    public Boolean doesUserNameExist(String userName) {
        if (getUser(userName) != null)
            return true;
        else
            return false;
    }

    /**
     * method to add user
     *
     * @param signupDto
     * @throws UserExistException
     */


    public void addUser(SignupDto signupDto) throws UserExistException {
        if (signupDto == null)
            throw new RuntimeException("");

        if (doesUserNameExist(signupDto.getUserName())) {
            throw new UserExistException("User already exists");
        }
        User user = new User();
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setUserName(signupDto.getUserName());
        user.setUserPassword(signupDto.getPassword());
        user.setUserGender(signupDto.getGender());
        user = userRepository.save(user);
        if (user == null) {
            throw new RuntimeException("");
        }
    }

    /**
     * method to delete a user
     *
     * @param userId
     * @throws UserDoesNotExistException
     */


    public void removeUser(Long userId) throws UserDoesNotExistException {
        User user = userRepository.findOne(userId);
        if (!doesUserNameExist(user.getUserName())) {
            throw new UserDoesNotExistException("User does not exist");
        }
        userRepository.delete(user);

    }

    /**
     * method to update user info
     *
     * @param userId
     * @param signupDto
     * @throws UserDoesNotExistException
     */


    public void updateUser(Long userId, SignupDto signupDto) throws UserDoesNotExistException {
        User user = userRepository.findOne(userId);
        if (!doesUserNameExist(user.getUserName())) {
            throw new UserDoesNotExistException("User does not exist");
        }
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setUserName(signupDto.getUserName());
        user.setUserGender(signupDto.getGender());
        user.setUserPassword(signupDto.getPassword());
        user = userRepository.save(user);
    }

    /**
     * method to change the rolse of a user
     *
     * @param userId
     * @throws UserDoesNotExistException
     */

    public void changeRoleToModerator(Long userId) throws UserDoesNotExistException {
        User user = userRepository.findOne(userId);
        user.setRole("moderator");
        userRepository.save(user);
    }

    /**
     * method to make user an admin
     *
     * @param userId
     * @throws UserDoesNotExistException
     */

    public void changeRoleToAdmin(Long userId) throws UserDoesNotExistException {
        User user = userRepository.findOne(userId);
        user.setRole("admin");
        userRepository.save(user);
    }

    /**
     * to authenticate login
     *
     * @param userName
     * @param password
     * @return
     * @throws LoginFailException
     */


    public User loginAuthenticate(String userName, String password) throws LoginFailException {
        User user = userRepository.findByUserNameAndUserPassword(userName, password);
        if (user != null)
            return user;
        else
            throw new LoginFailException("Invalid User Name or Password");
    }
}
