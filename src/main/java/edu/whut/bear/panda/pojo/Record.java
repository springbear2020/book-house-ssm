package edu.whut.bear.panda.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/9 22:32
 */
@Data
@Component
public class Record {
    public static final int TYPE_UPLOAD = 0;
    public static final int TYPE_DOWNLOAD = 1;
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_ABNORMAL = 1;

    private Integer id;
    private Integer userId;
    private Integer bookId;
    private Integer type;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;
    private String title;
    private String author;

    public Record() {
    }

    public Record(Integer id, Integer userId, Integer bookId, Integer type, Integer status, Date time, String title, String author) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.type = type;
        this.status = status;
        this.time = time;
        this.title = title;
        this.author = author;
    }
}
