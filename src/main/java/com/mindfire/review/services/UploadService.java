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

	private final String UPLOAD_DIRECTORY = "/home/pratyasa/Desktop/Wotkspace/git/uploads";

	//private final String UPLOAD_DIRECTORY = "/home/pratyasa/Desktop/uploads";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	/**
	 * 
	 * @param file1
	 * @param httpSession
	 * @param encryption
	 * @return
	 */

	public String simpleUpload(FileItem file1, HttpSession httpSession, boolean encryption) {

		String fileName = null;
		MultipartFile file = new CommonsMultipartFile(file1);

		try {
			if (!(file).isEmpty()) {
				// ServletContext context = httpSession.getServletContext();
				// String path = context.getRealPath(UPLOAD_DIRECTORY);

				System.out.println(file.getSize());

				if (encryption) {
					String currentFileName = file.getOriginalFilename();
					String extension = currentFileName.substring(currentFileName.lastIndexOf("."),
							currentFileName.length());
					Long nameRandom = Calendar.getInstance().getTimeInMillis();
					String newFileName = nameRandom + extension;
					fileName = newFileName;
				} else
					fileName = file.getOriginalFilename();

				byte bytes[] = file.getBytes();
				File dir = new File(UPLOAD_DIRECTORY);

				if (!dir.exists()) {
					if (!dir.mkdir()) {
						throw new IOException("Unable to create directory");
					}

				}

				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
				bufferedOutputStream.write(bytes);
				bufferedOutputStream.close();
				System.out.println("\n file path = "+serverFile.getAbsolutePath());
				System.out.println("\n file name =" + fileName);
				//return fileName;  
				return serverFile.getAbsolutePath();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * 
	 * @param user
	 * @param fileName
	 */

	public void uploadUserImage(User user, String fileName) {
		System.out.println(fileName + "  " + user.getFirstName());
		user.setUserImage(fileName);
		userRepository.save(user);

	}

	/**
	 * 
	 * @param file1
	 * @param httpSession
	 * @param encryption
	 * @return
	 */

	public String simpleUploadAuthor(FileItem file1, HttpSession httpSession, boolean encryption) {

		String fileName = null;
		MultipartFile file = new CommonsMultipartFile(file1);

		try {
			if (!(file).isEmpty()) {
				// ServletContext context = httpSession.getServletContext();
				// String path = context.getRealPath(UPLOAD_DIRECTORY);

				System.out.println(file.getSize());

				if (encryption) {
					String currentFileName = file.getOriginalFilename();
					String extension = currentFileName.substring(currentFileName.lastIndexOf("."),
							currentFileName.length());
					Long nameRandom = Calendar.getInstance().getTimeInMillis();
					String newFileName = nameRandom + extension;
					fileName = newFileName;
				} else
					fileName = file.getOriginalFilename();

				byte bytes[] = file.getBytes();
				File dir = new File(UPLOAD_DIRECTORY);

				if (!dir.exists()) {
					if (!dir.mkdir()) {
						throw new IOException("Unable to create directory");
					}

				}

				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
				bufferedOutputStream.write(bytes);
				bufferedOutputStream.close();
				System.out.println(serverFile.getAbsolutePath());
				return fileName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * 
	 * @param author
	 * @param fileName
	 */

	public void uploadAuthorImage(Author author, String fileName) {

		author.setAuthorImage(fileName);
		authorRepository.save(author);

	}
	
	public String simpleUploadBook(FileItem file1, HttpSession httpSession, boolean encryption) {

		String fileName = null;
		MultipartFile file = new CommonsMultipartFile(file1);

		try {
			if (!(file).isEmpty()) {
				// ServletContext context = httpSession.getServletContext();
				// String path = context.getRealPath(UPLOAD_DIRECTORY);

				System.out.println(file.getSize());

				if (encryption) {
					String currentFileName = file.getOriginalFilename();
					String extension = currentFileName.substring(currentFileName.lastIndexOf("."),
							currentFileName.length());
					Long nameRandom = Calendar.getInstance().getTimeInMillis();
					String newFileName = nameRandom + extension;
					fileName = newFileName;
				} else
					fileName = file.getOriginalFilename();

				byte bytes[] = file.getBytes();
				File dir = new File(UPLOAD_DIRECTORY);

				if (!dir.exists()) {
					if (!dir.mkdir()) {
						throw new IOException("Unable to create directory");
					}

				}

				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
				bufferedOutputStream.write(bytes);
				bufferedOutputStream.close();
				System.out.println(serverFile.getAbsolutePath());
				return fileName;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	public void uploadBookImage(Book book, String fileName) {

		book.setBookImage(fileName);
		bookRepository.save(book);

	}

}
