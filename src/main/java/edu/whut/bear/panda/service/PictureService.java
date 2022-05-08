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
     * Get pixabay picture by start position and offset
     *
     * @param start  Start position
     * @param offset Offset
     * @return Pixabay list or null
     */
    List<Pixabay> getPixabayByPositionAndOffset(Integer start, Integer offset);

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

    /**
     * Get the background picture record of the user by user id
     *
     * @param userId Id of user
     * @return Background list or null
     */
    List<Background> getUserAllBackground(Integer userId);
}
