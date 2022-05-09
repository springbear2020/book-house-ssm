package edu.whut.bear.panda.service.impl;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import edu.whut.bear.panda.pojo.Upload;
import edu.whut.bear.panda.service.TransferService;
import edu.whut.bear.panda.util.PandaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/8 23:31
 */
@Service
public class TransferServiceImpl implements TransferService {
    @Autowired
    private PandaUtils pandaUtils;

    @Override
    public String[] getFileUploadToken(String key, Integer fileType) {
        StringMap putPolicy = new StringMap();
        // Limit the file type uploaded by user
        String domain;
        if (fileType == Upload.TYPE_BOOK) {
            putPolicy.put("mimeLimit", "application/pdf");
            domain = pandaUtils.getBookDomain();
        } else {
            putPolicy.put("mimeLimit", "image/*");
            domain = pandaUtils.getImgDomain();
        }
        String bucket = fileType == Upload.TYPE_BOOK ? pandaUtils.getBookBucket() : pandaUtils.getImgBucket();
        Auth auth = Auth.create(pandaUtils.getAccessKey(), pandaUtils.getSecretKey());
        String token = auth.uploadToken(bucket, key, 1800, putPolicy);
        return new String[]{domain, bucket, token};
    }

    @Override
    public String getBookDownloadUrl(String key) {
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(key, "utf-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String publicUrl = String.format("%s%s", pandaUtils.getBookDomain(), encodedFileName);
        Auth auth = Auth.create(pandaUtils.getAccessKey(), pandaUtils.getSecretKey());
        return auth.privateDownloadUrl(publicUrl, 1800);
    }
}
