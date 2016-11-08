/**
 * 
 */
package com.mindfire.review.services;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mindfire.review.web.controllers.EmailController;
import com.mindfire.review.web.dto.EmailDto;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.UserRepository;

/**
 * @author pratyasa
 *
 */
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender mailSender;
	@Autowired
	private UserService userService;
	@Autowired
	UserRepository userRepository;

	static final Logger logger = LoggerFactory.getLogger(EmailController.class);

	/**
	 * This method constructs a simple message mail and sends to the recipient.
	 * 
	 * @param emailDto, which contains the email informations
	 */
	@Override
	public void sendEmailNewsLetter(EmailDto emailDto) {
		
		// takes input from e-mail form
		String recipientAddress = emailDto.getRecipient();
		String subject = emailDto.getSubject();
		String message = emailDto.getMessage();

		// prints debug info
		logger.info("To : " + recipientAddress);
		logger.info("Subject : " + subject);
		logger.info("message : " + message);

		// creates a simple e-mail object
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message);

		// sends the NewsLetter
		mailSender.send(email);
	}

	/**
	 * This method accepts the userId of the user who forgot the password. It
	 * constructs a random confirmation key and sends it to the user's email
	 * address.
	 * 
	 * @param userId
	 */
	@Override
	public void forgotPasswordEmail(Long userId) {

		User user = userService.getUserById(userId);
		String passwordCode = user.getFirstName() + Calendar.getInstance().getTimeInMillis();
		user.setUserVerification(passwordCode);
		userRepository.save(user);
		String recipient = user.getUserEmail();
		String subject = "Password recovery action.";

		String message = "Hi " + user.getFirstName()
				+ ", \n Please enter the following verification code, when prompted.\n \t" + passwordCode
				+ "\n Please ignore if it was not you.";

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipient);
		email.setSubject(subject);
		email.setText(message);

		// send the password recovery mail.
		mailSender.send(email);

	}

}
