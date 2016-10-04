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
@Table(name = "author_like")
@NamedQuery(name = "AuthorLike.findAll", query = "SELECT r FROM AuthorLike r")
public class AuthorLike implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_like_id", unique = true, nullable = false)
    private Long authorLikeId;
	
	//bi-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
  //bi-directional many-to-one association to Author
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

	public Long getAuthorLikeId() {
		return this.authorLikeId;
	}

	public void setAuthorLikeId(Long authorLikeId) {
		this.authorLikeId = authorLikeId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Author getAuthor() {
		return this.author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

}
