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
     * @param pageSize        Data size per page
     * @param navigationPages Navigation pages
     * @return Book page info
     */
    PageInfo<Book> getBookPageData(String title, Integer pageNum, Integer pageSize, Integer navigationPages);

    /**
     * Get the number of book record total pages
     *
     * @param pageSize Page size
     * @return Number of book data total pages
     */
    int getTotalPages(int pageSize);

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
