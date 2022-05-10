package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 20:07
 */
@Repository
public interface BookMapper {
    /**
     * Get all books record
     *
     * @return Book list or null
     */
    List<Book> getAllBooks();

    /**
     * Get books record by the book title
     *
     * @param title Title of book
     * @return Book list or null
     */
    List<Book> getBooksByTitle(@Param("title") String title);

    /**
     * Get the total count of the book record
     *
     * @return Book total count
     */
    int getBooksTotalCount();

    /**
     * Save a book record
     *
     * @param book Book
     * @return 1 - Save successfully
     */
    int saveBook(Book book);

    /**
     * Get book record by id
     *
     * @param id Id of book record
     * @return Book or null
     */
    Book getBookById(@Param("id") Integer id);

    /**
     * Increase the book's downloads by one
     *
     * @param id Id of book
     * @return 1 - Increase successfully
     */
    int bookDownloadsIncreaseOne(@Param("id") Integer id);
}
