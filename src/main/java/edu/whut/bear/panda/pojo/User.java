package edu.whut.bear.panda.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 8:51
 */
@Data
@Component
public class User {
    public static final int USER_TYPE_COMMON = 0;
    public static final int USER_TYPE_ADMIN = 1;
    public static final int USER_STATUS_NORMAL = 0;
    public static final int USER_STATUS_ABNORMAL = 1;
    public static final String DEFAULT_PORTRAIT_PATH = "static/img/portrait.png";

    private Integer id;
    private String username;
    private String password;
    private String email;
    private Integer type = USER_TYPE_COMMON;
    private Integer status = USER_STATUS_NORMAL;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registerDate = new Date();
    private String portraitPath = DEFAULT_PORTRAIT_PATH;
}
