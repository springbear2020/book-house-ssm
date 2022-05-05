package edu.whut.bear.panda.util;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/5 6:48
 */
@Data
@Component
@PropertySource("classpath:qiniu.properties")
public class QiniuUtils {
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

    public String getBookUploadToken(String key, String mimeLimit) {
        // Rename the save file name
        Auth auth = Auth.create(accessKey, secretKey);
        // Limit the file type uploaded by the user
        StringMap putPolicy = new StringMap();
        putPolicy.put("mimeLimit", mimeLimit);
        return auth.uploadToken(bookBucket, key, 1800, putPolicy);
    }

    public String getImageUploadToken(String key, String mimeLimit) {
        // Rename the save file name
        Auth auth = Auth.create(accessKey, secretKey);
        // Limit the file type uploaded by the user
        StringMap putPolicy = new StringMap();
        putPolicy.put("mimeLimit", mimeLimit);
        return auth.uploadToken(imgBucket, key, 1800, putPolicy);
    }
}
