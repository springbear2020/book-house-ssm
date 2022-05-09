package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.EmailService;
import edu.whut.bear.panda.service.UserService;
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
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/email/{email}")
    public Response verifyEmailExistence(@PathVariable("email") String email) {
        if (email == null || email.length() == 0) {
            return Response.info("邮箱地址不能为空");
        }
        if (!email.matches(User.EMAIL_REG_EXP)) {
            return Response.danger("无效的邮箱地址");
        }

        // Verify the existence of email
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return Response.info("邮箱已被占用，请重新输入");
        }
        return Response.success("");
    }

    @PostMapping("/email/{email}")
    public Response sendEmailVerifyCode(@PathVariable("email") String email, HttpSession session) {
        if (email == null || email.length() == 0) {
            return Response.info("邮箱地址不能为空");
        }
        if (!email.matches(User.EMAIL_REG_EXP)) {
            return Response.danger("无效的邮箱地址");
        }

        // Send an verify code email to the specified email address
        String verifyCode = emailService.sendEmailVerifyCode(email);
        if (verifyCode == null) {
            return Response.info("服务器繁忙，验证码发送失败");
        }
        session.setAttribute("verifyCode", verifyCode);
        return Response.success("邮箱验证码发送成功");
    }
}
