package edu.whut.bear.panda.service.impl;

import edu.whut.bear.panda.dao.UserMapper;
import edu.whut.bear.panda.service.EmailService;
import edu.whut.bear.panda.util.EmailUtils;
import edu.whut.bear.panda.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 21:11
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public boolean isEmailExists(String email) {
        return userMapper.getUserByEmail(email) != null;
    }

    @Override
    public String sendEmailVerifyCode(String receiverEmail, int codeLength) {
        // Generate the verify code in length randomly
        String verifyCode = NumberUtils.generateCodeInLengthRandomly(codeLength);
        // try to send the email to the email address of the receiver
        if (emailUtils.sendEmail(receiverEmail, verifyCode)) {
            return verifyCode;
        }
        return null;
    }
}
