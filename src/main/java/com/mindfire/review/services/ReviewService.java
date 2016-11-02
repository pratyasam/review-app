package com.mindfire.review.services;

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.web.dto.ReviewAuthorDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import com.mindfire.review.web.models.*;


/**
 * Created by pratyasa on 10/8/16.
 */
public interface ReviewService {
	
    /**
     * This method is called by the admin or moderator, to add a review made by a user for a particular book, to the database.
     * This method throws an exception if the particular user has already reviewed a book.
     * @param reviewBookDto
     * @param userName
     * @param bookId
     * @throws AlreadyReviewedException
     */

    void addBookReview(ReviewBookDto reviewBookDto, String userName, Long bookId) throws AlreadyReviewedException;

    /**
     * This method is used to remove a review for a book, searched by the book review id.
     * This method can be called only by the admin or the moderator. This method throws an exception is the review does not exist.
     * @param reviewId
     * @throws ReviewDoesnotExistException
     */

    void removeBookReview(Long reviewId) throws ReviewDoesnotExistException;

    /**
     * This method is called by the admin or moderator to update a  book review made by the user, searched by the book review id.
     * This throws an exception if the review doesn't exist.
     * @param reviewBookDto
     * @param reviewId
     * @throws ReviewDoesnotExistException
     */

    void updateBookReview(ReviewBookDto reviewBookDto, Long reviewId) throws ReviewDoesnotExistException;

    /**
     * This method is called by the admin or the moderator to add a review made for the author, by the user.
     * The author is marked by the id and the user is marked by the user name.
     * This method throws and exception if the user has already reviewed the particular author.
     * @param reviewAuthorDto
     * @param authorId
     * @param userName
     * @throws AlreadyReviewedException
     */

    void addAuthorReview(ReviewAuthorDto reviewAuthorDto, Long authorId, String userName) throws AlreadyReviewedException;

    /**
     * This method is called to delete a particular review made by the user for the author, searched by the author review id.
     * This method throws and exception if the author review doesn't exist.
     * @param reviewAuthorId
     * @throws ReviewDoesnotExistException
     */

    void removeAuthorReview(Long reviewAuthorId) throws ReviewDoesnotExistException;

    /**
     * This method is called to update an existing author review.
     * This method throws an exception if the author review requested for does not exist.
     * @param reviewAuthorDto
     * @param reviewAuthorId
     * @throws ReviewDoesnotExistException
     */

    void updateAuthorReview(ReviewAuthorDto reviewAuthorDto, Long reviewAuthorId) throws ReviewDoesnotExistException;

    /**
     * This method returns a particular author review, searched by the author review id.
     * @param reviewAuthorId
     * @return ReviewAuthor
     */

     ReviewAuthor getReviewAuthorById(Long reviewAuthorId);

    /**
     * This method returns a particular book review, searched by the book review id.
     * @param reviewBookId
     * @return ReviewBook
     */

     ReviewBook getReviewBookById(Long reviewBookId);
     
     /**
      * This method returns the count of the author reviews liked by a particular user, searched by the user.
      * @param user
      * @return int
      */
     
     int getNumberOfAuthorReviewsLikedByTheUser(User user);
     
     /**
      * This method returns the count of the book reviews liked by a particular user, searched by the user.
      * @param user
      * @return int
      */
     
     int getNumberOfBookReviewsLikedByTheUser(User user);
     
     /**
      * This method is called to add a like for an author review. The user is marked by the user name.
      * This method throws an exception, if the user has already liked the author review.
      * @param userName
      * @param reviewAuthorId
      * @throws AlreadyReviewedException
      */
     
     void addLikeForAuthorReview(String userName, Long reviewAuthorId) throws AlreadyReviewedException;
     
     /**
      * This method is called to dislike an author review. The user is marked by the user id and the review is marked by the review id.
      * It throws an exception when the author review, requested for, doesn't exist.
      * @param userName
      * @param reviewAuthorLikeId
      */
     
     void removeLikeForAuthorReview(String userName, Long reviewAuthorId) throws ReviewDoesnotExistException;
     
     /**
      * This method is called to add a like for an book review. The user is marked by the user name.
      * This method throws an exception, if the user has already liked the book review.
      * @param userName
      * @param reviewBookId
      * @throws AlreadyReviewedException
      */
     
     void addLikeForBookReview(String userName, Long reviewBookId) throws AlreadyReviewedException;
     
     /**
      * This method is called to dislike an book review. The user is marked by the user id and the review is marked by the review id.
      * It throws an exception when the book review, requested for, doesn't exist.
      * @param userName
      * @param reviewBookLikeId
      * @throws ReviewDoesnotExistException
      */
     
     void removeLikeForBookReview(String userName, Long reviewBookId) throws ReviewDoesnotExistException;
     
     /**
      * This method returns the total number of likes for a particular book review, searched by the review.
      * @param book
      * @return int
      */
     
     int getNumberOfReviewLikesByBook(ReviewBook reviewBook);
     
     /**
      * This method returns the total number of likes for a particular author review, searched by the review.
      * @param author
      * @return int
      */
     
     int getNumberOfReviewLikesByAuthor(ReviewAuthor reviewAuthor);
     
     /**
      * This method returns the total number of dislikes for a particular book review, searched by the review.
      * @param book
      * @return int
      */
     
     int getNumberOfReviewDislikesByBook(ReviewBook reviewBook);
     
     /**
      * This method returns the total number of dislikes for a particular author review, searched by the review.
      * @param author
      * @return int
      */
     
     int getNumberOfReviewDislikesByAuthor(ReviewAuthor reviewAuthor);
     
     /**
      * This method returns the total number of reviews, both books and authors, liked by a user, searched by the user.
      * @param user
      * @return int
      */
     int getNumberOfReviewLikesByUser(User user);
}
