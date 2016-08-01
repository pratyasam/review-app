/**
 * 
 */
package com.mindfire.review.web.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mindfire.review.web.models.Book;

/**
 * @author pratyasa
 *
 */
@Repository
public interface BookRepository extends JpaRepository<Book,Long>{
	@Query(name="Book.findAll")
	List<Book> findAll();
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	Book findByBookNameContainsIgnoreCase(String name);
	/**
	 * 
	 * @param genre
	 * @return
	 */
	List<Book> findByBookGenreContainsIgnoreCase(String genre);
	/**
	 * 
	 * @param isbn
	 * @return
	 */
	Book findByBookIsbn(String isbn);
	/**
	 * 
	 * @param rating
	 * @return
	 */
	List<Book> findByBookRating(int rating);
	
	

}
