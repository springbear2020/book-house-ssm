package edu.whut.bear.panda.service;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Book;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 08:15 Monday
 */
@Service
public interface BookService {
    /**
     * Save a book record
     *
     * @param book Book
     * @return true - Save successfully
     */
    boolean saveBook(Book book);

    /**
     * Get a book page data randomly
     *
     * @param title   The title of the book which user want to search
     * @param pageNum The page num
     * @return Book page info
     */
    PageInfo<Book> getBookPageData(String title, Integer pageNum);
}
