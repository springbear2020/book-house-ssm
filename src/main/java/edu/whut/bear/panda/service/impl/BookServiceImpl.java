package edu.whut.bear.panda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.dao.BookMapper;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.service.BookService;
import edu.whut.bear.panda.util.PandaUtils;
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
    @Autowired
    private PandaUtils pandaUtils;

    @Override
    public PageInfo<Book> getBookPageData(String title, Integer pageNum) {
        PageHelper.startPage(pageNum, pandaUtils.getPageSize());
        List<Book> bookList;
        // Get book though different ways by condition named title
        if (title != null && title.length() > 0) {
            bookList = bookMapper.getBooksByTitle(title);
        } else {
            bookList = bookMapper.getAllBooks();
        }
        return new PageInfo<>(bookList, pandaUtils.getBookNavigationPages());
    }

    @Override
    public int getTotalPages() {
        int totalCounts = bookMapper.getBooksTotalCount();
        int pages = totalCounts / pandaUtils.getPageSize();
        pages = totalCounts % pandaUtils.getPageSize() == 0 ? pages : pages + 1;
        return pages;
    }

    @Override
    public boolean saveBook(Book book) {
        return bookMapper.saveBook(book) == 1;
    }

    @Override
    public Book getBookById(Integer id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public boolean increaseBookDownloads(Integer id) {
        return bookMapper.bookDownloadsIncreaseOne(id) == 1;
    }
}
