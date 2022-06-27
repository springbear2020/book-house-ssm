package edu.whut.bear.panda.service;

import edu.whut.bear.panda.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 9:06
 */
@Service
public interface UserService {
    /**
     * Get user by username and password
     *
     * @param username username
     * @param password password
     * @return User or null
     */
    User getUserByUsernameAndPassword(String username, String password);

    /**
     * Get user by username
     *
     * @param username Username
     * @return User or null
     */
    User getUserByUsername(String username);

    /**
     * Get user by email
     *
     * @param email Email address
     * @return User or null
     */
    User getUserByEmail(String email);

    /**
     * Save user info entered by user
     *
     * @param user User
     * @return true - Save successfully
     */
    boolean saveUser(User user);
}
