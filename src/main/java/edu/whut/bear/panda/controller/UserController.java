package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @GetMapping("/user")
    public Response login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        // Empty username or password parameters
        if (username == null || username.length() == 0 || password == null || password.length() == 0) {
            return Response.failed("用户名或密码不能为空", null);
        }
        User user = userService.verifyUsernameAndPassword(username, password);
        // Wrong username or password
        if (user == null) {
            return Response.failed("用户名不存在或密码错误", null);
        }
        // Judge the user status, whether it is abnormal
        Integer status = user.getStatus();
        if (User.USER_STATUS_ABNORMAL == status) {
            return Response.failed("账号状态异常，暂时不能登录", null);
        }
        session.setAttribute("user", user);
        return Response.success("", null);
    }

    @GetMapping("/dispatcher")
    public String loginPageRedirect(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        return User.USER_TYPE_ADMIN == user.getType() ? "redirect:/admin" : "redirect:/main";
    }
}
