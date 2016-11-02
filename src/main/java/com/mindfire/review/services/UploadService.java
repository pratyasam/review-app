/**
 * 
 */
package com.mindfire.review.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.User;
import com.mindfire.review.web.repositories.AuthorRepository;
import com.mindfire.review.web.repositories.BookRepository;
import com.mindfire.review.web.repositories.UserRepository;

/**
 * @author pratyasa
 *
 */

@Service
public class UploadService {

	public static final String UPLOADDIRECTORY = "/home/pratyasa/Desktop/Wotkspace/git/uploads";
	public static final String IOMESSAGE = "unable to create directory.";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	/**
	 * method to upload user image and return the file name.
	 * @param file1
	 * @param httpSession
	 * @param encryption
	 * @return String
	 */

	public String simpleUpload(FileItem file1, boolean encryption) {

		String fileName = null;
		MultipartFile file = new CommonsMultipartFile(file1);

		try {
			if (!(file).isEmpty()) {
				

				if (encryption) {
					String currentFileName = file.getOriginalFilename();
					String extension = currentFileName.substring(currentFileName.lastIndexOf('.'),
							currentFileName.length());
					Long nameRandom = Calendar.getInstance().getTimeInMillis();
					String newFileName = nameRandom + extension;
					fileName = newFileName;
				} else
					fileName = file.getOriginalFilename();

				byte[] bytes = file.getBytes();
				File dir = new File(UPLOADDIRECTORY);

				if (!dir.exists() && !dir.mkdir()) {
					
						throw new IOException(IOMESSAGE);
					

				}

				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
				bufferedOutputStream.write(bytes);
				bufferedOutputStream.close();
				 
				return serverFile.getAbsolutePath();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * persist user image in database
	 * @param user
	 * @param fileName
	 */

	public void uploadUserImage(User user, String fileName) {
		user.setUserImage(fileName);
		userRepository.save(user);

	}

	/**
	 * method to upload author image and return the file name.
	 * @param file1
	 * @param httpSession
	 * @param encryption
	 * @return String
	 */

	public String simpleUploadAuthor(FileItem file1, boolean encryption) {

		String fileName = null;
		MultipartFile file = new CommonsMultipartFile(file1);

		try {
			if (!(file).isEmpty()) {

				if (encryption) {
					String currentFileName = file.getOriginalFilename();
					String extension = currentFileName.substring(currentFileName.lastIndexOf('.'),
							currentFileName.length());
					Long nameRandom = Calendar.getInstance().getTimeInMillis();
					String newFileName = nameRandom + extension;
					fileName = newFileName;
				} else
					fileName = file.getOriginalFilename();

				byte[] bytes = file.getBytes();
				File dir = new File(UPLOADDIRECTORY);

				if (!dir.exists() && !dir.mkdir()) {
					
						throw new IOException(IOMESSAGE);
					
				}

				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
				bufferedOutputStream.write(bytes);
				bufferedOutputStream.close();
				return fileName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * persist author image in database
	 * @param author
	 * @param fileName
	 */

	public void uploadAuthorImage(Author author, String fileName) {

		author.setAuthorImage(fileName);
		authorRepository.save(author);

	}
	/**
	 * method to upload book image and return the file name.
	 * @param file1
	 * @param httpSession
	 * @param encryption
	 * @return String
	 */
	
	public String simpleUploadBook(FileItem file1, boolean encryption) {

		String fileName = null;
		MultipartFile file = new CommonsMultipartFile(file1);

		try {
			if (!(file).isEmpty()) {

				if (encryption) {
					String currentFileName = file.getOriginalFilename();
					String extension = currentFileName.substring(currentFileName.lastIndexOf('.'),
							currentFileName.length());
					Long nameRandom = Calendar.getInstance().getTimeInMillis();
					String newFileName = nameRandom + extension;
					fileName = newFileName;
				} else
					fileName = file.getOriginalFilename();

				byte[] bytes = file.getBytes();
				File dir = new File(UPLOADDIRECTORY);

				if (!dir.exists() && !dir.mkdir()) {
					
						throw new IOException(IOMESSAGE);
					}

				

				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
				bufferedOutputStream.write(bytes);
				bufferedOutputStream.close();
				return fileName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	/**
	 * persist the image name in database
	 * @param book
	 * @param fileName
	 */
	public void uploadBookImage(Book book, String fileName) {

		book.setBookImage(fileName);
		bookRepository.save(book);

	}

}
