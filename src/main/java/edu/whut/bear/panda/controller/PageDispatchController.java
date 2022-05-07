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
    @GetMapping("/user")
    public String toUserMainPage(HttpSession session) {
        // User user = (User) session.getAttribute("user");
        // if (user == null || user.getType() != User.USER_TYPE_COMMON || user.getStatus() != User.USER_STATUS_NORMAL) {
        //     return "redirect:/";
        // }
        return "main";
    }

    @GetMapping("user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String toAdminLoginPage(HttpSession session) {
        // User user = (User) session.getAttribute("user");
        // if (user == null || user.getType() != User.USER_TYPE_ADMIN || user.getStatus() != User.USER_STATUS_NORMAL) {
        //     return "redirect:/";
        // }
        return "admin_login";
    }

    @GetMapping("/manage")
    public String toAdminManagePage(HttpSession session) {
        // User user = (User) session.getAttribute("user");
        // if (user == null || user.getType() != User.USER_TYPE_ADMIN || user.getStatus() != User.USER_STATUS_NORMAL) {
        //     return "redirect:/";
        // }
        //
        // Admin admin = (Admin) session.getAttribute("admin");
        // if (admin == null || admin.getStatus() != Admin.ADMIN_STATUS_NORMAL) {
        //     return "redirect:/admin";
        // }

        return "admin_manage";
    }
}
