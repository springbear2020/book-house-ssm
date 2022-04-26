package edu.whut.bear.panda.controller;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Book;
import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.service.BookService;
import edu.whut.bear.panda.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private PropertyUtils propertyUtils;

    @GetMapping("/book/page/{pageNum}")
    public Response getBookPageData(String title, @PathVariable("pageNum") Integer pageNum) {
        PageInfo<Book> bookPageInfo = bookService.getBookPageData(title.replace(" ", ""), pageNum, propertyUtils.getPageSize(), propertyUtils.getNavigationPages());
        if (bookPageInfo == null || bookPageInfo.getList() == null || bookPageInfo.getList().size() == 0) {
            return Response.failed("您查询的图书暂无数据", null);
        }
        return Response.success("", bookPageInfo);
    }
}
