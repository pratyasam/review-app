package com.mindfire.review.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mindfire.review.exceptions.LoginFailException;
import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.util.Utility;
import com.mindfire.review.web.dto.SignupDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.AuthorLikeRepository;
import com.mindfire.review.web.repositories.BookLikeRepository;
import com.mindfire.review.web.repositories.ReviewAuthorRepository;
import com.mindfire.review.web.repositories.ReviewBookRepository;
import com.mindfire.review.web.repositories.UserRepository;

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
	
	@Autowired
	private BookLikeRepository bookLikeRepository;
	
	@Autowired
	private AuthorLikeRepository authorLikeRepository;

	/**
	 * get user by user name
	 *
	 * @param userName
	 * @return
	 */

	@Override
	public User getUser(String userName) {
		return userRepository.findByUserName(userName);
	}

	/**
	 * get the list of all registered users
	 *
	 * @return
	 */

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	/**
	 * get users in page form
	 * @param pageno
	 * @param size
	 * @return
	 */

	@Override
	public Page<User> getUsers(int pageno, int size) {
		Pageable page = Utility.buildPageRequest(size, pageno);
		return userRepository.findAll(page);
	}

	/**
	 * get users with the same first name
	 *
	 * @param firstName
	 * @return
	 */

	@Override
	public Page<User> getUserFirstName(String firstName, int page) {
		
		return userRepository.findByFirstNameContaining(firstName, Utility.buildPageRequest(10, page));
	}

	/**
	 * get users with the same last name
	 *
	 * @param lastName
	 * @return
	 */

	@Override
	public List<User> getUserlastName(String lastName) {
		return userRepository.findByLastNameIgnoreCase(lastName);
	}
	/**
	 * get users by last name in page form
	 * @param lastName
	 * @param pageno
	 * @param size
	 * @return
	 */
	public Page<User> getUserlastName(String lastName, int pageno, int size) {
		Pageable page = Utility.buildPageRequest(size, pageno);
		return userRepository.findByLastNameIgnoreCase(lastName, page);
	}

	/**
	 * get the list of users according to the role
	 *
	 * @param type
	 * @return
	 */

	@Override
	public List<User> getUserByType(String type) {
		return userRepository.findByRoleIgnoreCase(type);
	}
	public Page<User> getUserByType(String type, int pageno, int size) {
		Pageable page = Utility.buildPageRequest(size, pageno);
		return userRepository.findByRoleIgnoreCase(type, page);
	}

	/**
	 * get all the reviews tmade by a user on the authors
	 *
	 * @param user
	 * @return
	 */

	@Override
	public List<ReviewAuthor> getAuthorReviewByUser(String user) {
		User userName = getUser(user);
		return reviewAuthorRepository.findByUser(userName);

	}
	/**
	 * get the author reviews made by the user
	 * @param user
	 * @return
	 */
	@Override
	public List<Author> getAuthorReviewedByUser(String user) {
		User userName = getUser(user);
		List<ReviewAuthor> reviewAuthors = reviewAuthorRepository.findByUser(userName);
		List<Author> authors = new ArrayList<>();
		for(ReviewAuthor ra : reviewAuthors){
			authors.add(ra.getAuthor());
		}
		return authors;

	}
	
	/**
	 * get authors reviewed by the user in page form
	 * @param user
	 * @param user
	 * @param user
	 */
	@Override
	public Page<Author> getAuthorReviewedByUser(String user, int pageno, int size) {
		User userName = getUser(user);
		List<ReviewAuthor> reviewAuthors = reviewAuthorRepository.findByUser(userName);
		List<Author> authors = new ArrayList<>();
		for(ReviewAuthor ra : reviewAuthors){
			authors.add(ra.getAuthor());
		}
		Pageable page = Utility.buildPageRequest(size, pageno);
		return new PageImpl<>(authors,page, size);

	}

	/**
	 * get a user by user Id
	 *
	 * @param userIdget
	 * @return
	 */

	@Override
	public User getUserById(Long userId) {
		return userRepository.findOne(userId);
	}

	/**
	 * get all the reviews made a user on the books
	 *
	 * @param user
	 * @return
	 */

	@Override
	public List<ReviewBook> getBookReviewByUser(String user) {
		User userName = getUser(user);
		return reviewBookRepository.findByUser(userName);
	}
	/**
	 * get books reviewed by the user
	 * @param user
	 * @return
	 */
	@Override
	public List<Book> getBookReviewedByUser(String user) {
		User userName = getUser(user);
		List<ReviewBook> reviewBooks = reviewBookRepository.findByUser(userName);
		List<Book> books = new ArrayList<>();
		for(ReviewBook rb : reviewBooks){
			books.add(rb.getBook());
		}
		return books;
	}
	/**
	 * get books reviewed by the user in page form
	 * @param user
	 * @param pageno
	 * @param size
	 * @return
	 */
	@Override
	public Page<Book> getBookReviewedByUser(String user, int pageno, int size) {
		User userName = getUser(user);
		List<ReviewBook> reviewBooks = reviewBookRepository.findByUser(userName);
		List<Book> books = new ArrayList<>();
		for(ReviewBook rb : reviewBooks){
			books.add(rb.getBook());
		}
		Pageable page = Utility.buildPageRequest(size, pageno);
		PageImpl<Book> pageImpl = new PageImpl<>(books,page,size);
		return pageImpl;
	}

	/**
	 * check whether the username is available or not
	 *
	 * @param userName
	 * @return
	 */

	@Override
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

	@Override
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
		user.setUserPassword(DigestUtils.sha256Hex(signupDto.getPassword()));
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

	@Override
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

	@Override
	public void updateUser(Long userId, SignupDto signupDto) throws UserDoesNotExistException {
		User user = userRepository.findOne(userId);
		if (!doesUserNameExist(user.getUserName())) {
			throw new UserDoesNotExistException("User does not exist");
		}
		user.setFirstName(signupDto.getFirstName());
		user.setLastName(signupDto.getLastName());
		user.setUserName(signupDto.getUserName());
		user.setUserGender(signupDto.getGender());
		user.setUserPassword(DigestUtils.sha256Hex(signupDto.getPassword()));
		userRepository.save(user);
	}

	/**
	 * method to change the role of a user
	 *
	 * @param userId
	 * @throws UserDoesNotExistException
	 */

	@Override
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

	@Override
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

	@Override
	public User loginAuthenticate(String userName, String password) throws LoginFailException {
		
		User user = userRepository.findByUserNameAndUserPassword(userName, DigestUtils.sha256Hex(password));
		if (user != null)
			return user;
		else
			throw new LoginFailException("Invalid User Name or Password");
	}
	
	/**
	 * get total books and author likes made by the user
	 * @param user
	 */
	@Override
	public int totalLikesByUser(User user) { 
		
		int bookLikes = bookLikeRepository.findByUser(user).size();
		int authorLike = authorLikeRepository.findByUser(user).size();
		return bookLikes + authorLike;
	}
	
	/**
	 * get the total reviews made by the user
	 * @param user
	 */
	@Override
	public int totalReviewsMadeByTheUser(User user){
		
		int bookReviews = getBookReviewByUser(user.getUserName()).size();
		int authorReviews = getAuthorReviewedByUser(user.getUserName()).size();
		 return authorReviews + bookReviews;
	}
}
