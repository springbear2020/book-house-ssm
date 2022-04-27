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
     * Verify the correctness of the username and password
     *
     * @param username username
     * @param password password
     * @return User or null
     */
    User verifyUsernameAndPassword(String username, String password);
}
