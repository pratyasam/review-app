/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mindfire.review.web.dto.UploadDto;

/**
 * @author pratyasa
 *
 */
@Controller
public class UploadController {

	private static final String UPLOAD_DIRECTORY = "/images";

	@RequestMapping(value = "/userupload", method = RequestMethod.GET)
	public Object uploadPage(HttpSession httpSession, HttpServletRequest httpServletRequest) throws Exception {
		
		if (httpSession.getAttribute("userName") != null) {
			ModelAndView modelAndView = new ModelAndView("userfileupload");
			modelAndView.addObject("photofile", new UploadDto());
			return modelAndView;
		} else{
			String url = httpServletRequest.getRequestURI();
    		System.out.println(url);
    		if(url != null)
    		httpSession.setAttribute("url", url);
    		return "redirect:/login";
		}
			
	}
	
	@RequestMapping(value = "/userupload", method = RequestMethod.POST)
	public @ResponseBody  String saveFile(@Valid @ModelAttribute("photofile") UploadDto uploadDto, BindingResult bindingResult, HttpSession httpSession) {
		
		MultipartFile multipartFile = uploadDto.getFile();
		if(multipartFile.isEmpty() || bindingResult.hasErrors()){
			return "redirect:/userupload";
		}
		
		ServletContext context = httpSession.getServletContext();
		String path = context.getRealPath(UPLOAD_DIRECTORY);
		String fileName = multipartFile.getOriginalFilename();

		System.out.println(path + " " + fileName);

		File uploadDirectory = new File(path);
		if (!uploadDirectory.exists() || !uploadDirectory.isDirectory()) {
			
			uploadDirectory.mkdir();
			System.out.println("Directory Created");
		}

		
		BufferedOutputStream stream = null;
		try {
			byte[] bytes = multipartFile.getBytes();
			stream = new BufferedOutputStream(new FileOutputStream(new File(path + File.separator + fileName)));
			stream.write(bytes);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();			
			System.out.println("Couldn't upload file"+e.getMessage()); // handle the exception
		} 
		return "redirect:/thankyou";
	}
}
