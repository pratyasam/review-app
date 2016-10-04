/**
 * 
 */
package com.mindfire.review.web.repositories;

/**
 * @author pratyasa
 *
 */
import com.mindfire.review.web.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewBookLikeRepository extends JpaRepository<ReviewBookLike, Long> {
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	
	 List<ReviewBookLike> findByUser(User user);
	 
	 /**
	  * 
	  * @param reviewBook
	  * @return
	  */
	 
	 List<ReviewBookLike> findByReviewBook(ReviewBook reviewBook);
	 
	 /**
	  * 
	  * @param reviewBook
	  * @param user
	  * @return
	  */
	 
	 ReviewBookLike findByReviewBookAndUser(ReviewBook reviewBook, User user);

}
