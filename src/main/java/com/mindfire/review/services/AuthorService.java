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
     * @return
     */

    Page<Author> getAllAuthor(int pageno, int size);
  
    /**
     * 
     * @return
     */
    List<Author> getAllAuthor();

    /**
     * @param name
     * @return
     */

    Page<Author> getAuthorByNameLike(String name,int page);

    /**
     * @param name
     * @return
     */

    Author getAuthorByName(String name);

    /**
     * @param rating
     * @return
     */

    Page<Author> getAuthorByRating(float rating, int pageno, int size);
   
    /**
     * 
     * @param rating
     * @return
     */
    List<Author> getAuthorByRating(float rating);

    /**
     * @param genre
     * @return
     */

    Page<Author> getAuthorByGenre(String genre, int pageno, int size);

    /**
     * 
     * @param genre
     * @return
     */
    List<Author> getAuthorByGenre(String genre);

    /**
     * @param name
     * @return
     */

    List<ReviewAuthor> getAuthorReviewByAuthorName(String name);
    /**
     * 
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<ReviewAuthor> getAuthorReviewByAuthorName(String name, int pageno, int size);
    /**
     * 
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<ReviewBook> getBookReviewByAuthorName(String name, int pageno, int size);

    /**
     * @param name
     * @return
     */

    List<Book> getBookByAuthor(String name);

    /**
     * @param name
     * @return
     */

    List<User> getUserByAuthor(String name);
    /**
     * 
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<User> getUserByAuthor(String name, int pageno, int size);

    /**
     * @param name
     * @return
     */
    List<ReviewBook> getBookReviewByAuthorName(String name);
    /**
     * 
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<Book> getBookByAuthor(String name, int pageno, int size);

    /**
     *
     * @param authorId
     * @return
     */
     Author getAuthorById(Long authorId);

    /**
     *
     * @param authorDto
     * @throws AuthorExistenceException
     */
     void addAuthor(AuthorDto authorDto) throws AuthorExistenceException;

    /**
     *
     * @param authorDto
     * @param authorId
     * @throws AuthorExistenceException
     */
    void updateAuthor(AuthorDto authorDto, Long authorId) throws AuthorExistenceException;

    /**
     *
     * @param authorId
     * @throws AuthorExistenceException
     */
    public void removeAuthor(Long authorId) throws AuthorExistenceException;
    
    /**
     * 
     * @param userName
     * @param authorId
     * @throws AlreadyReviewedException
     */
    
    void addAuthorLikeByUser(String userName, Long authorId) throws AlreadyReviewedException;
    
    /**
     * 
     * @param authorLikeId
     * @throws ReviewDoesnotExistException
     */
    
    void removeAuthorLikeByUser(String userName, Long authorId) throws ReviewDoesnotExistException;
    
    /**
     * 
     * @param authorId
     * @return
     */
    
    int getNumberOfLikesByUser(Long authorId);
    
    /**
     * 
     * @param name
     * @return
     */
    
    int getTotalAuthorReviewByAuthorName(String name);
    
    /**
     * 
     * @return
     */
    List<Author> getTop10Authors();

}