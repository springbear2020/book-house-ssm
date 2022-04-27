package edu.whut.bear.panda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.dao.BookMapper;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 20:29
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public PageInfo<Book> getBookPageData(String title, Integer pageNum, Integer pageSize, Integer navigationPages) {
        PageHelper.startPage(pageNum, pageSize);
        List<Book> bookList;
        // Get book though different ways by condition named title
        if (title != null && title.length() > 0) {
            bookList = bookMapper.getBooksByTitle(title);
        } else {
            bookList = bookMapper.getAllBooks();
        }
        return new PageInfo<>(bookList, navigationPages);
    }

    @Override
    public int getTotalPages(int pageSize) {
        int totalCounts = bookMapper.getBookTotalCounts();
        int pages = totalCounts / pageSize;
        pages = totalCounts % pageSize == 0 ? pages : pages + 1;
        return pages;
    }
}
