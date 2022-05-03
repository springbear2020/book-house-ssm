package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Admin;
import edu.whut.bear.panda.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Comparator;

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
        if (user == null || user.getType() != User.USER_TYPE_COMMON || user.getStatus() != User.USER_STATUS_NORMAL) {
            return "redirect:/";
        }
        return "user_main";
    }

    @GetMapping("/admin")
    public String toAdminLoginPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getType() != User.USER_TYPE_ADMIN || user.getStatus() != User.USER_STATUS_NORMAL) {
            return "redirect:/";
        }
        return "admin_login";
    }

    @GetMapping("/manage")
    public String toAdminManagePage(HttpSession session) {
        // Admin admin = (Admin) session.getAttribute("admin");
        // User user = (User) session.getAttribute("user");
        // if (user == null || user.getType() != User.USER_TYPE_ADMIN || user.getStatus() != User.USER_STATUS_NORMAL) {
        //   TODO  return "redirect:/";
        // }
        // if (admin == null || admin.getStatus() != Admin.ADMIN_STATUS_NORMAL) {
        //     return "redirect:/admin";
        // }

        return "admin_manage";
    }
}
