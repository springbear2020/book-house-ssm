package edu.whut.bear.panda.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 22:09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpiderUtilsTest {
    @Autowired
    private SpiderUtils spiderUtils;

    @Test
    public void executePixabaySpider() {
        System.out.println(spiderUtils.executeSpider("D:\\pixabay_spider.py", "girl 1"));
    }
}