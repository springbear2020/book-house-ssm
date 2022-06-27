package edu.whut.bear.panda.service;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.pojo.Login;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 9:14
 */
@Service
public interface RecordService {
    /**
     * Save the log of the user
     *
     * @param login Login log
     * @return true - Save successfully
     */
    boolean saveLoginLog(Login login);

    /**
     * Get the user's login page data
     *
     * @param userId Id of user
     * @return Login page data or null
     */
    PageInfo<Login> getLoginPageData(Integer userId, Integer pageNum);

    /**
     * Get the record of the user upload the books history
     *
     * @param userId Id of user
     * @param pageNum Number of the pages
     * @return User book upload page info
     */
    PageInfo<Book> getUserBookRecord(Integer userId, Integer pageNum);
}
