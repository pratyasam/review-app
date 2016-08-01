package com.mindfire.review.web.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id", unique=true, nullable=false)
	private Long userId;

	@Column(name="user_firstName", nullable=false, length=50)
	private String firstName;

	@Column(name="user_lastName", nullable=false, length=50)
	private String lastName;

	@Column(name="user_name", unique=true, nullable=false, length=255)
	private String userName;

	@Column(name="user_password", nullable=false, length=45)
	private String userPassword;

	@Column(name="user_type", nullable=false, length=45)
	private String userType;
	
	@Column(name="user_gender", nullable=false, length=45)
	private String userGender;


	//bi-directional many-to-one association to ReviewAuthor
	@OneToMany(mappedBy="user")
	private List<ReviewAuthor> reviewAuthors;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="user")
	private List<Review> reviews;

	public User() {
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUser_firstName() {
		return this.firstName;
	}

	public void setUser_firstName(String user_firstName) {
		this.firstName = user_firstName;
	}

	public String getUser_lastName() {
		return this.lastName;
	}

	public void setUser_lastName(String user_lastName) {
		this.lastName = user_lastName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
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

	public List<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setUser(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setUser(null);

		return review;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", user_firstName=" + firstName + ", user_lastName=" + lastName
				+ ", userName=" + userName + ", userPassword=" + userPassword + ", userType=" + userType + "]";
	}

	
	
}