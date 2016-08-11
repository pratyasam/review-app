package com.mindfire.review.services;

import com.mindfire.review.exceptions.BookDoesNotExistException;
import com.mindfire.review.exceptions.BookExistException;
import com.mindfire.review.web.dto.BookDto;
import com.mindfire.review.web.models.*;
import com.mindfire.review.web.repositories.BookAuthorRepository;
import com.mindfire.review.web.repositories.BookRepository;
import com.mindfire.review.web.repositories.ReviewAuthorRepository;
import com.mindfire.review.web.repositories.ReviewBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService implements BookServiceInterface {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookAuthorRepository bookAuthorRepository;
    @Autowired
    private ReviewBookRepository reviewBookRepository;
    @Autowired
    private ReviewAuthorRepository reviewAuthorRepository;


    public BookService() {
        super();
    }

    /**
     * @param id
     * @return
     */
    public Book getBookById(Long id) {
        return bookRepository.findOne(id);
    }

    /**
     * @param page
     * @param size
     * @return
     */
    public Page<Book> getBooks(int page, int size) {
        return bookRepository.findAll(new PageRequest(page, size));
    }

    /**
     * @param genre
     * @return
     */

    public List<Book> getBookByGenre(String genre) {
        return bookRepository.findByBookGenreContainsIgnoreCase(genre);
    }

    /**
     * @param rating
     * @return
     */

    public List<Book> getBookByRating(int rating) {
        return bookRepository.findByBookRating(rating);
    }

    /**
     * @param isbn
     * @return
     */
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByBookIsbn(isbn);
    }

    /**
     * @param name
     * @return
     */

    public Book getBookByName(String name) {
        return bookRepository.findByBookNameContainsIgnoreCase(name);
    }

    /**
     * @param name
     * @return
     */

    public List<Author> getAuthorByBook(String name) {
        Book book = getBookByName(name);
        return bookAuthorRepository.findByBook(book);
    }

    /**
     * @param name
     * @return
     */

    public List<ReviewBook> getBookReviewByBook(String name) {
        return reviewBookRepository.findByBook(getBookByName(name));
    }

    /**
     * @param name
     * @return
     */
    public List<ReviewAuthor> getAuthorReviewByBook(String name) {
        List<Author> authorList = getAuthorByBook(name);
        List<ReviewAuthor> reviewAuthorList = new ArrayList<>();
        List<ReviewAuthor> reviewAuthorList1;
        for (Author a : authorList) {
            reviewAuthorList1 = reviewAuthorRepository.findByAuthor(a);
            reviewAuthorList.addAll(reviewAuthorList1);
        }
        return reviewAuthorList;
    }

    /**
     * @param verified
     * @return
     */
    public List<Book> getVerifiedBook(boolean verified) {
        return bookRepository.findByBookVerified(verified);
    }

    /**
     * @param name
     * @return
     */

    public List<User> getUserByBookReview(String name) {
        List<User> users = new ArrayList<>();
        List<ReviewBook> reviewBooks;
        reviewBooks = getBookReviewByBook(name);
        for (ReviewBook r : reviewBooks) {
            users.add(r.getUser());
        }
        return users;
    }

    /**
     * @param isbn
     * @return
     */

    public boolean doesBookExist(String isbn) {
        Book book = getBookByIsbn(isbn);
        if (book != null)
            return true;
        else
            return false;
    }

    /**
     * @param bookDto
     * @throws BookExistException
     */

    public void addBook(BookDto bookDto) throws BookExistException {
        if (bookDto == null)
            throw new RuntimeException("");
        if (doesBookExist(bookDto.getBookIsbn())) {
            throw new BookExistException("Book Already Exists");
        }
        Book book = new Book();
        book.setBookCost(bookDto.getBookCost());
        book.setBookDescription(bookDto.getBookDescription());
        book.setBookGenre(bookDto.getBookGenre());
        book.setBookIsbn(bookDto.getBookIsbn());
        book.setBookName(bookDto.getBookName());
        book.setBookLink(bookDto.getBookLink());
        book.setBookReview(bookDto.getBookReview());

        book = bookRepository.save(book);
        System.out.println("Book saved");
        if (book == null) {
            throw new RuntimeException("");
        }

    }

    /**
     * @param id
     * @throws BookDoesNotExistException
     */

    public void removeBook(Long id) throws BookDoesNotExistException {
        Book book = getBookById(id);
        if (book == null) {
            throw new RuntimeException("");
        }
        if (!doesBookExist(book.getBookIsbn())) {
            throw new BookDoesNotExistException("Book does not exist");
        }
        bookRepository.delete(book);

    }

    /**
     * @param bookId, bookDto
     * @param bookDto
     * @throws BookDoesNotExistException
     */

    public void updateBook(Long bookId, BookDto bookDto) throws BookDoesNotExistException {
        Book bk = getBookById(bookId);
        if (bookDto == null) {
            throw new RuntimeException("");
        }
        if (bk == null) {
            throw new BookDoesNotExistException("Book does not exist");
        }

        bookRepository.saveAndFlush(bk);
    }

    /**
     * @param id
     */
    public void verifyBook(Long id) {
        Book book = getBookById(id);
        book.setBookVerified(true);
    }


}