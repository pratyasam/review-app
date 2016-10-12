/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.services.AuthorService;
import com.mindfire.review.services.BookService;
import com.mindfire.review.services.UploadService;
import com.mindfire.review.services.UserService;
import com.mindfire.review.web.models.User;

/**
 * @author pratyasa
 *
 */
@Controller
public class UploadController {

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

	private final String UPLOAD_DIRECTORY = "/images";

	@RequestMapping(value = "/userupload", method = RequestMethod.GET)
	public Object uploadPage(HttpSession httpSession, HttpServletRequest httpServletRequest) throws Exception {

		if (httpSession.getAttribute("userName") != null) {
			ModelAndView modelAndView = new ModelAndView("userfileupload");
			return modelAndView;
		} else {
			String url = httpServletRequest.getRequestURI();
			System.out.println(url);
			if (url != null)
				httpSession.setAttribute("url", url);
			return "redirect:/login";
		}

	}

	@RequestMapping(value = "/userupload", method = RequestMethod.POST)
	public String saveFile(HttpServletRequest request, HttpSession httpSession, Model model) {

		User user = userService.getUser((String) httpSession.getAttribute("userName"));

		if (!ServletFileUpload.isMultipartContent(request)) {
			return "redirect:/userupload";
		}

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		/// Configure a repository (to ensure a secure temp location is used)
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> items = upload.parseRequest(request);
			if (items.size() == 1) {
				FileItem file = items.get(0);

				String imageName = uploadService.simpleUpload(file, httpSession, true);
				uploadService.uploadUserImage(user, imageName);
				model.addAttribute("photo", imageName);
				return "uploadsuccess";

			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return ":redirect/userupload";
	}
	
	@RequestMapping(value = "/{authorId}/authorupload", method = RequestMethod.GET)
	public Object authorUploadPage(@PathVariable("authorId") int authorId, HttpSession httpSession, HttpServletRequest httpServletRequest) throws Exception {

		if (httpSession.getAttribute("userName") != null) {
			ModelAndView modelAndView = new ModelAndView("authorupload");
			return modelAndView;
		} else {
			String url = httpServletRequest.getRequestURI();
			System.out.println(url);
			if (url != null)
				httpSession.setAttribute("url", url);
			return "redirect:/login";
		}

	}
	
	@RequestMapping(value = "/{authorId}/authorupload", method = RequestMethod.POST)
	public String saveAuthorFile(HttpServletRequest request, HttpSession httpSession, Model model) {
		if (!ServletFileUpload.isMultipartContent(request)) {
			return "redirect:/authorupload";
		}

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		/// Configure a repository (to ensure a secure temp location is used)
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> items = upload.parseRequest(request);
			if (items.size() == 1) {
				FileItem file = items.get(0);

				String imageName = uploadService.simpleUpload(file, httpSession, true);
				
				model.addAttribute("photo", imageName);
				return "redirect:/authors/{authorId}";

			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return ":redirect/authorupload";
	}
	
}
