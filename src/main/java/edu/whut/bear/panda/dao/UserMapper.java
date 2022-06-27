package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 9:00
 */
@Repository
public interface UserMapper {

    /**
     * Get user by username and password
     *
     * @param username Username
     * @param password Password
     * @return User or null
     */
    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * Get user by the username
     *
     * @param username Username
     * @return User or null
     */
    User getUserByUsername(@Param("username") String username);

    /**
     * Get the user info by the email address
     *
     * @param email Email address
     * @return User or null
     */
    User getUserByEmail(@Param("email") String email);

    /**
     * Save user
     *
     * @param user User
     * @return 1 - Save successfully
     */
    int saveUser(User user);
}
