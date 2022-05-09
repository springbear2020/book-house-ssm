package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Pixabay;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 20:49
 */
@Repository
public interface PixabayMapper {
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

    /**
     * Delete all pixabay record
     *
     * @return Number of rows affected
     */
    int deleteAllPixabay();
}
