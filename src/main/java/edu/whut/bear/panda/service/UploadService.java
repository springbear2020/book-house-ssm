package edu.whut.bear.panda.service;

import edu.whut.bear.panda.pojo.Upload;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 9:14
 */
@Service
public interface UploadService {
    /**
     * Save upload record
     *
     * @param upload Upload
     * @return true - Save successfully
     */
    boolean saveUpload(Upload upload);
}
