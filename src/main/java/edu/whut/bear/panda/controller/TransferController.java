package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.Upload;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.BookService;
import edu.whut.bear.panda.service.RecordService;
import edu.whut.bear.panda.util.DateUtils;
import edu.whut.bear.panda.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/28 17:53
 */
@RestController
public class TransferController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private QiniuUtils qiniuUtils;
    @Autowired
    private BookService bookService;

    @PostMapping("/transfer/upload/book")
    public Response bookUpload(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.danger("登录后方可上传图书文件");
        }

        String key = DateUtils.dateIntoFileName(new Date()) + "-" + user.getId() + ".pdf";
        String uploadToken = qiniuUtils.getBookUploadToken(key, "application/pdf");
        String bookPath = qiniuUtils.getBookDomain() + key;

        // Save the upload record to database
        Upload upload = new Upload(null, user.getId(), user.getType(), user.getUsername(),
                Upload.TYPE_BOOK, Upload.STATUS_UNPROCESSED, new Date(),
                qiniuUtils.getBookDomain(), key, qiniuUtils.getBookBucket());
        if (!recordService.saveUpload(upload)) {
            return Response.danger("图书上传记录保存失败");
        }
        return Response.success("").add("key", key).add("token", uploadToken).add("bookPath", bookPath);
    }

    @PostMapping("/transfer/upload/image/{type}")
    public Response imageUpload(@PathVariable("type") Integer type, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.danger("登录后方可上传图片文件");
        }

        String savePath;
        switch (type) {
            case Upload.TYPE_COVER:
                savePath = "cover/";
                break;
            case Upload.TYPE_PORTRAIT:
                savePath = "portrait/";
                break;
            case Upload.TYPE_BACKGROUND:
                savePath = "background/";
                break;
            default:
                savePath = null;
        }
        if (savePath == null) {
            return Response.danger("图片类别不正确");
        }

        String key = savePath + DateUtils.dateIntoFileName(new Date()) + "-" + user.getId() + ".png";
        String uploadToken = qiniuUtils.getImageUploadToken(key, "image/*");
        String imgPath = qiniuUtils.getImgDomain() + key;

        // Save the upload record to database
        Upload upload = new Upload(null, user.getId(), user.getType(), user.getUsername(), type, Upload.STATUS_PROCESSED, new Date(), qiniuUtils.getImgDomain(), key, qiniuUtils.getImgBucket());
        if (!recordService.saveUpload(upload)) {
            return Response.danger("图片上传记录保存失败");
        }
        return Response.success("").add("key", key).add("token", uploadToken).add("imgPath", imgPath);
    }

    @DeleteMapping("/transfer/upload/{id}")
    public Response deleteBucketFile(@PathVariable("id") Integer id) {
        Upload upload = recordService.getUploadById(id);
        if (upload == null) {
            return Response.danger("上传记录不存在");
        }
        // Delete the file by key in the Qiniu bucket
        if (!qiniuUtils.deleteBookFileByKey(upload.getKey())) {
            return Response.danger("七牛空间文件删除失败");
        }
        if (!recordService.updateUploadRecordStatus(id, Upload.STATUS_PROCESSED)) {
            return Response.danger("更新图书上传记录状态失败");
        }
        return Response.success("文件删除成功，记录已处理");
    }

    @GetMapping("/transfer/download/book/{id}")
    public Response downloadBookById(@PathVariable("id") Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.info("");
        }
        Book book = bookService.getBookById(id);
        if (book == null || book.getBookPath() == null) {
            return Response.danger("图书资源不存在");
        }
        String bookPath = book.getBookPath();
        String key = bookPath.substring(bookPath.lastIndexOf('/') + 1);
        return Response.success("").add("downloadUrl", qiniuUtils.downloadBookFileByKey(key));
    }
}
