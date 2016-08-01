/**
 * 
 */
package com.mindfire.review.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.AuthorReview;
import com.mindfire.review.web.models.Review;

/**
 * @author pratyasa
 *
 */
public interface AuthorReviewRepository extends JpaRepository<AuthorReview,Long>{
	List<Review> findByAuthor(Author author);

}
