package edu.whut.bear.panda.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 20:07
 */
@Data
@Component
public class Book {
    public static final int ON = 0;
    public static final int OFF = 1;

    private Integer id;
    private String title;
    private String author;
    private String translator;
    private Integer downloads = 0;
    private Integer collections = 0;
    private Integer uploadUserId;
    private String uploadUsername;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uploadTime;
    private Integer bookState = Book.ON;
    private String bookPath;
    private String coverPath;
    private String comments;
}
