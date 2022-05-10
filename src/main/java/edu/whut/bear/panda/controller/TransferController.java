package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.*;
import edu.whut.bear.panda.service.BookService;
import edu.whut.bear.panda.service.PictureService;
import edu.whut.bear.panda.service.RecordService;
import edu.whut.bear.panda.service.TransferService;
import edu.whut.bear.panda.util.DateUtils;
import edu.whut.bear.panda.util.StringUtils;
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
    private BookService bookService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private TransferService transferService;

    @PostMapping("/transfer/upload/book")
    public Response bookUpload(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.danger("登录后方可上传图书文件");
        }
        // Give a new file name of the file will be uploaded
        String key = DateUtils.dateIntoFileName(new Date()) + "-" + user.getId() + ".pdf";
        // token[0]:domain    token[0]:bucket   token[0]:uploadToken
        String[] token = transferService.getFileUploadToken(key, Upload.TYPE_BOOK);

        // Save the upload record to database
        Upload upload = new Upload(null, user.getId(), user.getType(), user.getUsername(), Upload.TYPE_BOOK, Upload.STATUS_UNPROCESSED, new Date(), token[0], key, token[1]);
        if (!recordService.saveUpload(upload)) {
            return Response.danger("图书上传记录保存失败");
        }
        return Response.success("").put("key", key).put("token", token[2]).put("bookPath", token[0] + key).put("bookUploadId", upload.getId());
    }

    @PostMapping("/transfer/upload/image/{type}")
    public Response imageUpload(@PathVariable("type") Integer type, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.danger("登录后方可上传图片文件");
        }
        // Set the save directory of the different type image files
        String directory;
        switch (type) {
            case Upload.TYPE_IMAGE_COVER:
                directory = "cover/";
                break;
            case Upload.TYPE_IMAGE_BACKGROUND:
                directory = "background/";
                break;
            default:
                return Response.info("图片保存类别不正确");
        }

        String key = directory + DateUtils.dateIntoFileName(new Date()) + "-" + user.getId() + ".png";
        // token[0]:domain    token[0]:bucket   token[0]:uploadToken
        String[] token = transferService.getFileUploadToken(key, Upload.TYPE_IMAGE);

        // Save the upload record to database
        Upload upload = new Upload(null, user.getId(), user.getType(), user.getUsername(), Upload.TYPE_IMAGE_COVER, Upload.STATUS_UNPROCESSED, new Date(), token[0], key, token[1]);
        if (!recordService.saveUpload(upload)) {
            return Response.danger("图片上传记录保存失败");
        }
        if (type == Upload.TYPE_IMAGE_BACKGROUND && !pictureService.saveBackground(new Background(null, user.getId(), upload.getId(), new Date(), token[0] + key, Background.STATUS_NORMAL))) {
            return Response.danger("背景上传记录保存失败");
        }
        return Response.success("").put("key", key).put("token", token[2]).put("imgPath", token[0] + key).put("imageUploadId", upload.getId());
    }

    @GetMapping("/transfer/download/book/{id}")
    public Response bookDownload(@PathVariable("id") Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.info("请先登录您的账号");
        }
        Book book = bookService.getBookById(id);
        // Update book downloads, display the downloads as clicked amounts actually
        if (!bookService.increaseBookDownloads(book.getId())) {
            return Response.danger("图书下载量自增 1 失败");
        }
        // Empty book download url
        if (book.getBookPath() == null || book.getBookPath().length() == 0) {
            return Response.danger("暂无该图书资源");
        }
        String bookPath = book.getBookPath();
        String key = StringUtils.getContentAfterDomain(bookPath);
        // Save user book download record
        Record record = new Record(null, user.getId(), book.getId(), Record.TYPE_DOWNLOAD, Record.STATUS_NORMAL, new Date(), book.getTitle(), book.getAuthor());
        if (!recordService.saveRecord(record)) {
            return Response.danger("图书下载记录保存失败");
        }
        // Get a private download url of the book file
        return Response.success("").put("downloadUrl", transferService.getBookDownloadUrl(key));
    }
}