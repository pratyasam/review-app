/**
 *
 */
package com.mindfire.review.web.repositories;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author pratyasa
 */
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    /**
     * @param author
     * @return
     */

    List<BookAuthor> findByAuthor(Author author);

    /**
     * @param book
     * @return
     */
    List<BookAuthor> findByBook(Book book);

}
