/**
 * 
 */
package com.mindfire.review.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author pratyasa
 *
 */
public class UploadService{

	private static final String UPLOAD_DIRECTORY = "/home/pratyasa/Desktop/uploads";

	public static String simpleUpload(FileItem file1, HttpSession httpSession, boolean encryption) {
		
		String fileName = null;
		MultipartFile file = new CommonsMultipartFile(file1);
		
		try {
			if (!(file).isEmpty()) {
				//ServletContext context = httpSession.getServletContext();
				//String path = context.getRealPath(UPLOAD_DIRECTORY);
				
				
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
					if(!dir.mkdir()){
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

}
