package edu.whut.bear.panda.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    public boolean deleteBookFileByKey(String key) {
        Configuration configuration = new Configuration(Region.region0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, configuration);
        try {
            Response delete = bucketManager.delete(bookBucket, key);
            System.out.println(delete);
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String downloadBookFileByKey(String key) {
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(key, "utf-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String publicUrl = String.format("%s%s", bookDomain, encodedFileName);
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.privateDownloadUrl(publicUrl, 180);
    }
}
