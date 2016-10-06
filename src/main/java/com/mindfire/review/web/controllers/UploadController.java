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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.services.UploadService;
import com.mindfire.review.services.ValidationService;

/**
 * @author pratyasa
 *
 */
@Controller
public class UploadController {

	@Autowired
	private ServletContext servletContext;

	private static final String UPLOAD_DIRECTORY = "/images";

	@RequestMapping(value = "/userupload", method = RequestMethod.GET)
	public Object uploadPage(HttpSession httpSession, HttpServletRequest httpServletRequest) throws Exception {

		if (httpSession.getAttribute("userName") != null) {
			ModelAndView modelAndView = new ModelAndView("userfileupload");
			// modelAndView.addObject("photofile", new UploadDto());
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
	public String saveFile(HttpServletRequest request,HttpSession httpSession, Model model ){

		if(!ServletFileUpload.isMultipartContent(request)){
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
			if(items.size() == 1){
				FileItem file = items.get(0);
//				ValidationService validationService = new ValidationService();
//				validationService.validate(file, bindingResult);
//				if(bindingResult.hasErrors())
//					return "redirect:/userupload";
					
					model.addAttribute("photo", UploadService.simpleUpload(file, httpSession, true));
					return "uploadsuccess";
				
			
			}
			} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		
		return ":redirect/userupload";
	}
}
