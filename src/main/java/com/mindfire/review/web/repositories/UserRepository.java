/**
 *
 */
package com.mindfire.review.web.repositories;

import com.mindfire.review.web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * @param firstName
     * @return
     */
    List<User> findByFirstNameIgnoreCase(String firstName);

    /**
     * @param lastName
     * @return
     */

    List<User> findByLastNameIgnoreCase(String lastName);

    /**
     * @param name
     * @return
     */
    List<User> findByUserNameIgnoreCaseContaining(String name);

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
     * @param id
     * @return
     */
    User findOne(Long id);

}
