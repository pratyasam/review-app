/**
 * 
 */
package com.mindfire.review.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindfire.review.web.models.Author;
import com.mindfire.review.web.models.Book;
import com.mindfire.review.web.models.BookAuthor;

/**
 * @author pratyasa
 *
 */
public interface BookAuthorRepository extends JpaRepository<BookAuthor,Long>{
	/**
	 * 
	 * 
	 * @param author
	 * @return
	 */
	
	List<Book> findByAuthor(Author author);
	
	/**
	 * 
	 * @param book
	 * @return
	 */
	List<Author> findByBook(Book book);

}
