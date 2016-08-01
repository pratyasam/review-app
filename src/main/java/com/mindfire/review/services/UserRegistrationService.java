/**
 * 
 */
package com.mindfire.review.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindfire.review.exceptions.UserExistException;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.UserRepository;

/**
 * @author pratyasa
 *
 */
@Service
public class UserRegistrationService {
	@Autowired
	private UserServiceInterface userService;
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	
	public Boolean doesUserNameExist(String userName){
		if(userService.getUser(userName) != null)
			return true;
		else
			return false;
	}
	
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
		
}
