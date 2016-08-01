/**
 * 
 */
package com.mindfire.review.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mindfire.review.web.models.Author;



/**
 * @author pratyasa
 *
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	/**
	 * 
	 */
	@Query(name="Author.findAll")
	List<Author> findAll();

	/**
	 * 
	 * @param name
	 * @return
	 */
	Author findByAuthorNameIgnoreCase(String name);
	
	/**
	 * 
	 * @param genre
	 * @return
	 */
	List<Author> findByAuthorGenreContainsIgnoreCase(String genre);
	
	/**
	 * 
	 * @param rating
	 * @return
	 */
	List<Author> findByAuthorRating(int rating);
	/**
	 * 
	 * @param name
	 * @return
	 */
	List<Author> findByAuthorNameContainsIgnoreCase(String name);
	
}












