package edu.whut.bear.panda.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 10:42
 */
@Data
@Component
public class Admin {
    public static final int ADMIN_STATUS_NORMAL = 0;
    public static final int ADMIN_STATUS_ABNORMAL = 1;

    private Integer id;
    private String username;
    private String password;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registerDate;
}
