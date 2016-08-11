/**
 *
 */
package com.mindfire.review.web.repositories;

import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.ReviewBook;
import com.mindfire.review.web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author pratyasa
 */
public interface ReviewBookRepository extends JpaRepository<ReviewBook, Long> {
    /**
     * @param user
     * @return
     */
    List<ReviewBook> findByUser(User user);

    /**
     * @param book
     * @return
     */
    List<ReviewBook> findByBook(Book book);

    /**
     * @param id
     * @return
     */
    ReviewBook findByReviewBookId(Long id);

    /**
     * @param user,book
     * @return
     */

    ReviewBook findByUserAndBook(User user, Book book);

}
