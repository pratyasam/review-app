/**
 * 
 */
package com.mindfire.review.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfire.review.web.models.Review;
import com.mindfire.review.web.models.ReviewAuthor;
import com.mindfire.review.web.models.User;

/**
 * @author pratyasa
 *
 */
public interface ReviewAuthorRepository extends JpaRepository<ReviewAuthor,Long>{
	List<Review> findByUser(User user);
}
