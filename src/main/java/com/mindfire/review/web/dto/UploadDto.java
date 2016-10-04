package com.mindfire.review.web.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadDto {
	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
