package edu.whut.bear.panda.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/10 8:22
 */
@Data
@Component
public class Login {
    private Integer id;
    private Integer userId;
    private String username;
    private String ip;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    public Login() {
    }

    public Login(Integer id, Integer userId, String username, String ip, String location, Date time) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.ip = ip;
        this.location = location;
        this.time = time;
    }
}
