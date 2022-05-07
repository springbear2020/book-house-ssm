package edu.whut.bear.panda.controller;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.Upload;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.BookService;
import edu.whut.bear.panda.service.RecordService;
import edu.whut.bear.panda.util.PandaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 20:07
 */
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private PandaUtils pandaUtils;
    @Autowired
    private RecordService recordService;

    @GetMapping("/book/{pageNum}")
    public Response getBookPageData(@RequestParam("title") String title, @PathVariable("pageNum") Integer pageNum) {
        int pageSize = pandaUtils.getPageSize();
        int navigationPages = pandaUtils.getNavigationPages();

        if (title == null) {
            title = "";
        }
        // Trim the blank in the title entered by user
        title = title.replace(" ", "");

        // If the page number is wrong, get the first page data by default
        int maxPageNum = bookService.getTotalPages(pageSize);
        if (pageNum == null || pageNum <= 0 || pageNum > maxPageNum) {
            pageNum = 1;
        }

        PageInfo<Book> bookPageInfo = bookService.getBookPageData(title, pageNum, pageSize, navigationPages);
        // No book data queried
        if (bookPageInfo == null || bookPageInfo.getList() == null || bookPageInfo.getList().size() == 0) {
            return Response.info("您查询的图书暂无数据");
        }
        return Response.success("").add("bookPageData", bookPageInfo);
    }

    @PostMapping("/book/{uploadRecordId}")
    public Response dealBook(@PathVariable("uploadRecordId") Integer id, Book book) {
        String comments = book.getComments();
        // Trim the blank in the comments
        comments = comments.replace(" ", "");
        book.setComments(comments);
        if (!bookService.saveBook(book)) {
            return Response.danger("新增图书记录保存失败");
        }
        // Set the relevant upload record to processed from unprocessed
        if (!recordService.updateUploadRecordStatus(id, Upload.STATUS_PROCESSED)) {
            return Response.danger("更新图书上传记录状态失败");
        }
        return Response.success("新增图书记录保存成功");
    }

    @PostMapping("/book")
    public Response saveBook(Book book, HttpSession session) {
        User user = (User) session.getAttribute("user");
        book.setUploadTime(new Date());
        book.setUploadUserId(user.getId());
        book.setUploadUsername(user.getUsername());
        if (!bookService.saveBook(book)) {
            return Response.danger("图书记录保存失败");
        }
        return Response.success("图书保存成功，感谢您的共享");
    }
}
