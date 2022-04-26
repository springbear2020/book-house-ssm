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
    public static final int FAILED = 100;
    public static final int SUCCESS = 200;

    private Integer code;
    private String msg;
    private Map<String, Object> resultMap;

    public static Response success(String msg, Object obj) {
        return getResponse(msg, obj, SUCCESS);
    }

    public static Response failed(String msg, Object obj) {
        return getResponse(msg, obj, FAILED);
    }

    private static Response getResponse(String msg, Object obj, int code) {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("content", obj);
        response.setResultMap(map);
        return response;
    }
}