/**
 * 
 */
package com.mindfire.review.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mindfire.review.web.models.User;

/**
 * @author pratyasa
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * 
	 */
	@Query(name = "User.findAll")
	List<User> findAll();

	/**
	 * 
	 * @param firstName
	 * @return
	 */
	List<User> findByfirstNameIgnoreCase(String firstName);

	/**
	 * 
	 * @param lastName
	 * @return
	 */

	List<User> findBylastNameIgnoreCase(String lastName);

	/**
	 * 
	 * @param name
	 * @return
	 */
	List<User> findByUserNameIgnoreCaseContaining(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	User findByUserName(String name);
	/**
	 * 
	 * @param type
	 * @return
	 */
	List<User> findByUserTypeIgnoreCase(String type);

}
