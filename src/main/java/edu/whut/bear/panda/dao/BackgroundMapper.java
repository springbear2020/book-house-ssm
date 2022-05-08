package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Background;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/8 9:52
 */
@Repository
public interface BackgroundMapper {
    /**
     * Save background record to the database
     *
     * @param background Background
     * @return 1 - Save successfully
     */
    int saveBackground(Background background);

    /**
     * Get the all background picture record of the user
     *
     * @param userId Id of user
     * @return Background list or null
     */
    List<Background> getUserBackground(@Param("userId") Integer userId);
}
