package edu.whut.bear.panda.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/26 20:32
 */
@Data
@Component
@PropertySource("classpath:jdbc.properties")
@PropertySource("classpath:qiniu.properties")
@PropertySource("classpath:email.properties")
@PropertySource("classpath:panda.properties")
public class PropertyUtils {
    /**
     * JDBC config data
     */
    @Value("${jdbc.driverClass}")
    private String driverClass;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    /**
     * Panda config data
     */
    @Value("${panda.bookPageSize}")
    private Integer pageSize;
    @Value("${panda.navigationPages}")
    private Integer navigationPages;

    /**
     * Email config data
     */
    @Value("${email.fromEmail}")
    private String fromEmail;
    @Value("${email.password}")
    private String emailPassword;
    @Value("${email.smtpHost}")
    private String smtpHost;
    @Value("${email.codeLength}")
    private Integer codeLength;

    /**
     * Qiniu config data
     */
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bookBucketName}")
    private String bookBucket;
    @Value("${qiniu.imgBucketName}")
    private String imgBucket;
    @Value("${qiniu.bookCdnDomain}")
    private String bookDomain;
    @Value("${qiniu.imgCdnDomain}")
    private String imgDomain;
}
