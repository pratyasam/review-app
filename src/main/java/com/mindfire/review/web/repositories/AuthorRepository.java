/**
 *
 */
package com.mindfire.review.web.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mindfire.review.web.models.Author;


/**
 * @author pratyasa
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     *
     */
    @Query(name = "Author.findAll")
    Page<Author> findAll(Pageable page);
    /**
     * 
     */
    List<Author> findAll();

    /**
     * @param name
     * @return
     */
    Author findByAuthorNameIgnoreCase(String name);

    /**
     * @param genre
     * @return
     */
    Page<Author> findByAuthorGenreContainsIgnoreCase(String genre, Pageable page);
    /**
     * 
     * @param genre
     * @return
     */
    List<Author> findByAuthorGenreContainsIgnoreCase(String genre);

    /**
     * @param rating
     * @return
     */
    Page<Author> findByAuthorRating(float rating,Pageable page);
    /**
     * 
     * @param rating
     * @return
     */
   List<Author> findByAuthorRating(float rating);

    /**
     * @param name
     * @return
     */
    List<Author> findByAuthorNameContainsIgnoreCase(String name);

    /**
     * @param id
     * @return
     */
    Author findOne(Long id);
}












