package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Admin;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 10:54
 */
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/login")
    public Response login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        // Empty username or password parameters
        if (username == null || username.length() == 0 || password == null || password.length() == 0) {
            return Response.warning("用户名或密码不能为空", null);
        }
        Admin admin = adminService.getAdminByUsernameAndPassword(username, password);
        if (admin == null) {
            return Response.error("管理员账号不存在或密码错误", null);
        }
        if (admin.getStatus() == Admin.ADMIN_STATUS_ABNORMAL) {
            return Response.warning("管理员账号异常，暂时不能登录", null);
        }
        session.setAttribute("admin", admin);
        return Response.success("", null);
    }
}
