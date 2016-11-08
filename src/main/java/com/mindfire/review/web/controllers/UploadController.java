/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.UserDoesNotExistException;
import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookService;
import com.mindfire.review.services.UploadService;
import com.mindfire.review.services.UserService;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.User;

/**
 * @author pratyasa
 *
 */
@Controller
public class UploadController {
	
	public static final String USERNAME = "userName";
	public static final String REDIRECTLOGIN = "redirect:/login";
	public static final String PATHNAME = "javax.servlet.context.tempdir";
	public static final String IMAGEURL = "imageurl";
	public static final String IMAGENAME = "imagename";
    public static final String DEFAULT_ERROR_VIEW = "resourcenotfound";
    public static final String ERRORINTERNAL = "internalerror";
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private BookService bookService;

	/**
	 * to get the page for user picture upload
	 * @param httpSession
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userupload", method = RequestMethod.GET)
	public Object uploadPage(HttpSession httpSession, HttpServletRequest httpServletRequest)  {

		if (httpSession.getAttribute(USERNAME) != null) {
			return new ModelAndView("userfileupload");
		} else {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECTLOGIN;
		}

	}

	/**
	 * to save the picture uploaded by user
	 * @param request
	 * @param httpSession
	 * @param model
	 * @return
	 * @throws FileUploadException
	 */
	@RequestMapping(value = "/userupload", method = RequestMethod.POST)
	public String saveFile(HttpServletRequest request, HttpSession httpSession, Model model) throws FileUploadException {

		User user = userService.getUser((String) httpSession.getAttribute(USERNAME));

		if (!ServletFileUpload.isMultipartContent(request)) {
			return "redirect:/userupload";
		}

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		/// Configure a repository (to ensure a secure temp location is used)
		File repository = (File) servletContext.getAttribute(PATHNAME);
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> items = upload.parseRequest(request);
			if (items.size() == 1) {
				FileItem file = items.get(0);

				String imageUrl = uploadService.simpleUpload(file, true);
				String imageName = imageUrl.substring(imageUrl.lastIndexOf('/')+1, imageUrl.length());

				uploadService.uploadUserImage(user, imageName);
				model.addAttribute(IMAGEURL, imageUrl);
				model.addAttribute(IMAGENAME,imageName);
				
				return "redirect:/profile";

			}
		} catch (FileUploadException e) {
			throw e;
		}

		return "redirect:/userupload";
	}
	

	
	/**
	 * to get the page to upload the author picture
	 * @param authorId
	 * @param httpSession
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/authors/{authorId}/authorupload", method = RequestMethod.GET)
	public Object authorUploadPage(@PathVariable("authorId") int authorId, HttpSession httpSession, HttpServletRequest httpServletRequest) {

		if (httpSession.getAttribute(USERNAME) != null) {
			ModelAndView modelAndView = new ModelAndView("authorupload");
			modelAndView.addObject("authorId",authorId);
			return modelAndView;
		} else {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECTLOGIN;
		}

	}
	
	/**
	 * to save the author image
	 * @param authorId
	 * @param request
	 * @param httpSession
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authors/{authorId}/authorupload", method = RequestMethod.POST)
	public String saveAuthorFile(@PathVariable("authorId") Long authorId, HttpServletRequest request, HttpSession httpSession, Model model) throws FileUploadException {
		
		Author author = authorService.getAuthorById(authorId);
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			return "redirect:/authorupload";
		}

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		/// Configure a repository (to ensure a secure temp location is used)
		File repository = (File) servletContext.getAttribute(PATHNAME);
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> items = upload.parseRequest(request);
			if (items.size() == 1) {
				FileItem file = items.get(0);

				String imageUrl = uploadService.simpleUpload(file, true);
				String imageName = imageUrl.substring(imageUrl.lastIndexOf('/')+1, imageUrl.length());
				uploadService.uploadAuthorImage(author, imageName);
				model.addAttribute(IMAGEURL, imageUrl);
				model.addAttribute(IMAGENAME,imageName);
				return "redirect:/authors/{authorId}";

			}
		} catch (FileUploadException e) {
			 throw e;
		}

		return "redirect:/authors/{authorId}/authorupload";
	}
	
	/**
	 * to get the page to upload book image
	 * @param bookId
	 * @param httpSession
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/books/{bookId}/bookupload", method = RequestMethod.GET)
	public Object bookUploadPage(@PathVariable("bookId") int bookId, HttpSession httpSession, HttpServletRequest httpServletRequest)  {

		if (httpSession.getAttribute(USERNAME) != null) {
			ModelAndView modelAndView = new ModelAndView("bookupload");
			modelAndView.addObject("bookId",bookId);
			return modelAndView;
		} else {
			String url = httpServletRequest.getRequestURI();
			if (url != null)
				httpSession.setAttribute("url", url);
			return REDIRECTLOGIN;
		}

	}
	
	/**
	 * to save the book image uploaded
	 * @param bookId
	 * @param request
	 * @param httpSession
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/books/{bookId}/bookupload", method = RequestMethod.POST)
	public String saveBookFile(@PathVariable("bookId") Long bookId, HttpServletRequest request, HttpSession httpSession, Model model) throws FileUploadException{
		
		Book book = bookService.getBookById(bookId);
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			return "redirect:/books/{bookId}/bookupload";
		}

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		/// Configure a repository (to ensure a secure temp location is used)
		File repository = (File) servletContext.getAttribute(PATHNAME);
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> items = upload.parseRequest(request);
			if (items.size() == 1) {
				FileItem file = items.get(0);

				String imageUrl = uploadService.simpleUpload(file, true);
				String imageName = imageUrl.substring(imageUrl.lastIndexOf('/')+1, imageUrl.length());
				uploadService.uploadBookImage(book, imageName);
				model.addAttribute(IMAGEURL, imageUrl);
				model.addAttribute(IMAGENAME,imageName);
				return "redirect:/books/{bookId}";

			}
		} catch (FileUploadException e) {
			throw e;
		}

		return "redirect:/books/{bookId}/bookupload";
	}
	
	/**
	 * Exception handler for handling 404 exceptions.
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({FileNotFoundException.class,BookDoesNotExistException.class, UserDoesNotExistException.class})
	public String defaultErrorHandler(HttpServletRequest request, Exception ex) {
		
		logger.error("Resource not found"+" Error Occured:: URL="+request.getRequestURL());
		// Send the user to a resource not found error-view.
		return DEFAULT_ERROR_VIEW;
	}
	
	/**
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({RuntimeException.class, FileUploadException.class})
	public String internalError(HttpServletRequest request, Exception ex) {
		
		logger.info("Internal Occured:: URL="+request.getRequestURL());
		return ERRORINTERNAL;
	}
	
}
