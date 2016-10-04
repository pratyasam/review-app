/**
 * 
 */
package com.mindfire.review.web.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author pratyasa
 *
 */
@Entity
@Table(name = "book_like")
@NamedQuery(name = "BookLike.findAll", query = "SELECT b FROM BookLike b")
public class BookLike implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_like_id", unique = true, nullable = false)
    private Long bookLikeId;
	
	//bi-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
  //bi-directional many-to-one association to Book
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

	public Long getBookLikeId() {
		return this.bookLikeId;
	}

	public void setBookLikeId(Long bookLikeId) {
		this.bookLikeId = bookLikeId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
