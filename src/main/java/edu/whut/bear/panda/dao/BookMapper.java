package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 08:16 Monday
 */
@Repository
public interface BookMapper {
    /**
     * Save a book record
     *
     * @param book Book
     * @return 1 - Save successfully
     */
    int saveBook(Book book);

    /**
     * Get books record by the book title
     *
     * @param title Title of book
     * @return Book list or null
     */
    List<Book> getBooksByTitle(@Param("title") String title);

    /**
     * Get all books record
     *
     * @return Book list or null
     */
    List<Book> getAllBooks();

    /**
     * Get the record of the user upload the books history
     *
     * @param userId Id of user
     * @return Book list or null
     */
    List<Book> getUserBookUploadRecord(@Param("userId") Integer userId);
}
