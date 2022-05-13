package edu.whut.bear.panda.service;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 20:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    public void getBookPageData() {
        PageInfo<Book> bookPageData = bookService.getBookPageData("", 1);
        System.out.println(bookPageData);
    }
}