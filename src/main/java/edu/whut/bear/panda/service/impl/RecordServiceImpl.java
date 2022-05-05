package edu.whut.bear.panda.service.impl;

import edu.whut.bear.panda.dao.UploadMapper;
import edu.whut.bear.panda.pojo.Upload;
import edu.whut.bear.panda.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 9:15
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private UploadMapper uploadMapper;

    @Override
    public boolean saveUpload(Upload upload) {
        return uploadMapper.saveUpload(upload) == 1;
    }

    @Override
    public List<Upload> getBookUploadByStatus(Integer status, Integer start, Integer offset) {
        // Because of human habit start with 1 but the data table start with 0, so start - 1
        start -= 1;
        return uploadMapper.getUploadRecord(Upload.TYPE_BOOK, status, start, offset);
    }
}
