package com.mindfire.review.services;

import com.mindfire.review.exceptions.AuthorExistenceException;
import com.mindfire.review.web.dto.AuthorDto;
import com.mindfire.review.web.dto.DeleteDto;
import com.mindfire.review.web.models.*;
import com.mindfire.review.web.repositories.AuthorRepository;
import com.mindfire.review.web.repositories.BookAuthorRepository;
import com.mindfire.review.web.repositories.ReviewAuthorRepository;
import com.mindfire.review.web.repositories.ReviewBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pratyasa
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ReviewAuthorRepository reviewAuthorRepository;
    @Autowired
    private BookAuthorRepository bookAuthorRepository;
    @Autowired
    private ReviewBookRepository reviewBookRepository;
    @Autowired
    private BookService bookService;

    /**
     * get the list of all the authors
     * @return List<Author>
     */
    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    /**
     * to get the author or authors with the same name
     * @param name
     * @return List<Author>
     */


    @Override
    public List<Author> getAuthorByNameLike(String name) {return authorRepository.findByAuthorNameContainsIgnoreCase(name);}

    /**
     * get the author by name
     * @param name
     * @return
     */


    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.findByAuthorNameIgnoreCase(name);
    }

    /**
     * to get the list of authors by their rating
     * @param rating
     * @return
     */


    @Override
    public List<Author> getAuthorByRating(int rating) {
        return authorRepository.findByAuthorRating(rating);
    }

    /**
     * to get the list of authors according to the genre
     * @param genre
     * @return
     */


    @Override
    public List<Author> getAuthorByGenre(String genre) {return authorRepository.findByAuthorGenreContainsIgnoreCase(genre);}

    /**
     * to get all the reviews on the author
     * @param name
     * @return
     */


    @Override
    public List<ReviewAuthor> getAuthorReviewByAuthorName(String name) {return reviewAuthorRepository.findByAuthor(getAuthorByName(name));}

    /**
     * to get the author by the Id
     * @param authorId
     * @return
     */

    @Override
    public Author getAuthorById(Long authorId){ return authorRepository.findOne(authorId);}

    /**
     * get the book reviews on all the books authored by the author
     * @param name
     * @return
     */
    @Override
    public List<ReviewBook> getBookReviewByAuthorName(String name) {
        List<Book> bookList = getBookByAuthor(name);
        List<ReviewBook> reviewBookList = new ArrayList<>();
        List<ReviewBook> reviewBookList1;
        for (Book b : bookList) {
            reviewBookList1 = reviewBookRepository.findByBook(b);
            reviewBookList.addAll(reviewBookList1);
        }
        return reviewBookList;
    }

    /**
     * to get the list of books authored by the author
     * @param name
     * @return
     */

    @Override
    public List<Book> getBookByAuthor(String name) {
        return bookAuthorRepository.findByAuthor(getAuthorByName(name));
    }

    /**
     * to get all the users who have commented on the books by the author
     * @param name
     * @return
     */


    @Override
    public List<User> getUserByAuthor(String name) {
        List<User> users = new ArrayList<>();
        List<Book> books = getBookByAuthor(name);
        for (Book b : books) {
            users.addAll(bookService.getUserByBookReview(b.getBookName()));
        }
        return users;
    }

    /**
     * method to add authors
     * @param authorDto
     * @throws AuthorExistenceException
     */

    public void addAuthor(AuthorDto authorDto) throws AuthorExistenceException{
        if(authorDto == null){
            throw new RuntimeException("");
        }
        if(authorRepository.findByAuthorNameIgnoreCase(authorDto.getAuthorName()) != null){
            throw new AuthorExistenceException("Author already exists.");
        }
        Author author = new Author();
        author.setAuthorDescription(authorDto.getAuthorDescription());
        author.setAuthorGenre(authorDto.getAuthorGenre());
        author.setAuthorName(authorDto.getAuthorName());
        author.setAuthorRating(authorDto.getAuthorRating());
        authorRepository.save(author);
        System.out.println("author added");
    }

    /**
     * method to update author
     * @param authorDto
     * @param authorId
     * @throws AuthorExistenceException
     */
    public void updateAuthor(AuthorDto authorDto, Long authorId) throws AuthorExistenceException{
        Author author = authorRepository.findOne(authorId);
        if(authorDto == null){
            throw new RuntimeException("");
        }
        if (author == null){
            throw new AuthorExistenceException("Author does not esist");
        }
        author.setAuthorRating(authorDto.getAuthorRating());
        author.setAuthorName(authorDto.getAuthorName());
        author.setAuthorGenre(authorDto.getAuthorGenre());
        author.setAuthorDescription(authorDto.getAuthorDescription());
        authorRepository.save(author);
        System.out.println("author updated");
    }

    /**
     * method to remove authors
     * @param authorId
     * @throws AuthorExistenceException
     */
    public void removeAuthor(Long authorId, DeleteDto deleteDto) throws AuthorExistenceException{
        Author author = authorRepository.findOne(authorId);
        if(author == null){
            throw new AuthorExistenceException("Author does not exist");
        }
        if(deleteDto.getChoice().equalsIgnoreCase("Y")) {
            authorRepository.delete(author);
            System.out.println("Author deleted");
        }
    }

}
