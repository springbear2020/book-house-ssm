package edu.whut.bear.panda.util;

import edu.whut.bear.panda.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 22:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class ResourceTest {

    @Test
    public void resourceTest() throws IOException {
        Resource resource = new ClassPathResource("pixabay_spider.py");
        String fileRealPath = resource.getURL().toString();
        fileRealPath = fileRealPath.substring(fileRealPath.indexOf('/') + 1);
        SpiderUtils.executeSpider(fileRealPath, "girls 1");
    }
}
