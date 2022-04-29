package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/28 20:46
 */
@Controller
public class PageDispatchController {

    @GetMapping("/dispatcher/login")
    public String loginDispatcher(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        return User.USER_TYPE_ADMIN == user.getType() ? "redirect:/admin" : "redirect:/user";
    }

    @GetMapping("/user")
    public String toUserMainPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getType() != User.USER_TYPE_COMMON) {
            return "redirect:/";
        }
        return "user_main";
    }

    @GetMapping("/admin")
    public String toAdminLoginPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getType() != User.USER_TYPE_ADMIN) {
            return "redirect:/";
        }
        return "admin_login";
    }
}
