package edu.whut.bear.panda.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022-06-27 10:05 Monday
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Background {
    public static final int NORMAL = 0;
    public static final int ABNORMAL = 1;

    private Integer id;
    private Integer userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uploadTime;
    private String url;
    private Integer status;
}
