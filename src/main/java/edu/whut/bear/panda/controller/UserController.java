package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Login;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.EmailService;
import edu.whut.bear.panda.service.RecordService;
import edu.whut.bear.panda.service.UserService;
import edu.whut.bear.panda.util.PropertyUtils;
import edu.whut.bear.panda.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 9:10
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PropertyUtils propertyUtils;

    @ResponseBody
    @GetMapping("/user/{username}/{password}")
    public Response login(@PathVariable("username") String username, @PathVariable("password") String password, HttpServletRequest request) {
        // Empty username or password parameters
        if (username == null || username.length() == 0 || password == null || password.length() == 0) {
            return Response.info("用户名或密码不能为空");
        }

        User user = userService.getUserByUsernameAndPassword(username, password);
        // Wrong username or password
        if (user == null) {
            return Response.danger("用户名不存在或密码错误");
        }
        // Judge the user status, whether it is abnormal
        if (User.USER_STATUS_ABNORMAL == user.getStatus()) {
            return Response.danger("账号状态异常，暂时不能登录");
        }
        String ip = WebUtils.getIpAddress(request);
        String location = "未知地点";
        // Parse the ip location
        if (propertyUtils.getOpenIpParse()) {
            String parseIp = WebUtils.parseIp(ip);
            if (parseIp != null) {
                location = parseIp;
            }
        }

        Login login = new Login(null, user.getId(), user.getUsername(), ip, location, new Date());
        if (!recordService.saveLoginLog(login)) {
            return Response.danger("登录记录保存失败");
        }
        request.getSession().setAttribute("user", user);
        return Response.success("");
    }

    @ResponseBody
    @GetMapping("/user/{username}")
    public Response verifyUsernameExistence(@PathVariable("username") String username) {
        if (username == null || username.length() == 0) {
            return Response.info("用户名不能为空");
        }

        User user = userService.getUserByUsername(username);
        // Username has been used by other user
        if (user != null) {
            return Response.info("用户名已被占用，请重新输入");
        }
        return Response.success("");
    }

    @ResponseBody
    @GetMapping("/email")
    public Response verifyEmailExistence(@RequestParam("email") String email) {
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

    @ResponseBody
    @PostMapping("/email")
    public Response sendEmailVerifyCode(@RequestParam("email") String email, HttpSession session) {
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

    @ResponseBody
    @PostMapping("/user/{verifyCode}")
    public Response register(@PathVariable("verifyCode") String codeByUser, User user, HttpSession session) {
        if (codeByUser == null || codeByUser.length() == 0) {
            return Response.info("邮箱验证码不能为空");
        }

        // Verify the correctness of the email verify code entered by user
        String codeBySystem = (String) session.getAttribute("verifyCode");
        // The email is sending but not failed, will be sending successfully later
        if (codeBySystem == null) {
            return Response.info("系统正在发送验证码，请稍等···");
        }
        if (!codeBySystem.equals(codeByUser)) {
            return Response.danger("邮箱验证码有误，请重新输入");
        }
        // Save user to the database table
        user.setRegisterDate(new Date());
        user.setType(User.USER_TYPE_COMMON);
        user.setStatus(User.USER_STATUS_NORMAL);
        user.setPortraitPath(User.DEFAULT_PORTRAIT_PATH);
        if (!userService.saveUser(user)) {
            return Response.danger("注册失败，请稍后重试");
        }
        session.removeAttribute("verifyCode");
        return Response.success("注册成功，快登录吧");
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}