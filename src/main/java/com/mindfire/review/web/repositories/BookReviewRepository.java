/**
 * 
 */
package com.mindfire.review.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.BookReview;
import com.mindfire.review.web.models.Review;

/**
 * @author pratyasa
 *
 */
public interface BookReviewRepository extends JpaRepository<BookReview,Long>{
	/**
	 * 
	 * @param book
	 * @return
	 */
	List<Review> findByBook(Book book);
	/**
	 * 
	 * @param review
	 * @return
	 */
	Book findByReview(Review review);

}
