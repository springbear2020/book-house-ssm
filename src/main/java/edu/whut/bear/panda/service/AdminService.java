package edu.whut.bear.panda.service;

import edu.whut.bear.panda.pojo.Admin;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 10:55
 */
@Service
public interface AdminService {
    /**
     * Get admin info by username and password
     *
     * @param username Username
     * @param password Password
     * @return Admin or null
     */
    Admin getAdminByUsernameAndPassword(String username, String password);
}
