package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 9:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUserByUsernameAndPassword() {
        System.out.println(userMapper.getUserByUsernameAndPassword("admin", "admin"));
    }
}