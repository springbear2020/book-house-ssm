package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.config.SpringConfig;
import edu.whut.bear.panda.pojo.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/9 22:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class RecordMapperTest {
    @Autowired
    private RecordMapper recordMapper;
    @Test
    public void saveRecord() {
        Record record = new Record();
        record.setUserId(1);
        record.setBookId(1);
        record.setType(0);
        record.setStatus(0);
        record.setTime(new Date());
        record.setTitle("test");
        record.setAuthor("test");
        System.out.println(recordMapper.saveRecord(record));
    }
}