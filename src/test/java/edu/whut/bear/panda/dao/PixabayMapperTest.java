package edu.whut.bear.panda.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 20:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PixabayMapperTest {
    @Autowired
    private PixabayMapper pixabayMapper;

    @Test
    public void deletePixabayById() {
        System.out.println(pixabayMapper.deletePixabayById(100));
    }

    @Test
    public void getFirstPixabay() {
        System.out.println(pixabayMapper.getFirstPixabay());
    }
}