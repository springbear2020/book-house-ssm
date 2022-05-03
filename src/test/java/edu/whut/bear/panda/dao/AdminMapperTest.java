package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 10:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class AdminMapperTest {
    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void getAdminByUsernameAndPassword() {
        System.out.println(adminMapper.getAdminByUsernameAndPassword("admin", "admin"));
    }
}