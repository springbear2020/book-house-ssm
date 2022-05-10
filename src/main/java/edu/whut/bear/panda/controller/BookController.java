package edu.whut.bear.panda.controller;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.pojo.Record;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.BookService;
import edu.whut.bear.panda.service.RecordService;
import edu.whut.bear.panda.util.StringUtils;
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
    private RecordService recordService;

    @GetMapping("/book/{pageNum}")
    public Response getBookPageData(@RequestParam("title") String title, @PathVariable("pageNum") Integer pageNum) {
        if (title == null) {
            title = "";
        }
        // Trim the blank in the title entered by user
        title = StringUtils.trimAllBlank(title);

        // If the page number is wrong, get the first page data by default
        int maxPageNum = bookService.getTotalPages();
        if (pageNum == null || pageNum <= 0 || pageNum > maxPageNum) {
            pageNum = 1;
        }

        PageInfo<Book> bookPageInfo = bookService.getBookPageData(title, pageNum);
        // No book data queried
        if (bookPageInfo == null || bookPageInfo.getList() == null || bookPageInfo.getList().size() == 0) {
            return Response.info("您查询的图书暂无数据");
        }
        return Response.success("").put("bookPageData", bookPageInfo);
    }

    @PostMapping("/book")
    public Response saveBook(Book book, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Response.info("登录后方可新增图书");
        }
        book.setDownloads(0);
        book.setBookState(Book.WAIT);
        book.setUploadTime(new Date());
        book.setUploadUserId(user.getId());
        book.setUploadUsername(user.getUsername());
        // Trim the blank in the book comments
        book.setComments(StringUtils.trimAllBlank(book.getComments()));
        if (!bookService.saveBook(book)) {
            return Response.danger("图书记录保存失败");
        }
        Record record = new Record(null, user.getId(), book.getId(), Record.TYPE_UPLOAD, Record.STATUS_NORMAL, new Date(), book.getTitle(), book.getAuthor());
        if (!recordService.saveRecord(record)) {
            return Response.danger("图书上传记录保存失败");
        }
        return Response.success("图书保存成功，感谢您的共享");
    }
}
