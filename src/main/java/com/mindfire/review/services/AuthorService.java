package com.mindfire.review.services;

import com.mindfire.review.exceptions.AuthorExistenceException;
import com.mindfire.review.web.dto.AuthorDto;
import com.mindfire.review.web.dto.ChoiceDto;
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

    List<Author> getAuthorByRating(float rating);

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
    List<ReviewBook> getBookReviewByAuthorName(String name);

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
    public void removeAuthor(Long authorId, ChoiceDto choiceDto) throws AuthorExistenceException;

}