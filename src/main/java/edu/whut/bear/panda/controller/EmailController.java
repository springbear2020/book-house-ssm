package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.service.EmailService;
import edu.whut.bear.panda.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 21:12
 */
@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private PropertyUtils propertyUtils;

    @GetMapping("/email/{email}")
    public Response verifyEmailExistence(@PathVariable("email") String email) {
        if (email == null || email.length() == 0) {
            return Response.error("邮箱地址不能为空", null);
        }
        // Verify the existence of email
        if (emailService.isEmailExists(email)) {
            return Response.warning("邮箱已被占用，请重新输入", null);
        }
        return Response.success("", null);
    }

    @GetMapping("/email")
    public Response sendEmailVerifyCode(@RequestParam("email") String email, HttpSession session) {
        if (email == null || email.length() == 0) {
            return Response.error("邮箱地址不能为空", null);
        }
        if (!email.matches("^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")) {
            return Response.error("无效的邮箱地址", null);
        }
        // Send an verify code email to the specified email address
        String verifyCode = emailService.sendEmailVerifyCode(email, propertyUtils.getCodeLength());
        if (verifyCode == null) {
            return Response.warning("服务器繁忙，验证码发送失败", null);
        }
        // TODO Set the verify code effective time 10 minutes
        session.setAttribute("verifyCode", verifyCode);
        return Response.success("", null);
    }
}
