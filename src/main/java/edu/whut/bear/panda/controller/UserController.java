package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 9:10
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/user/{username}")
    public Response login(@PathVariable("username") String username, @RequestParam("password") String password, HttpSession session) {
        // Empty username or password parameters
        if (username == null || username.length() == 0 || password == null || password.length() == 0) {
            return Response.warning("用户名或密码不能为空", null);
        }
        User user = userService.verifyUsernameAndPassword(username, password);
        // Wrong username or password
        if (user == null) {
            return Response.error("用户名不存在或密码错误", null);
        }
        // Judge the user status, whether it is abnormal
        Integer status = user.getStatus();
        if (User.USER_STATUS_ABNORMAL == status) {
            return Response.error("账号状态异常，暂时不能登录", null);
        }
        session.setAttribute("user", user);
        return Response.success("", null);
    }

    @ResponseBody
    @PostMapping("/user")
    public Response register(@RequestParam("verifyCode") String codeByUser, User user, HttpSession session) {
        // Verify the correctness of the email verify code entered by user
        String codeBySystem = (String) session.getAttribute("verifyCode");
        // The email is sending but not failed, will be sending successfully later
        if (codeBySystem == null) {
            return Response.info("正在发送验证码，请稍等···", null);
        }
        if (!codeBySystem.equals(codeByUser)) {
            return Response.error("验证码有误，请重新输入", null);
        }
        // Save user to the database table
        if (userService.saveUser(user)) {
            session.removeAttribute("verifyCode");
            return Response.success("注册成功，赶快返回登录吧", null);
        }
        return Response.error("注册失败，请稍后重试", null);
    }

    @GetMapping("/user")
    public String toUserMainPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getType() != User.USER_TYPE_COMMON) {
            return "redirect:/";
        }
        return "user_main";
    }
}
