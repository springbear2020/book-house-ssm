package edu.whut.bear.panda.service;

import edu.whut.bear.panda.pojo.Pixabay;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 20:53
 */
@Service
public interface PictureService {
    /**
     * Get first pixabay picture
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
     * @return true - Delete successfully
     */
    boolean deleteAllPixabay();
}
