package edu.whut.bear.panda.service;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Book;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 20:08
 */
@Service
public interface BookService {
    /**
     * Get a book page data randomly
     *
     * @param title           The title of the book which user want to search
     * @param pageNum         The page num
     * @return Book page info
     */
    PageInfo<Book> getBookPageData(String title, Integer pageNum);

    /**
     * Get the number of book record total pages
     *
     * @return Number of book data total pages
     */
    int getTotalPages();

    /**
     * Save a book record
     *
     * @param book Book
     * @return true - Save successfully
     */
    boolean saveBook(Book book);

    /**
     * Get book info by id
     *
     * @param id Id of book record
     * @return Book or null
     */
    Book getBookById(Integer id);
}
