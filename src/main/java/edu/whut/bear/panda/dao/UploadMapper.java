package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Upload;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 9:01
 */
@Repository
public interface UploadMapper {
    /**
     * Save upload record
     *
     * @param upload Upload
     * @return 1 - Save successfully
     */
    int saveUpload(Upload upload);

    /**
     * Get the first unprocessed book upload record
     *
     * @param type     Type of upload record(book || cover || portrait || background)
     * @param status   Status of upload record(processed || unprocessed)
     * @param position Start position
     * @param offset   Offset
     * @return Upload or null
     */
    List<Upload> getUploadRecord(@Param("type") Integer type, @Param("status") Integer status, @Param("position") Integer position, @Param("offset") Integer offset);

    /**
     * Update the upload record status by the record id
     *
     * @param id     Id of the upload record
     * @param status The new status of the record you want to set
     * @return 1 - Update status successfully
     */
    int updateUploadStatusById(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * Get upload record by id
     *
     * @param id Id of upload record
     * @return Upload or null
     */
    Upload getUploadById(@Param("id") Integer id);
}
