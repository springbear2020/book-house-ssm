package edu.whut.bear.panda.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 20:41
 */
@Data
@Component
public class Pixabay {
    public static final int MAX_PAGE_NUMBER = 25;

    private Integer id;
    private String condition;
    private String tags;
    private Integer views;
    private Integer downloads;
    private Integer collections;
    private Integer likes;
    private Integer comments;
    private Date addTime;
    private String url;
}
