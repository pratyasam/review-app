/**
 *
 */
package com.mindfire.review.web.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;

/**
 * @author pratyasa
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	@Query(name = "Book.findAll")
	Page<Book> findAll(Pageable page);

	/**
	 * 
	 */
	List<Book> findAll();

	/**
	 * @param name
	 * @return
	 */
	List<Book> findByBookNameContainsIgnoreCaseAndBookVerified(String name, boolean bool);
	/**
	 * 
	 * @param name
	 * @return
	 */
	List<Book> findByBookNameContainsIgnoreCase(String name);
	

	/**
	 *
	 * @param name
	 * @return
	 */

	Book findByBookName(String name);

	/**
	 * @param genre
	 * @return
	 */
	Page<Book> findByBookGenreContainsIgnoreCaseAndBookVerified(String genre, boolean verified, Pageable page);
	/**
	 * 
	 * @param genre
	 * @param page
	 * @return
	 */
	Page<Book> findByBookGenreContainsIgnoreCase(String genre, Pageable page);

	/**
	 * 
	 * @param genre
	 * @param verified
	 * @return
	 */
	List<Book> findByBookGenreContainsIgnoreCaseAndBookVerified(String genre, boolean verified);
	/**
	 * 
	 * @param genre
	 * @return
	 */
	List<Book> findByBookGenreContainsIgnoreCase(String genre);

	/**
	 * @param isbn
	 * @return
	 */
	Book findByBookIsbn(String isbn);

	/**
	 * @param rating
	 * @return
	 */
	Page<Book> findByBookRating(float rating, Pageable page);

	/**
	 * 
	 * @param rating
	 * @return
	 */
	List<Book> findByBookRating(float rating);

	/**
	 * @param verified
	 * @return List<@Book>
	 */
	Page<Book> findByBookVerified(boolean verified, Pageable page);

	/**
	 * 
	 * @param verified
	 * @return
	 */
	List<Book> findByBookVerified(boolean verified);

	/**
	 * @param id
	 * @return
	 */
	Book findOne(Long id);
	
	
	List<Book> findTop10ByBookLikesGreaterThanOrderByBookLikesDesc(int likes);

}
