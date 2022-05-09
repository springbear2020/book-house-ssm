package edu.whut.bear.panda.service;

import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 21:11
 */
@Service
public interface EmailService {
    /**
     * Send an email verify code to the specified email address
     *
     * @param receiverEmail Receiver email address
     * @return The email verify code which had delivered
     */
    String sendEmailVerifyCode(String receiverEmail);
}
