package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Pixabay;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 11:00 Monday
 */
@Repository
public interface PixabayMapper {
    /**
     * Delete all pixabay record
     *
     * @return Number of rows affected
     */
    int deleteAllPixabay();

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
     * @return 1 - Delete successfully
     */
    int deletePixabayById(@Param("id") Integer id);
}
