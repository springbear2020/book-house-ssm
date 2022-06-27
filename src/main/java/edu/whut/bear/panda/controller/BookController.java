package edu.whut.bear.panda.controller;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.User;
import edu.whut.bear.panda.service.BookService;
import edu.whut.bear.panda.util.DateUtils;
import edu.whut.bear.panda.util.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 07:35 Monday
 */
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    public Response saveBook(Book book, @RequestParam("coverFile") MultipartFile coverFile, HttpSession session) {
        User user = (User) session.getAttribute("user");

        // Save the book file to the local disk
        String realPath = session.getServletContext().getRealPath("/static/cover");
        String fileName = user.getId() + "-" + DateUtils.dateIntoFileName(new Date()) + ".png";
        String coverPath = "static/cover/" + fileName;
        try {
            coverFile.transferTo(new File(realPath + "/" + fileName));
        } catch (IOException e) {
            return Response.danger("服务器图片保存失败，请稍后重试");
        }

        book.setDownloads(NumberUtils.generateOneNumberInBoundRandomly(10000));
        book.setBookState(Book.ON);
        book.setUploadTime(new Date());
        book.setUploadUserId(user.getId());
        book.setUploadUsername(user.getUsername());
        book.setCoverPath(coverPath);

        // Save book record
        if (!bookService.saveBook(book)) {
            return Response.danger("图书记录保存失败");
        }
        return Response.success("图书保存成功，感谢您的共享");
    }

    @GetMapping("/book/{pageNum}")
    public Response getBookPageData(@RequestParam("title") String title, @PathVariable("pageNum") Integer pageNum) {
        if (title == null) {
            title = "";
        }

        PageInfo<Book> bookPageInfo = bookService.getBookPageData(title, pageNum);
        // No book data queried
        if (bookPageInfo == null || bookPageInfo.getList() == null || bookPageInfo.getList().size() == 0) {
            return Response.info("您查询的图书暂无数据");
        }
        return Response.success("").put("bookPageData", bookPageInfo);
    }
}
