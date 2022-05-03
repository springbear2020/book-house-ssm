package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 10:43
 */
@Repository
public interface AdminMapper {
    /**
     * Get admin info by username and password
     *
     * @param username Username
     * @param password Password
     * @return Admin or null
     */
    Admin getAdminByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
