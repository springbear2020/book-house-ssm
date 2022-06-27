package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Background;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


/**
 * @author Spring-_-Bear
 * @datetime 2022/5/8 9:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BackgroundMapperTest {
    @Autowired
    private BackgroundMapper backgroundMapper;

    @Test
    public void saveBackground() {
        Background background = new Background();
        background.setUserId(1);
        background.setUploadTime(new Date());
        background.setUrl("http:");
        background.setStatus(Background.NORMAL);
        System.out.println(backgroundMapper.saveBackground(background));
    }
}