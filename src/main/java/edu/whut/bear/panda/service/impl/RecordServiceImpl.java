package edu.whut.bear.panda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.dao.LoginMapper;
import edu.whut.bear.panda.dao.RecordMapper;
import edu.whut.bear.panda.dao.UploadMapper;
import edu.whut.bear.panda.pojo.Login;
import edu.whut.bear.panda.pojo.Record;
import edu.whut.bear.panda.pojo.Upload;
import edu.whut.bear.panda.service.RecordService;
import edu.whut.bear.panda.util.PandaUtils;
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
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private PandaUtils pandaUtils;

    @Override
    public boolean saveUpload(Upload upload) {
        return uploadMapper.saveUpload(upload) == 1;
    }

    @Override
    public boolean saveRecord(Record record) {
        return recordMapper.saveRecord(record) == 1;
    }

    @Override
    public PageInfo<Record> getRecordPageData(Integer userId, Integer type, Integer pageNum) {
        PageHelper.startPage(pageNum, pandaUtils.getRecordPageSize());
        List<Record> recordList = recordMapper.getUserRecord(userId, type);
        return new PageInfo<>(recordList, pandaUtils.getRecordNavigationPages());
    }

    @Override
    public boolean saveLoginLog(Login login) {
        return loginMapper.saveLoginLog(login) == 1;
    }

    @Override
    public PageInfo<Login> getLoginPageData(Integer userId, Integer pageNum) {
        PageHelper.startPage(pageNum, pandaUtils.getRecordPageSize());
        List<Login> loginList = loginMapper.getLoginLogByUserId(userId);
        return new PageInfo<>(loginList, pandaUtils.getRecordNavigationPages());
    }
}