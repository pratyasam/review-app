package com.mindfire.review.services;

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.web.dto.ReviewAuthorDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewBook;

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
}
