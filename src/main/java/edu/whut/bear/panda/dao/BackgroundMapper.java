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
     * Save background record to the "t_background" table
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
    List<Background> getUserBackgroundsByUserId(@Param("userId") Integer userId);

    /**
     * Get a background record by id
     *
     * @param id Id of background
     * @return Background or null
     */
    Background getBackgroundById(@Param("id") Integer id);

    /**
     * Update the status of the background record though id
     *
     * @param id     Id of background
     * @param status The new status of the record you want to set
     * @return 1 - Update successfully
     */
    int updateBackgroundStatusById(@Param("id") Integer id, @Param("status") Integer status);
}
