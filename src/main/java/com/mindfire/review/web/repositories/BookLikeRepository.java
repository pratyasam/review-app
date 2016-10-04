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
public interface BookLikeRepository extends JpaRepository<BookLike, Long> {
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	
	 List<BookLike> findByUser(User user);
	 
	 /**
	  * 
	  * @param book
	  * @return
	  */
	 
	 List<BookLike> findByBook(Book book);
	 
	 /**
	  * 
	  * @param book
	  * @param user
	  * @return
	  */
	 
	 BookLike findByBookAndUser(Book book, User user);


}
