/**
 *
 */
package com.mindfire.review.web.repositories;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author pratyasa
 */
public interface ReviewAuthorRepository extends JpaRepository<ReviewAuthor, Long> {
    /**
     * @param user
     * @return
     */
    List<ReviewAuthor> findByUser(User user);

    /**
     * @param author
     * @return
     */
    List<ReviewAuthor> findByAuthor(Author author);


    /**
     * @param id
     * @return
     */
    ReviewAuthor findOne(Long id);

    /**
     * @param user
     * @param author
     * @return
     */
    ReviewAuthor findByUserAndAuthor(User user, Author author);
}
