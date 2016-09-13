package com.mindfire.review.services;

import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.BookExistException;
import com.mindfire.review.web.dto.BookDto;
import com.mindfire.review.web.dto.ChoiceDto;
import com.mindfire.review.web.models.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    /**
     * @return
     */
    List<Book> getBooks();

    /**
     * @param id
     * @return
     */
    public Book getBookById(Long id);

    /**
     * @param genre
     * @return
     */
    List<Book> getBookByGenre(String genre);

    /**
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
    List<Book> getBookByNameLike(String name);

    /**
     * @param name
     * @return
     */
    List<Author> getAuthorByBook(String name);

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
     * @param name
     * @return
     */
    List<User> getUserByBookReview(String name);

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
    public List<Book> getVerifiedBook(boolean verified);

    /**
     * @param name
     * @return
     */
    public List<ReviewAuthor> getAuthorReviewByBook(String name);

    /**
     * @param id
     * @param choiceDto
     */
    public void verifyBook(Long id, ChoiceDto choiceDto);

}