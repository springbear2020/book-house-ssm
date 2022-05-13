package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Admin;
import edu.whut.bear.panda.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/28 20:46
 */
@Controller
public class DispatcherController {
    @GetMapping("/user")
    public String user(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        return "main";
    }

    @GetMapping("user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/bookAdd")
    public String bookAdd(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        return "book_add";
    }

    @GetMapping("/history")
    public String history(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        return "history";
    }

    @GetMapping("/pixabay")
    public String admin(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin";
        }
        return "pixabay";
    }
}
