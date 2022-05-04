package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Admin;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 9:10
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public Response userLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        // Empty username or password parameters
        if (username == null || username.length() == 0 || password == null || password.length() == 0) {
            return Response.info("用户名或密码不能为空", null);
        }

        User user = userService.getUserByUsernameAndPassword(username, password);
        // Wrong username or password
        if (user == null) {
            return Response.danger("用户名不存在或密码错误", null);
        }
        // Judge the user status, whether it is abnormal
        Integer status = user.getStatus();
        if (User.USER_STATUS_ABNORMAL == status) {
            return Response.danger("账号状态异常，暂时不能登录", null);
        }
        session.setAttribute("user", user);
        return Response.success("", null);
    }

    @GetMapping("user/{username}")
    public Response verifyUsernameExistence(@PathVariable("username") String username) {
        if (username == null || username.length() == 0) {
            return Response.info("用户名不能为空", null);
        }

        User user = userService.getUserByUsername(username);
        // Username has been used by other user
        if (user != null) {
            return Response.warning("用户名已被占用，请重新输入", null);
        }
        return Response.success("", null);
    }

    @PostMapping("/user")
    public Response register(@RequestParam("verifyCode") String codeByUser, User user, HttpSession session) {
        if (codeByUser == null || codeByUser.length() == 0) {
            return Response.info("邮箱验证码不能为空", null);
        }
        // Verify the correctness of the email verify code entered by user
        String codeBySystem = (String) session.getAttribute("verifyCode");
        // The email is sending but not failed, will be sending successfully later
        if (codeBySystem == null) {
            return Response.info("正在发送验证码，请稍等···", null);
        }
        if (!codeBySystem.equals(codeByUser)) {
            return Response.danger("验证码有误，请重新输入", null);
        }
        // Save user to the database table
        if (userService.saveUser(user)) {
            session.removeAttribute("verifyCode");
            return Response.success("注册成功，赶快返回登录吧", null);
        }
        return Response.danger("注册失败，请稍后重试", null);
    }

    @GetMapping("/admin/login")
    public Response adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        // Empty username or password parameters
        if (username == null || username.length() == 0 || password == null || password.length() == 0) {
            return Response.info("用户名或密码不能为空", null);
        }
        Admin admin = userService.getAdminByUsernameAndPassword(username, password);
        if (admin == null) {
            return Response.danger("管理员账号不存在或密码错误", null);
        }
        if (admin.getStatus() == Admin.ADMIN_STATUS_ABNORMAL) {
            return Response.warning("管理员账号状态异常，暂时不能登录", null);
        }
        session.setAttribute("admin", admin);
        return Response.success("", null);
    }
}
