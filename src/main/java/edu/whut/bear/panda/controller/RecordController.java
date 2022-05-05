package edu.whut.bear.panda.controller;

import edu.whut.bear.panda.pojo.Response;
import edu.whut.bear.panda.pojo.Upload;
import edu.whut.bear.panda.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/4 16:31
 */
@RestController
public class RecordController {
    @Autowired
    private RecordService recordService;

    @GetMapping("/record/upload/book")
    public Response getFirstUnprocessedBookUploadRecord() {
        List<Upload> bookUploadList = recordService.getBookUploadByStatus(Upload.STATUS_UNPROCESSED, 1, 1);
        if (bookUploadList == null || bookUploadList.size() == 0) {
            return Response.info("暂无未处理的图书上传记录");
        }
        return Response.success("").add("bookUploadList", bookUploadList);
    }
}
