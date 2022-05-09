package edu.whut.bear.panda.service;

import edu.whut.bear.panda.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 22:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmailVerifyCode() {
        System.out.println(emailService.sendEmailVerifyCode("springbear2020@163.com"));
    }
}