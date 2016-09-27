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

import com.mindfire.review.web.models.User;

/**
 * @author pratyasa
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
     */
    @Query(name = "User.findAll")
    Page<User> findAll(Pageable page);

    /**
     * @param firstName
     * @return
     */
    List<User> findByFirstNameIgnoreCase(String firstName);
    /**
     * 
     * @param firstName
     * @param page
     * @return
     */
    Page<User> findByFirstNameIgnoreCase(String firstName, Pageable page);

    /**
     * @param lastName
     * @return
     */

    List<User> findByLastNameIgnoreCase(String lastName);
    /**
     * 
     * @param lastName
     * @param page
     * @return
     */
    Page<User> findByLastNameIgnoreCase(String lastName, Pageable page);

    /**
     * @param name
     * @return
     */
    User findByUserName(String name);

    /**
     * @param name
     * @param password
     * @return
     */
    User findByUserNameAndUserPassword(String name, String password);

    /**
     * @param type
     * @return
     */
    List<User> findByRoleIgnoreCase(String type);
    /**
     * @param type
     * @param page
     * @return
     */
    Page<User> findByRoleIgnoreCase(String type, Pageable page);

    /**
     * @param id
     * @return
     */
    User findOne(Long id);

}
