package edu.whut.bear.panda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.dao.BookMapper;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.service.BookService;
import edu.whut.bear.panda.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 08:15 Monday
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private PropertyUtils propertyUtils;

    @Override
    public boolean saveBook(Book book) {
        return bookMapper.saveBook(book) == 1;
    }

    @Override
    public PageInfo<Book> getBookPageData(String title, Integer pageNum) {
        PageHelper.startPage(pageNum, propertyUtils.getPageSize());
        List<Book> bookList;
        // Get book though different ways by condition named title
        if (title != null && title.length() > 0) {
            bookList = bookMapper.getBooksByTitle(title);
        } else {
            bookList = bookMapper.getAllBooks();
        }
        return new PageInfo<>(bookList, propertyUtils.getBookNavigationPages());
    }
}
