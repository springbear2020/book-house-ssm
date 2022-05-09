package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.config.SpringConfig;
import edu.whut.bear.panda.pojo.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 20:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class BookMapperTest {
    @Autowired
    private BookMapper bookMapper;

    @Test
    public void getAllBooks() {
        List<Book> allBooks = bookMapper.getAllBooks();
        allBooks.forEach(System.out::println);
    }

    @Test
    public void getBookTotalCounts() {
        System.out.println(bookMapper.getBooksTotalCount());
    }

    @Test
    public void getBookById() {
        Book book = bookMapper.getBookById(6);
        String bookPath = book.getBookPath();
        System.out.println(bookPath);
    }
}