package com.mindfire.review.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author pratyasa
 *
 */
public class LoginDto {
	@NotNull(message = "User Name cannot be blank")
	@Size(min = 3, max = 20)
	private String userName;
	@NotNull(message = "Password cannot be blank")
	@Size(min = 6)
	private String password;

	/**
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 *
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
