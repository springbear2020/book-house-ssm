package edu.whut.bear.panda.service.impl;

import edu.whut.bear.panda.dao.UploadMapper;
import edu.whut.bear.panda.pojo.Upload;
import edu.whut.bear.panda.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 9:15
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private UploadMapper uploadMapper;

    @Override
    public boolean saveUpload(Upload upload) {
        return uploadMapper.saveUpload(upload) == 1;
    }
}
