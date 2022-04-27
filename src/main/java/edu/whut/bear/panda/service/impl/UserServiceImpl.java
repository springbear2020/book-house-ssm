package edu.whut.bear.panda.service.impl;

import edu.whut.bear.panda.dao.UserMapper;
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

    @Override
    public User verifyUsernameAndPassword(String username, String password) {
        return userMapper.getUserByUsernameAndPassword(username, password);
    }
}
