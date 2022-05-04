package edu.whut.bear.panda.controller;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.Upload;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.RecordService;
import edu.whut.bear.panda.util.DateUtils;
import edu.whut.bear.panda.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private RecordService recordService;

    @GetMapping("/upload/{type}")
    public Response upload(@PathVariable("type") Integer type, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.danger("登录后方可上传文件", null);
        }
        // TODO Set different config info by the file type
        String key;
        String fileSuffix = ".png";
        String mimeLimit = "image/*";
        String domain = propertyUtils.getImgDomain();
        String bucket = propertyUtils.getImgBucket();
        switch (type) {
            case Upload.TYPE_BOOK:
                fileSuffix = ".pdf";
                mimeLimit = "application/pdf";
                bucket = propertyUtils.getBookBucket();
                domain = propertyUtils.getBookDomain();
                key = user.getType() == User.USER_TYPE_COMMON ? "user/" : "admin/";
                break;
            case Upload.TYPE_COVER:
                key = "cover/";
                break;
            case Upload.TYPE_PORTRAIT:
                key = "portrait/";
                break;
            case Upload.TYPE_BACKGROUND:
                key = "background/";
                break;
            default:
                return Response.info("请上传正确格式的文件", null);
        }

        // Rename the save file name
        key = key + DateUtils.dateIntoFileName(new Date()) + "-" + user.getId() + fileSuffix;
        Auth auth = Auth.create(propertyUtils.getAccessKey(), propertyUtils.getSecretKey());
        // Limit the file type uploaded by the user
        String uploadToken = auth.uploadToken(bucket, key, 1800, new StringMap().put("mimeLimit", mimeLimit));
        List<String> res = new ArrayList<>();
        res.add(key);
        res.add(uploadToken);

        // Save the upload record to database
        Upload upload = new Upload(null, user.getId(), user.getType(), user.getUsername(), type, Upload.STATUS_UNPROCESSED, new Date(), domain, key, bucket);
        if (!recordService.saveUpload(upload)) {
            return Response.danger("请求上传文件失败", null);
        }
        return Response.success("", res);
    }
}
