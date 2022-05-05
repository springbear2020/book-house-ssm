package edu.whut.bear.panda.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 20:25
 */
@Data
@Component
public class Response {
    public static final int INFO = 0;
    public static final int SUCCESS = 1;
    public static final int WARNING = 2;
    public static final int DANGER = 3;

    private Integer code;
    private String msg;
    private Map<String, Object> resultMap = new HashMap<>();

    public static Response info(String msg) {
        return getResponse(msg, INFO);
    }

    public static Response warning(String msg) {
        return getResponse(msg, WARNING);
    }

    public static Response success(String msg) {
        return getResponse(msg, SUCCESS);
    }

    public static Response danger(String msg) {
        return getResponse(msg, DANGER);
    }

    private static Response getResponse(String msg, int code) {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public Response add(String key, Object value) {
        this.getResultMap().put(key, value);
        return this;
    }
}