/**
 *
 */
package com.mindfire.review.web.repositories;


import com.mindfire.review.web.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pratyasa
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(name = "Book.findAll")
    List<Book> findAll();

    /**
     * @param name
     * @return
     */
    List<Book> findByBookNameContainsIgnoreCase(String name);

    /**
     *
     * @param name
     * @return
     */

    Book findByBookName(String name);
    /**
     * @param genre
     * @return
     */
    List<Book> findByBookGenreContainsIgnoreCaseAndBookVerified(String genre, boolean verified);

    /**
     * @param isbn
     * @return
     */
    Book findByBookIsbn(String isbn);

    /**
     * @param rating
     * @return
     */
    List<Book> findByBookRating(int rating);

    /**
     * @param verified
     * @return List<@Book>
     */
    List<Book> findByBookVerified(boolean verified);

    /**
     * @param id
     * @return
     */
    Book findOne(Long id);


}
