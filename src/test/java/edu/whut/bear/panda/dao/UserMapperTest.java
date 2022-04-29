package edu.whut.bear.panda.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.whut.bear.panda.config.SpringConfig;
import edu.whut.bear.panda.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


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

    @Test
    public void getUserByEmail() {
        System.out.println(userMapper.getUserByEmail("admin@admin.com"));
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setUsername("spring");
        user.setPassword("spring");
        user.setEmail("spring@spring.com");
        user.setType(User.USER_TYPE_COMMON);
        user.setStatus(User.USER_STATUS_NORMAL);
        user.setRegisterDate(new Date());
        user.setPortraitPath(User.DEFAULT_PORTRAIT_PATH);
        System.out.println(userMapper.saveUser(user));
    }

    @Test
    public void getUserByUsername() {
        System.out.println(userMapper.getUserByUsername("bear"));
    }
}