package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Login;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/10 8:23
 */
@Repository
public interface LoginMapper {
    /**
     * Save the log of the user
     *
     * @param login Login log
     * @return 1 - Save successfully
     */
    int saveLoginLog(Login login);

    /**
     * Get the login log of the user's
     *
     * @param userId Id of user
     * @return Login log list or null
     */
    List<Login> getLoginLogByUserId(@Param("userId") Integer userId);
}
