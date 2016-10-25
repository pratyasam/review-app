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
     *
     * @param reviewBookDto
     * @param userName
     * @param bookId
     * @throws AlreadyReviewedException
     */

    void addBookReview(ReviewBookDto reviewBookDto, String userName, Long bookId) throws AlreadyReviewedException;

    /**
     *
     * @param reviewId
     * @throws ReviewDoesnotExistException
     */

    void removeBookReview(Long reviewId) throws ReviewDoesnotExistException;

    /**
     *
     * @param reviewBookDto
     * @param reviewId
     * @throws ReviewDoesnotExistException
     */

    void updateBookReview(ReviewBookDto reviewBookDto, Long reviewId) throws ReviewDoesnotExistException;

    /**
     *
     * @param reviewAuthorDto
     * @param authorId
     * @param userName
     * @throws AlreadyReviewedException
     */

    void addAuthorReview(ReviewAuthorDto reviewAuthorDto, Long authorId, String userName) throws AlreadyReviewedException;

    /**
     *
     * @param reviewAuthorId
     * @throws ReviewDoesnotExistException
     */

    void removeAuthorReview(Long reviewAuthorId) throws ReviewDoesnotExistException;

    /**
     *
     * @param reviewAuthorDto
     * @param reviewAuthorId
     * @throws ReviewDoesnotExistException
     */

    void updateAuthorReview(ReviewAuthorDto reviewAuthorDto, Long reviewAuthorId) throws ReviewDoesnotExistException;

    /**
     *
     * @param reviewAuthorId
     * @return
     */

     ReviewAuthor getReviewAuthorById(Long reviewAuthorId);

    /**
     *
     * @param reviewBookId
     * @return
     */

     ReviewBook getReviewBookById(Long reviewBookId);
     /**
      * 
      * @param user
      * @return
      */
     
     int getNumberOfAuthorReviewsLikedByTheUser(User user);
     
     /**
      * 
      * @param user
      * @return
      */
     
     int getNumberOfBookReviewsLikedByTheUser(User user);
     
     /**
      * 
      * @param userName
      * @param reviewAuthorId
      * @throws AlreadyReviewedException
      */
     
     void addLikeForAuthorReview(String userName, Long reviewAuthorId) throws AlreadyReviewedException;
     
     /**
      * 
      * @param reviewAuthorLikeId
      */
     
     void removeLikeForAuthorReview(String userName, Long reviewAuthorId) throws ReviewDoesnotExistException;
     
     /**
      * 
      * @param userName
      * @param reviewBookId
      * @throws AlreadyReviewedException
      */
     
     void addLikeForBookReview(String userName, Long reviewBookId) throws AlreadyReviewedException;
     
     /**
      * 
      * @param reviewBookLikeId
      * @throws ReviewDoesnotExistException
      */
     
     void removeLikeForBookReview(String userName, Long reviewBookId) throws ReviewDoesnotExistException;
     
     /**
      * 
      * @param book
      * @return
      */
     
     int getNumberOfReviewLikesByBook(ReviewBook reviewBook);
     
     /**
      * 
      * @param author
      * @return
      */
     
     int getNumberOfReviewLikesByAuthor(ReviewAuthor reviewAuthor);
     
     /**
      * 
      * @param book
      * @return
      */
     
     int getNumberOfReviewDislikesByBook(ReviewBook reviewBook);
     
     /**
      * 
      * @param author
      * @return
      */
     
     int getNumberOfReviewDislikesByAuthor(ReviewAuthor reviewAuthor);
     
     /**
      * 
      * @param user
      * @return
      */
     int getNumberOfReviewLikesByUser(User user);
}
