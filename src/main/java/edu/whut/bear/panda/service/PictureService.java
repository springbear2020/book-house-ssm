package edu.whut.bear.panda.service;

import edu.whut.bear.panda.pojo.Background;
import edu.whut.bear.panda.pojo.Pixabay;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 10:13 Monday
 */
@Service
public interface PictureService {
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
     * @return Background list or null
     */
    List<Background> getAllWallpapers();

    /**
     * Delete all pixabay
     *
     * @return Affected rows of record
     */
    int deleteAllPixabay();

    /**
     * Execute the python spider to get new pixabay data
     *
     * @param params Python command parameters
     * @return true - Insert new record successfully
     */
    boolean insertPixabayThoughSpider(String params);

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
}
