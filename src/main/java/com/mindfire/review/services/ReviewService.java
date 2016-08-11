package com.mindfire.review.services;

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.web.dto.ReviewAuthorDto;
import com.mindfire.review.web.dto.ReviewBookDto;
import com.mindfire.review.web.models.*;
import com.mindfire.review.web.repositories.AuthorRepository;
import com.mindfire.review.web.repositories.ReviewAuthorRepository;
import com.mindfire.review.web.repositories.ReviewBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pratyasa on 9/8/16.
 */
@Service
public class ReviewService implements ReviewServiceInterface {
    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private BookServiceInterface bookService;
    @Autowired
    private AuthorServiceInterface authorService;
    @Autowired
    private ReviewBookRepository reviewBookRepository;
    @Autowired
    private ReviewAuthorRepository reviewAuthorRepository;
    @Autowired
    private AuthorRepository authorRepository;


    /**
     * Add Review service for Book
     *
     * @param reviewBookDto
     * @param userName
     * @param bookId
     * @throws AlreadyReviewedException
     */

    @Override
    public void addBookReview(ReviewBookDto reviewBookDto, String userName, Long bookId) throws AlreadyReviewedException {
        Book book = bookService.getBookById(bookId);
        User user = userService.getUser(userName);
        if (reviewBookDto == null) {
            throw new RuntimeException("");
        }

        if (reviewBookRepository.findByUserAndBook(user, book) != null) {
            throw new AlreadyReviewedException("You have already Reviewed this book.");
        }
        ReviewBook reviewBook = new ReviewBook();
        reviewBook.setReviewText(reviewBookDto.getReviewText());

        reviewBook = reviewBookRepository.save(reviewBook);
        reviewBook.setUser(user);
        reviewBook.setBook(book);
        System.out.println("Book review Saved");
        if (reviewBook == null) {
            throw new RuntimeException("");
        }
    }

    /**
     * Remove Review service for Book
     *
     * @param reviewId
     * @throws ReviewDoesnotExistException
     */

    @Override
    public void removeBookReview(Long reviewId) throws ReviewDoesnotExistException {
        if (reviewBookRepository.findOne(reviewId) == null) {
            throw new ReviewDoesnotExistException("The Review doesn't exit");
        }
        reviewBookRepository.delete(reviewBookRepository.findByReviewBookId(reviewId));
    }

    /**
     * Update Review service for Book
     *
     * @param reviewBookDto
     * @param reviewId
     * @throws ReviewDoesnotExistException
     */
    @Override
    public void updateBookReview(ReviewBookDto reviewBookDto, Long reviewId) throws ReviewDoesnotExistException {
        ReviewBook rb = reviewBookRepository.findOne(reviewId);
        if (rb == null) {
            throw new ReviewDoesnotExistException("The Book Review doesn't exit.");
        }
        rb.setReviewText(reviewBookDto.getReviewText());
        reviewBookRepository.save(rb);

    }

    /**
     * Add Review service for Author
     *
     * @param reviewAuthorDto,
     * @param authorId
     * @param userName
     * @throws AlreadyReviewedException
     */
    @Override
    public void addAuthorReview(ReviewAuthorDto reviewAuthorDto, Long authorId, String userName) throws AlreadyReviewedException {
        User user = userService.getUser(userName);
        Author author = authorRepository.findOne(authorId);
        if (reviewAuthorDto == null) {
            throw new RuntimeException("");
        }
        if (reviewAuthorRepository.findByUserAndAuthor(user, author) != null) {
            throw new AlreadyReviewedException("You have already reviewed this author.");
        }
        ReviewAuthor reviewAuthor = new ReviewAuthor();
        reviewAuthor.setReviewAuthorText(reviewAuthorDto.getReviewText());
        reviewAuthor.setUser(user);
        reviewAuthor.setAuthor(author);
        reviewAuthor = reviewAuthorRepository.save(reviewAuthor);
        System.out.println("Author review saved");
        if (reviewAuthor == null) {
            throw new RuntimeException("");
        }
    }

    /**
     * Remove Review service for Author
     *
     * @param reviewAuthorId
     * @throws ReviewDoesnotExistException
     */
    @Override
    public void removeAuthorReview(Long reviewAuthorId) throws ReviewDoesnotExistException {
        if (reviewAuthorRepository.findOne(reviewAuthorId) == null) {
            throw new ReviewDoesnotExistException("The Author Review doesn't exist.");
        }
        reviewAuthorRepository.delete(reviewAuthorRepository.findOne(reviewAuthorId));
    }

    /**
     * Update Review service for Author
     *
     * @param reviewAuthorDto
     * @param reviewAuthorId
     * @throws ReviewDoesnotExistException
     */

    @Override
    public void updateAuthorReview(ReviewAuthorDto reviewAuthorDto, Long reviewAuthorId) throws ReviewDoesnotExistException {
        ReviewAuthor reviewAuthor = reviewAuthorRepository.findOne(reviewAuthorId);
        if(reviewAuthor == null){
            throw new ReviewDoesnotExistException("The Author Review doesn't not exist.");
        }
        reviewAuthor.setReviewAuthorText(reviewAuthorDto.getReviewText());
        reviewAuthorRepository.save(reviewAuthor);
    }

    @Override
    public ReviewAuthor getReviewAuthorById(Long reviewAuthorId){
        return reviewAuthorRepository.findOne(reviewAuthorId);
    }
    @Override
    public ReviewBook getReviewBookById(Long reviewBookId){
        return reviewBookRepository.findOne(reviewBookId);
    }


}
