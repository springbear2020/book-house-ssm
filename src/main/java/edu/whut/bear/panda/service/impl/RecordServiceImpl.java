package edu.whut.bear.panda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.dao.BookMapper;
import edu.whut.bear.panda.dao.LoginMapper;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.pojo.Login;
import edu.whut.bear.panda.service.RecordService;
import edu.whut.bear.panda.util.PropertyUtils;
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
    private LoginMapper loginMapper;
    @Autowired
    private PropertyUtils propertyUtils;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public boolean saveLoginLog(Login login) {
        return loginMapper.saveLoginLog(login) == 1;
    }

    @Override
    public PageInfo<Login> getLoginPageData(Integer userId, Integer pageNum) {
        PageHelper.startPage(pageNum, propertyUtils.getRecordPageSize());
        List<Login> loginList = loginMapper.getLoginLogByUserId(userId);
        return new PageInfo<>(loginList, propertyUtils.getRecordNavigationPages());
    }

    @Override
    public PageInfo<Book> getUserBookRecord(Integer userId, Integer pageNum) {
        PageHelper.startPage(pageNum, propertyUtils.getRecordPageSize());
        List<Book> bookList = bookMapper.getUserBookUploadRecord(userId);
        return new PageInfo<>(bookList, propertyUtils.getRecordNavigationPages());
    }
}
