package com.mindfire.review.services;

import java.util.List;

import org.springframework.data.domain.Page;
import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.AuthorExistenceException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.web.dto.AuthorDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;

public interface AuthorService {

    /**
     * returns all the authors in page format
     * @return
     */

    Page<Author> getAllAuthor(int pageno, int size);
  
    /**
     * returns all the authors in list format
     * @return
     */
    List<Author> getAllAuthor();

    /**
     * returns the authors with name similar to the parameter in page form
     * @param name
     * @param page
     * @return
     */

    Page<Author> getAuthorByNameLike(String name,int page);

    /**
     * returns specific author
     * @param name
     * @return
     */

    Author getAuthorByName(String name);

    /**
     * returns authors by rating in page form
     * @param rating
     * @param pageno
     * @param size
     * @return
     */

    Page<Author> getAuthorByRating(float rating, int pageno, int size);
   
    /**
     * returns authors by rating in list
     * @param rating
     * @return
     */
    List<Author> getAuthorByRating(float rating);

    /**
     * returns authors by genre in page form
     * @param genre
     * @param pageno
     * @param size
     * @return
     */

    Page<Author> getAuthorByGenre(String genre, int pageno, int size);

    /**
     * returns list of authors by genre
     * @param genre
     * @return
     */
    List<Author> getAuthorByGenre(String genre);

    /**
     * returns list of reviews for an author
     * @param name
     * @return
     */

    List<ReviewAuthor> getAuthorReviewByAuthorName(String name);
    /**
     * returns list of reviews for an author in page form
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<ReviewAuthor> getAuthorReviewByAuthorName(String name, int pageno, int size);
    /**
     * return book reviews for specific author
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<ReviewBook> getBookReviewByAuthorName(String name, int pageno, int size);

    /**
     * returns list of books by author
     * @param name
     * @return
     */

    List<Book> getBookByAuthor(String name);

    /**
     * returns list of users who reviewed the author
     * @param name
     * @return
     */

    List<User> getUserByAuthor(String name);
    /**
     * returns list of users who reviewed the author in page form
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<User> getUserByAuthor(String name, int pageno, int size);

    /**
     * 
     * @param name
     * @return
     */
    List<ReviewBook> getBookReviewByAuthorName(String name);
    /**
     * get books by the author in page form
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<Book> getBookByAuthor(String name, int pageno, int size);

    /**
     * find an author
     * @param authorId
     * @return
     */
     Author getAuthorById(Long authorId);

    /**
     * adds the author to the database
     * @param authorDto
     * @throws AuthorExistenceException
     */
     void addAuthor(AuthorDto authorDto) throws AuthorExistenceException;

    /**
     * update author details
     * @param authorDto
     * @param authorId
     * @throws AuthorExistenceException
     */
    void updateAuthor(AuthorDto authorDto, Long authorId) throws AuthorExistenceException;

    /**
     * deletes the author
     * @param authorId
     * @throws AuthorExistenceException
     */
    public void removeAuthor(Long authorId) throws AuthorExistenceException;
    
    /**
     * like the author
     * @param userName
     * @param authorId
     * @throws AlreadyReviewedException
     */
    
    void addAuthorLikeByUser(String userName, Long authorId) throws AlreadyReviewedException;
    
    /**
     * remove the author like
     * @param authorLikeId
     * @throws ReviewDoesnotExistException
     */
    
    void removeAuthorLikeByUser(String userName, Long authorId) throws ReviewDoesnotExistException;
    
    /**
     * returns number of likes for an author
     * @param authorId
     * @return
     */
    
    int getNumberOfLikesByUser(Long authorId);
    
    /**
     * returns total number of reviews made on the author
     * @param name
     * @return
     */
    
    int getTotalAuthorReviewByAuthorName(String name);
    
    /**
     * returns top 10 authors whose likes is greater than 0
     * @return
     */
    List<Author> getTop10Authors();

}