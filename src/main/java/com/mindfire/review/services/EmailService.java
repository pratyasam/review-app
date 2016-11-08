package com.mindfire.review.services;

import com.mindfire.review.web.dto.EmailDto;

public interface EmailService {
	
	/**
	 * This method constructs a simple message mail and sends to the recipient.
	 * @param emailDto, which contains the email informations
	 */
	void sendEmailNewsLetter(EmailDto emailDto) ;
	
	/**
	 * This method accepts the userId of the user who forgot the password.
	 * It constructs a random confirmation key and sends it to the user's email address.
	 * @param userId
	 */
	void forgotPasswordEmail(Long userId);
		
	

}