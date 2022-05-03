package edu.whut.bear.panda.service.impl;

import edu.whut.bear.panda.dao.AdminMapper;
import edu.whut.bear.panda.pojo.Admin;
import edu.whut.bear.panda.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 10:57
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin getAdminByUsernameAndPassword(String username, String password) {
        return adminMapper.getAdminByUsernameAndPassword(username, password);
    }
}
