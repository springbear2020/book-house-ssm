package edu.whut.bear.panda.service;

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
}
