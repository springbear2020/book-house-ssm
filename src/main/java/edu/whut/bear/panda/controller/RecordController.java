package edu.whut.bear.panda.controller;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.pojo.Login;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 11:39 Monday
 */
@RestController
public class RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping("/record/login/{pageNum}")
    public Response getUserLoginLog(@PathVariable("pageNum") Integer pageNum, HttpSession session) {
        User user = (User) session.getAttribute("user");
        PageInfo<Login> loginPageData = recordService.getLoginPageData(user.getId(), pageNum);
        if (loginPageData == null || loginPageData.getList() == null || loginPageData.getList().size() == 0) {
            return Response.info("暂无您的个人登录记录");
        }
        return Response.success("").put("loginPageData", loginPageData);
    }

    @GetMapping("/record/{pageNum}")
    public Response userBookRecord(@PathVariable("pageNum") Integer pageNum,
                                   HttpSession session) {
        User user = (User) session.getAttribute("user");
        PageInfo<Book> recordPageData = recordService.getUserBookRecord(user.getId(), pageNum);
        if (recordPageData == null || recordPageData.getList() == null || recordPageData.getList().size() == 0) {
            return Response.info("暂无您的个人图书上传记录");
        }
        return Response.success("").put("recordPageData", recordPageData);
    }
}
