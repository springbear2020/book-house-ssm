package edu.whut.bear.panda.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 22:09
 */
public class SpiderUtilsTest {

    @Test
    public void executePixabaySpider() {
        System.out.println(SpiderUtils.executeSpider("D:\\pixabay_spider.py", "girl 1"));
    }
}