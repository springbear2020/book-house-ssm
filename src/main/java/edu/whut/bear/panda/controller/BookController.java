package edu.whut.bear.panda.controller;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.service.BookService;
import edu.whut.bear.panda.util.PandaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
