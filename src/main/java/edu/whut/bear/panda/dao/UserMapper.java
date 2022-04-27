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
}
