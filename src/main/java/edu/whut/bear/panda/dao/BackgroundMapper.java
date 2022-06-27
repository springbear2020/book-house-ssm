package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Background;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 10:10 Monday
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
     * @return Background list or null
     */
    List<Background> getAllBackground();
}
