package com.mindfire.review.web.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by pratyasa on 10/8/16.
 */
public class AuthorDto {

    @NotNull(message = "Author Description cannot be null")
    private String authorDescription;

    @NotNull(message = "Author Genre cannot be null")
    private String authorGenre;

    @NotNull(message = "Author Name cannot be null")
    private String authorName;
    
    @Min(value = 1, message = "Rating should be atleast 1")
    @Max(value = 5, message = "Rating should be less than equal to 5")
    private float authorRating;
  
//	private MultipartFile fileupload;

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }

    public String getAuthorGenre() {
        return authorGenre;
    }

    public void setAuthorGenre(String authorGenre) {
        this.authorGenre = authorGenre;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public float getAuthorRating() {
        return authorRating;
    }

    public void setAuthorRating(float authorRating) {
        this.authorRating = authorRating;
    }

//	public MultipartFile getFileupload() {
//		return fileupload;
//	}
//
//	public void setFileupload(MultipartFile fileupload) {
//		this.fileupload = fileupload;
//	}
    
    
}
