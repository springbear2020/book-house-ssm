package edu.whut.bear.panda.controller;

import com.qiniu.util.Auth;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.util.DateUtils;
import edu.whut.bear.panda.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/28 17:53
 */
@RestController
public class TransferController {
    @Autowired
    private PropertyUtils propertyUtils;

    @GetMapping("/upload")
    public Response getQiniuToken(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // TODO upload other type files
            return Response.error("登录后方可上传图书", null);
        }
        Auth auth = Auth.create(propertyUtils.getAccessKey(), propertyUtils.getSecretKey());
        // Rename the file upload by user e.g directory/ datetime-userId.pdf
        String key = "upload/" + DateUtils.dateIntoFileName(new Date()) + "-" + user.getId() + ".pdf";
        String uploadToken = auth.uploadToken(propertyUtils.getBucketName(), key);
        List<String> responseList = new ArrayList<>();
        responseList.add(key);
        responseList.add(uploadToken);
        return Response.success("", responseList);
    }
}
