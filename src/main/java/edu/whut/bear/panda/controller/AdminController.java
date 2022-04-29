package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/28 20:35
 */
@Controller
public class AdminController {
    @GetMapping("/admin")
    public String toAdminLoginPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getType() != User.USER_TYPE_ADMIN) {
            return "redirect:/";
        }
        return "admin_login";
    }
}
