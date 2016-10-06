package com.mindfire.review.web.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadDto {
	
	private MultipartFile fileObject;

	public MultipartFile getFileObject() {
		return fileObject;
	}

	public void setFileObject(MultipartFile fileObject) {
		this.fileObject = fileObject;
	}


	
	

}
