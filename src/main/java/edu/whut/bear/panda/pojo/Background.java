package edu.whut.bear.panda.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/8 9:46
 */
@Data
@Component
public class Background {
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_ABNORMAL = 1;
    private Integer id;
    private Integer userId;
    private Integer uploadId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uploadTime;
    private String url;
    private Integer status;

    public Background() {
    }

    public Background(Integer id, Integer userId, Integer uploadId, Date uploadTime, String url, Integer status) {
        this.id = id;
        this.userId = userId;
        this.uploadId = uploadId;
        this.uploadTime = uploadTime;
        this.url = url;
        this.status = status;
    }
}
