/**
 * 
 */
package com.mindfire.review.web.repositories;

/**
 * @author pratyasa
 *
 */
import com.mindfire.review.web.models.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorLikeRepository extends JpaRepository<AuthorLike, Long>{
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	
	List<AuthorLike> findByUser(User user);
	
	/**
	 * 
	 * @param author
	 * @return
	 */
	
	List<AuthorLike> findByAuthor(Author author);
	
	/**
	 * 
	 * @param author
	 * @param user
	 * @return
	 */
	
	AuthorLike findByAuthorAndUser(Author author, User user);


}
