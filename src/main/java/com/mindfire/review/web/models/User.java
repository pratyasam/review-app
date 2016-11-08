package com.mindfire.review.web.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "user_name", unique = true, nullable = false, length = 255)
    private String userName;
    
    @Column(name = "password", nullable = false, length = 264)
    private String userPassword;
    
    @Column(name = "email", nullable = false, length = 264)
    private String userEmail;
    
    @Column(name = "role", nullable = false, length = 45)
    private String role = "normal";
    
    @Column(name = "gender", nullable = false, length = 45)
    private String userGender;
    
    @Column(name = "verification", nullable = true, length = 405)
    private String userVerification = null;
    
    @Column(name = "image", length = 4005)
    private String userImage = "user.png";
    
    //bi-directional many-to-one association to ReviewAuthor
    @OneToMany(mappedBy = "user")
    private List<ReviewAuthor> reviewAuthors;
    
    //bi-directional many-to-one association to ReviewBook
    @OneToMany(mappedBy = "user")
    private List<ReviewBook> reviewBooks;

    public User() {
    	// empty
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String userType) {
        this.role = userType;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    

    public String getUserVerification() {
		return userVerification;
	}

	public void setUserVerification(String userVerification) {
		this.userVerification = userVerification;
	}

	public List<ReviewAuthor> getReviewAuthors() {
        return this.reviewAuthors;
    }

    public void setReviewAuthors(List<ReviewAuthor> reviewAuthors) {
        this.reviewAuthors = reviewAuthors;
    }

    public ReviewAuthor addReviewAuthor(ReviewAuthor reviewAuthor) {
        getReviewAuthors().add(reviewAuthor);
        reviewAuthor.setUser(this);

        return reviewAuthor;
    }

    public ReviewAuthor removeReviewAuthor(ReviewAuthor reviewAuthor) {
        getReviewAuthors().remove(reviewAuthor);
        reviewAuthor.setUser(null);

        return reviewAuthor;
    }

    public List<ReviewBook> getReviewBooks() {
        return this.reviewBooks;
    }

    public void setReviewBooks(List<ReviewBook> reviewBooks) {
        this.reviewBooks = reviewBooks;
    }

    
    
    public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	
	

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public ReviewBook addReview(ReviewBook reviewBook) {
        getReviewBooks().add(reviewBook);
        reviewBook.setUser(this);

        return reviewBook;
    }

    public ReviewBook removeReview(ReviewBook reviewBook) {
        getReviewBooks().remove(reviewBook);
        reviewBook.setUser(null);

        return reviewBook;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", user_firstName=" + firstName + ", user_lastName=" + lastName
                + ", userName=" + userName + ", userPassword=" + userPassword + ", role=" + role + "]";
    }


}