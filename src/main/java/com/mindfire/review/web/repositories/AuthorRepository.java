/**
 * 
 */
package com.mindfire.review.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfire.review.web.models.Author;



/**
 * @author pratyasa
 *
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

	List<Author> findByAuthorNameIgnoreCase(String name);
	List<Author> findByGenre(String genre);
}
