package edu.whut.bear.panda.service.impl;

import edu.whut.bear.panda.dao.AdminMapper;
import edu.whut.bear.panda.dao.UserMapper;
import edu.whut.bear.panda.pojo.Admin;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 9:06
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return userMapper.getUserByUsernameAndPassword(username, password);
    }

    @Override
    public boolean saveUser(User user) {
        return userMapper.saveUser(user) == 1;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public Admin getAdminByUsernameAndPassword(String username, String password) {
        return adminMapper.getAdminByUsernameAndPassword(username, password);
    }
}
