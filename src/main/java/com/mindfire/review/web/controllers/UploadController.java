/**
 * 
 */
package com.mindfire.review.web.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author pratyasa
 *
 */
@Controller
public class UploadController {

	private static final String UPLOAD_DIRECTORY = "/images";

	@RequestMapping(value = "/userupload", method = RequestMethod.GET)
	public String uploadPage(HttpSession httpSession) throws Exception {
		
		if (httpSession.getAttribute("userName") != null) {
			return "userfileupload";
		} else
			return "redirect:/login";
	}
	
	@RequestMapping(value = "/userupload", method = RequestMethod.POST)
	public @ResponseBody  String saveFile(@RequestParam(name="uploadFile") CommonsMultipartFile multipartFile, HttpSession httpSession) {
		
		if(multipartFile.isEmpty()){
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
