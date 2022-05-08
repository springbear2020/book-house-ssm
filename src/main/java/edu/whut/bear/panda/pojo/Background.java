package edu.whut.bear.panda.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/8 9:46
 */
@Data
@Component
public class Background {
    private Integer id;
    private Integer userId;
    private Integer uploadId;
    private Date uploadTime;
    private String url;

    public Background() {
    }

    public Background(Integer id, Integer userId, Integer uploadId, Date uploadTime, String url) {
        this.id = id;
        this.userId = userId;
        this.uploadId = uploadId;
        this.uploadTime = uploadTime;
        this.url = url;
    }
}
