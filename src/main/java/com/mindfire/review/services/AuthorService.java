package com.mindfire.review.services;

import com.mindfire.review.exceptions.AuthorExistenceException;
import com.mindfire.review.web.dto.AuthorDto;
import com.mindfire.review.web.dto.DeleteDto;
import com.mindfire.review.web.models.*;

import java.util.List;

public interface AuthorService {

    /**
     * @return
     */

    List<Author> getAllAuthor();

    /**
     * @param name
     * @return
     */

    List<Author> getAuthorByNameLike(String name);

    /**
     * @param name
     * @return
     */

    Author getAuthorByName(String name);

    /**
     * @param rating
     * @return
     */

    List<Author> getAuthorByRating(int rating);

    /**
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
     * @param name
     * @return
     */
    public List<ReviewBook> getBookReviewByAuthorName(String name);

    /**
     *
     * @param authorId
     * @return
     */
    public Author getAuthorById(Long authorId);

    /**
     *
     * @param authorDto
     * @throws AuthorExistenceException
     */
    public void addAuthor(AuthorDto authorDto) throws AuthorExistenceException;

    /**
     *
     * @param authorDto
     * @param authorId
     * @throws AuthorExistenceException
     */
    public void updateAuthor(AuthorDto authorDto, Long authorId) throws AuthorExistenceException;

    /**
     *
     * @param authorId
     * @throws AuthorExistenceException
     */
    public void removeAuthor(Long authorId, DeleteDto deleteDto) throws AuthorExistenceException;

}