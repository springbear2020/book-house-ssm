package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.config.SpringConfig;
import edu.whut.bear.panda.pojo.Upload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 9:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class UploadMapperTest {
    @Autowired
    private UploadMapper uploadMapper;

    @Test
    public void saveUpload() {
        System.out.println(uploadMapper.saveUpload(new Upload(null, 2, 0, "bear", 0, 0, new Date(), "http://localhost:8080/", "user/book/1.pdf", "panda-books")));
    }
}