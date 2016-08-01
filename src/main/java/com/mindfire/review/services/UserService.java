package com.mindfire.review.services;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.web.models.Review;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.ReviewAuthorRepository;
import com.mindfire.review.web.repositories.ReviewRepository;
import com.mindfire.review.web.repositories.UserRepository;

/**
 * 
 * @author pratyasa
 *
 */
@Service
public class UserService implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReviewAuthorRepository reviewAuthorRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	/**
	 * 
	 */
	public User getUser(String userName){
		return userRepository.findByUserName(userName);
	}
	/**
	 * 
	 */
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	/**
	 * 
	 */
	
	public void deleteUser(User user){
		
	}
	/**
	 * 
	 */
	
	public List<User> getUserFirstName(String firstName){
		return userRepository.findByfirstNameIgnoreCase(firstName);
	}
	/**
	 * 
	 */

	public List<User> getUserlastName(String lastName){
		return userRepository.findBylastNameIgnoreCase(lastName);
	}
	/**
	 * 
	 */
	
	public List<User> getUserByType(String type){
		return userRepository.findByUserTypeIgnoreCase(type);
	}
	/**
	 * 
	 */
	
	public List<Review> getAuthorReviewByUser(String user){
		User userName = getUser(user);
		return reviewAuthorRepository.findByUser(userName);
		
	}
	/**
	 * 
	 */
	public List<Review> getBookReview(String user){
		User userName = getUser(user);
		return reviewRepository.findByUser(userName);
	}
	/**
	 * 
	 */
	
	public Boolean doesUserNameExist(String userName){
		if(getUser(userName) != null)
			return true;
		else
			return false;
	}
	/**
	 * 
	 */
	
	public void addUser(User user) throws UserExistException{
		if(user == null)
			throw new RuntimeException("");
		
		if(doesUserNameExist(user.getUserName())){
			throw new UserExistException("User already exists");
		}
		
		user = userRepository.save(user);
		if(user == null){
			throw new RuntimeException("");
		}
	}
	/**
	 * 
	 */
	public void removeUser(User user)throws UserDoesNotExistException{
		if(user == null){
			throw new RuntimeException("");
		}
		if(!doesUserNameExist(user.getUserName())){
			throw new UserDoesNotExistException("User does not exist");
		}
		 userRepository.delete(user);
	
	}
	/**
	 * 
	 */
	
	public void updateUser(User user) throws UserDoesNotExistException{
		if(user == null){
			throw new RuntimeException("");
		}
		if(!doesUserNameExist(user.getUserName())){
			throw new UserDoesNotExistException("User does not exist");
		}
	}
}
