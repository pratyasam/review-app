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

import com.mindfire.review.web.models.Book;

/**
 * @author pratyasa
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	@Override
	@Query(name = "Book.findAll")
	Page<Book> findAll(Pageable page);

	/**
	 * 
	 */
	@Override
	List<Book> findAll();

	/**
	 * @param name
	 * @return
	 */
	List<Book> findByBookNameContainingAndBookVerifiedOrBookIsbnContaining(String name,String isbn, boolean bool);
	
	/**
	 * 
	 * @param name
	 * @param bool
	 * @param page
	 * @return
	 */
	Page<Book> findByBookNameContainingAndBookVerifiedOrBookIsbnContaining(String name, boolean bool,String isbn,Pageable page);
	/**
	 * 
	 * @param name
	 * @return
	 */
	Page<Book> findByBookNameContainingOrBookIsbnContaining(String name,String isbn, Pageable page);
	

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
	Page<Book> findByBookGenreContainingAndBookVerified(String genre, boolean verified, Pageable page);
	/**
	 * 
	 * @param genre
	 * @param page
	 * @return
	 */
	Page<Book> findByBookGenreContaining(String genre, Pageable page);

	/**
	 * 
	 * @param genre
	 * @param verified
	 * @return
	 */
	List<Book> findByBookGenreContainingAndBookVerified(String genre, boolean verified);
	/**
	 * 
	 * @param genre
	 * @return
	 */
	List<Book> findByBookGenreContaining(String genre);
	
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
	@Override
	Book findOne(Long id);
	
	
	List<Book> findTop10ByBookLikesGreaterThanOrderByBookLikesDesc(int likes);

	Page<Book> findByBookNameContainingOrBookGenreContainingOrBookIsbnContaining(String name, String genre, String isbn, Pageable page);
	
}
