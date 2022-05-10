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
@PropertySource("classpath:panda.properties")
@PropertySource("classpath:qiniu.properties")
public class PandaUtils {
    /**
     * Panda config data
     */
    @Value("${panda.bookPageSize}")
    private Integer pageSize;
    @Value("${panda.bookNavigationPages}")
    private Integer bookNavigationPages;
    @Value("${panda.recordPageSize}")
    private Integer recordPageSize;
    @Value("${panda.recordNavigationPages}")
    private Integer recordNavigationPages;

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
