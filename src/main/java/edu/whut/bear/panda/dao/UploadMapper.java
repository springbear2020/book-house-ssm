package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Upload;
import org.springframework.stereotype.Repository;

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
}
