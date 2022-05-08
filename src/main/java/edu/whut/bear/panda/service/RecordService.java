package edu.whut.bear.panda.service;

import edu.whut.bear.panda.pojo.Background;
import edu.whut.bear.panda.pojo.Upload;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 9:14
 */
@Service
public interface RecordService {
    /**
     * Save upload record
     *
     * @param upload Upload
     * @return true - Save successfully
     */
    boolean saveUpload(Upload upload);

    /**
     * Get the first unprocessed book upload record
     *
     * @param status Status of book upload record
     * @param start  Start position
     * @param offset Offset
     * @return Upload or null
     */
    List<Upload> getBookUploadByStatus(Integer status, Integer start, Integer offset);

    /**
     * Update the status of the upload record
     *
     * @param id     Id of the upload record
     * @param status The new status of the upload record
     * @return true - Update successfully
     */
    boolean updateUploadRecordStatus(Integer id, Integer status);

    /**
     * Get upload record by id
     *
     * @param id Id of upload record
     * @return Upload or null
     */
    Upload getUploadById(Integer id);

    /**
     * Save the background
     *
     * @param background Background
     * @return true - Save successfully
     */
    boolean saveBackground(Background background);
}
