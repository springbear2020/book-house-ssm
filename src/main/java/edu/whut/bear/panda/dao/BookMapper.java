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
     * Get all books
     *
     * @return Book list or null
     */
    List<Book> getAllBooks();

    /**
     * Get books by the book title
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
    int getBookTotalCounts();
}
