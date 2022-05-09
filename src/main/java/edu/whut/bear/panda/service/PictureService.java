package edu.whut.bear.panda.service;

import edu.whut.bear.panda.pojo.Background;
import edu.whut.bear.panda.pojo.Pixabay;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 20:53
 */
@Service
public interface PictureService {
    /**
     * Get the first pixabay record in the "t_pixabay" table
     *
     * @return Pixabay or null
     */
    Pixabay getFirstPixabay();

    /**
     * Delete a pixabay picture by id
     *
     * @param id Id of pixabay
     * @return true - Delete successfully
     */
    boolean deletePixabayById(Integer id);

    /**
     * Delete all pixabay
     *
     * @return Affected rows of record
     */
    int deleteAllPixabay();

    /**
     * Save the background
     *
     * @param background Background
     * @return true - Save successfully
     */
    boolean saveBackground(Background background);

    /**
     * Get all background record of the specified user
     *
     * @param userId Id of user
     * @return Background list or null
     */
    List<Background> getUserAllBackgrounds(Integer userId);

    /**
     * Execute the python spider to get new pixabay data
     *
     * @param params Python command parameters
     * @return true - Insert new record successfully
     */
    boolean insertPixabayThoughSpider(String params);

    /**
     * Get a background record by id
     *
     * @param id Id of background
     * @return Background or null
     */
    Background getBackgroundById(Integer id);

    /**
     * Update the background record status
     *
     * @param id     Id of background
     * @param status New status of the background status
     * @return true - Update successfully
     */
    boolean updateBackgroundStatus(Integer id, Integer status);
}
