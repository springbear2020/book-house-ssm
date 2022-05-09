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

    @GetMapping("/background")
    public String background(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        return "background";
    }

    @GetMapping("/pixabay")
    public String pixabay(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        return "pixabay";
    }

    @GetMapping("/bookAdd")
    public String bookAdd(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        return "book_add";
    }
}
