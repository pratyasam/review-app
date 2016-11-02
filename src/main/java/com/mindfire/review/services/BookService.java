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
	 * returns the list of all the books present in the database.
	 * @return List<Book>
	 */
	 List<Book> getBooks();

    /**
     * returns a specified page consisting of specified number of books from the database.
     * @param int
     * @param int
     * @return Page<Book>
     */
    Page<Book> getBooks(int pageno, int size);

    /**
     * returns a book by its id.
     * @param id
     * @return Book
     */
    public Book getBookById(Long id);

    /**
     * returns a specified page consisting of a specified number of book from the database, searched by the genre name.
     * @param genre
     * @param boolean
     * @param int
     * @param int
     * @return Page<Book>
     */
    Page<Book> getBookByGenre(String genre, boolean choice,int pageno, int size);
    
    /**
     * returns a specific page consisting of a specified number of verified and non verified books from the database, searched by the genre name.
     * @param genre
     * @param pageno
     * @param size
     * @return Page<Book>
     */
    public Page<Book> getBookByGenreAdmin(String genre,int pageno, int size);
    
    /**
     * returns the list of all the verified or non verified books according to choice, by searching the genre name.
     * @param genre
     * @param choice
     * @return List<Book>
     */
    List<Book> getBookByGenre(String genre, boolean choice);
    
    /**
     * returns all the verified and non verified books for the admin, by searching the genre name.
     * @param genre
     * @return List<Book>
     */
    List<Book> getBookByGenreAdmin(String genre);

    /**
     * return a list of verified and non verified books in page format,by searching the rating.
     * @param rating
     * @param pageno
     * @param size
     * @return Page<Book>
     */
    Page<Book> getBookByRating(float rating, int pageno, int size);
    
    /**
     * returns a list of verified and non verified books, by searching the rating.
     * @param rating
     * @return List<Book>
     */
    List<Book> getBookByRating(float rating);

    /**
     * returns a book by searching for its ISBN.
     * @param isbn
     * @return Book
     */
    Book getBookByIsbn(String isbn);

    /**
     * returns a list of verified books in page form, by searching the book name similar to the input name and ISBN.
     * @param name
     * @param isbn
     * @param page
     * @return  Page<Book>
     */
    Page<Book> getBookByNameLikeAndIsbn(String name, String isbn, int page);
    
    /**
     * returns a list of verified and non verified books for admin/moderator in page form,
     *  by searching the book name similar to the input name and ISBN.
     * @param name
     * @param isbn
     * @param page
     * @return  Page<Book>
     */
    Page<Book> getBookByNameLikeAdminAndIsbn(String name,String isbn, int page);

    /**
     * returns list of authors for a particular book, by searching the book name.
     * @param name
     * @return  List<Author>
     */
    List<Author> getAuthorByBook(String name);
    
    /**
     * returns list of authors for a particular book, in page format, by searching the book name.
     * @param name
     * @param pageno
     * @param size
     * @return Page<Author>
     */
    Page<Author> getAuthorByBook(String name, int pageno, int size);

    /**
     * returns a book by searching for its name.
     * @param name
     * @return Book
     */
    Book getBookByName(String name);

    /**
     * returns a list of book and its reviews, by searching the book name.
     * @param name
     * @return List<ReviewBook>
     */
    List<ReviewBook> getBookReviewByBook(String name);
    
    /**
     * returns a list of book and its reviews,in page format, by searching the book name.
     * @param name
     * @param pageno
     * @param size
     * @return Page<ReviewBook>
     */
    Page<ReviewBook> getBookReviewByBook(String name, int pageno, int size);

    /**
     * returns a list of users who have reviewed the book, searched by the book name.
     * @param name
     * @return List<User>
     */
    List<User> getUserByBookReview(String name);
    
    /**
     * returns a list of users who have reviewed the book, in a page format, 
     * searched by the book name.
     * @param name
     * @param pageno
     * @param size
     * @return Page<User>
     */
    Page<User> getUserByBookReview(String name, int pageno, int size);

    /**
     * returns whether the the book exists or not by searching its isbn
     * @param isbn
     * @return boolean
     */
    boolean doesBookExist(String isbn);

    /**
     * A method to persist the book in the database. This method throws an exception,
     * if the book is already present. This method can be accessed only by the admin or the moderator.
     * @param bookDto
     * @throws BookExistException
     */
    void addBook(BookDto bookDto) throws BookExistException;

    /**
     * A method to delete the book from the database. This method throws and exception, 
     * if the book requested to be deleted, doesn't exist. This method can be accessed only by the admin or the moderator.
     * @param id
     * @throws BookDoesNotExistException
     */
    void removeBook(Long id) throws BookDoesNotExistException;

    /**
     * This method is called to update an existing book in the database. This method throws and exception, 
     * if the book requested to be updated, doesn't exist. This method can be accessed only by the admin or the moderator.
     * @param bookId
     * @param bookDto
     * @throws BookDoesNotExistException
     */
    void updateBook(Long bookId, BookDto bookDto) throws BookDoesNotExistException;

    /**
     * returns the list of verified books in the page format.
     * @param verified
     * @param pageno
     * @param size
     * @return Page<Book>
     */
   Page<Book> getVerifiedBook(boolean verified, int pageno, int size);
   
   /**
    * returns the list of verified books
    * @param verified
    * @return
    */
   List<Book> getVerifiedBook(boolean verified);


    /**
     * returns the list of reviews for the authors who have authored the book, searched by the book name.
     * @param name
     * @return List<ReviewAuthor>
     */
   List<ReviewAuthor> getAuthorReviewByBook(String name);
   
    /**
     * returns the list of reviews for the authors who have authored the book, in the page format, searched by the book name.
     * @param name
     * @param pageno
     * @param size
     * @return Page<ReviewAuthor>
     */
    Page<ReviewAuthor> getAuthorReviewByBook(String name, int pageno, int size);

    /**
     * This method is called when the admin or moderator wants to verify a book,searched by its book id.
     * @param id
     * @param choiceDto
     */
    void verifyBook(Long id, ChoiceDto choiceDto);
    
    /**
     * This method is called to add a like to a book. The user is marked by the user name and book is searched by its id.
     * This method throws an exception if the particular user has already liked the book.
     * @param userName
     * @param bookId
     * @throws AlreadyReviewedException
     */
    
    void addBookLikeByUser(String userName, Long bookId)throws AlreadyReviewedException;
    
    /**
     * This method is called to dislike a book. The user is marked by the user name and book is searched by its id.
     * @param userName
     * @param bookId
     * @throws ReviewDoesnotExistException
     */
    
    void removeBookLikeByUser(String userName, Long bookId) throws ReviewDoesnotExistException;
    
    /**
     * This method returns the total number of likes obtained by a book from all the users, searched by book id.
     * This method is only called by the admin or the moderator.
     * @param bookId
     * @return int
     */
    
    int getNumberOfBookLikesByUsers(Long bookId);
    
    /**
     * This method returns the total number of reviews made on the book, searched by the book id.
     * @param name
     * @return int
     */
    
    int getTotalBookReviewByBook(String name);

    /**
     * This method is a universal search for books by its name, genre or isbn.
     * This method can only be called by the admin or moderator.
     * @param name
     * @param genre
     * @param isbn
     * @param page
     * @return Page<Book>
     */
    Page<Book> searchForBooks(String name, String genre, String isbn, int page);
    
    /**
     * This method returns the top 10 books in the decreasing order of their likes. The  minimum like should be greater than 0.
     * @return  List<Book>
     */
    List<Book> getTop10Books();
    
}