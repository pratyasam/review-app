/**
 * 
 */
package com.mindfire.review.web.repositories;
import com.mindfire.review.web.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * @author pratyasa
 *
 */
public interface ReviewAuthorLikeRepository extends JpaRepository<ReviewAuthorLike, Long>{
	
	/**
     * @param user
     * @return
     */
	
    List<ReviewAuthorLike> findByUser(User user);
    
    /**
     * 
     * @param reviewAuthor
     * @return
     */
    
    List<ReviewAuthorLike> findByReviewAuthor(ReviewAuthor reviewAuthor);
    
    /**
     * 
     * @param reviewAuthor
     * @param user
     * @return
     */
    
    ReviewAuthorLike findByReviewAuthorAndUser(ReviewAuthor reviewAuthor, User user);
   

}
