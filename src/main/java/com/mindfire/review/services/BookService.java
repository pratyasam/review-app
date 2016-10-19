package com.mindfire.review.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mindfire.review.exceptions.AlreadyReviewedException;
import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.BookExistException;
import com.mindfire.review.exceptions.ReviewDoesnotExistException;
import com.mindfire.review.web.dto.BookDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;

public interface BookService {
	/**
	 * 
	 * @return
	 */
	 List<Book> getBooks();

    /**
     * @return
     */
    Page<Book> getBooks(int pageno, int size);

    /**
     * @param id
     * @return
     */
    public Book getBookById(Long id);

    /**
     * @param genre
     * @return
     */
    Page<Book> getBookByGenre(String genre, boolean choice,int pageno, int size);
    /**
     * 
     * @param genre
     * @param pageno
     * @param size
     * @return
     */
    public Page<Book> getBookByGenreAdmin(String genre,int pageno, int size);
    /**
     * 
     * @param genre
     * @return
     */
    List<Book> getBookByGenre(String genre, boolean choice);
    /**
     * 
     * @param genre
     * @return
     */
    List<Book> getBookByGenreAdmin(String genre);

    /**
     * @param rating
     * @return
     */
    Page<Book> getBookByRating(float rating, int pageno, int size);
    /**
     * 
     * @param rating
     * @return
     */
    List<Book> getBookByRating(float rating);

    /**
     * @param isbn
     * @return
     */
    Book getBookByIsbn(String isbn);

    /**
     * @param name
     * @return
     */
    Page<Book> getBookByNameLikeAndIsbn(String name, String isbn, int page);
    /**
     * 
     * @param name
     * @return
     */
    Page<Book> getBookByNameLikeAdminAndIsbn(String name,String isbn, int page);

    /**
     * @param name
     * @return
     */
    List<Author> getAuthorByBook(String name);
    /**
     * 
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<Author> getAuthorByBook(String name, int pageno, int size);

    /**
     *
     * @param name
     * @return
     */
    Book getBookByName(String name);

    /**
     * @param name
     * @return
     */
    List<ReviewBook> getBookReviewByBook(String name);
    /**
     * 
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<ReviewBook> getBookReviewByBook(String name, int pageno, int size);

    /**
     * @param name
     * @return
     */
    List<User> getUserByBookReview(String name);
    /**
     * 
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<User> getUserByBookReview(String name, int pageno, int size);

    /**
     * @param isbn
     * @return
     */
    boolean doesBookExist(String isbn);

    /**
     * @param bookDto
     * @throws BookExistException
     */
    void addBook(BookDto bookDto) throws BookExistException;

    /**
     * @param id
     * @throws BookDoesNotExistException
     */
    void removeBook(Long id) throws BookDoesNotExistException;

    /**
     * @param bookId, bookDto
     * @throws BookDoesNotExistException
     */
    void updateBook(Long bookId, BookDto bookDto) throws BookDoesNotExistException;

    /**
     * @param verified
     * @return
     */
   Page<Book> getVerifiedBook(boolean verified, int pageno, int size);
   /**
    * 
    * @param verified
    * @return
    */
   List<Book> getVerifiedBook(boolean verified);


    /**
     * @param name
     * @return
     */
    public List<ReviewAuthor> getAuthorReviewByBook(String name);
    /**
     * 
     * @param name
     * @param pageno
     * @param size
     * @return
     */
    Page<ReviewAuthor> getAuthorReviewByBook(String name, int pageno, int size);

    /**
     * @param id
     * @param choiceDto
     */
    public void verifyBook(Long id, ChoiceDto choiceDto);
    
    /**
     * 
     * @param userName
     * @param bookId
     * @throws AlreadyReviewedException
     */
    
    void addBookLikeByUser(String userName, Long bookId)throws AlreadyReviewedException;
    
    /**
     * 
     * @param bookLikeId
     * @throws ReviewDoesnotExistException
     */
    
    void removeBookLikeByUser(String userName, Long bookId) throws ReviewDoesnotExistException;
    
    /**
     * 
     * @param bookId
     * @return
     */
    
    int getNumberOfBookLikesByUsers(Long bookId);
    
    /**
     * 
     * @param name
     * @return
     */
    
    int getTotalBookReviewByBook(String name);

    /**
     * 
     * @param name
     * @param genre
     * @param isbn
     * @param page
     * @return
     */
    Page<Book> searchForBooks(String name, String genre, String isbn, int page);
    
}