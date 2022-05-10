package edu.whut.bear.panda.controller;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Login;
import edu.whut.bear.panda.pojo.Record;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/9 23:29
 */
@RestController
public class RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping("/record/{pageNum}")
    public Response getUserRecordPageData(@PathVariable("pageNum") Integer pageNum, @RequestParam("type") Integer type, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.info("请先登录您的账号");
        }
        if (!(Record.TYPE_UPLOAD == type || Record.TYPE_DOWNLOAD == type)) {
            return Response.danger("请求操作记录类型不正确");
        }
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        PageInfo<Record> recordPageData = recordService.getRecordPageData(user.getId(), type, pageNum);
        if (recordPageData == null || recordPageData.getList() == null || recordPageData.getList().size() == 0) {
            return Response.info("暂无您的个人记录");
        }
        return Response.success("").put("recordPageData", recordPageData);
    }

    @GetMapping("/record/login/{pageNum}")
    public Response getUserLoginLog(@PathVariable("pageNum") Integer pageNum, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.info("请先登录您的账号");
        }
        // TODO judge the correctness of the page number
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        PageInfo<Login> loginPageData = recordService.getLoginPageData(user.getId(), pageNum);
        if (loginPageData == null || loginPageData.getList() == null || loginPageData.getList().size() == 0) {
            return Response.info("暂无您的个人登录记录");
        }
        return Response.success("").put("loginPageData", loginPageData);
    }
}
