package edu.whut.bear.panda.util;


import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/10 8:32
 */
public class WebUtils {
    /**
     * Get the ip address from the client
     *
     * @param request HttpServletRequest from client
     * @return ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String s : ips) {
                if (!("unknown".equalsIgnoreCase(s))) {
                    ip = s;
                    break;
                }
            }
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }


    /**
     * Get the address from the ip address
     *
     * @param ip Ip
     * @return Address or null
     */
    public static String parseIp(String ip) {
        String responseContent;
        try {
            // Send a request to api.map.baidu.com for ip info
            responseContent = sendGetRequest(ip);
            if (responseContent == null) {
                return null;
            }
            // Parse the response content to Map then get address from the map
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> content = (Map<String, String>) mapper.readValue(responseContent, Map.class).get("content");
            return content.get("address");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Send a GET request to api.map.baidu.com to ask for the info about the ip given by server
     *
     * @param ip Ip address
     * @return The response content from api.map.baidu.com
     */
    private static String sendGetRequest(String ip) {
        String url = "https://api.map.baidu.com/location/ip?ak=IPhSgYKe4YCEn2KfYyQwdp3S8RtqrvV4&coor=bd09ll&ip=" + ip;
        // Try to make a connection with the request url
        URLConnection urlConnection;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            urlConnection = new URL(url).openConnection();
            HttpURLConnection connection = (HttpURLConnection) urlConnection;
            connection.setRequestMethod("GET");
            connection.connect();
            // Slice the response content line by line
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return stringBuffer.toString();
    }
}