package edu.whut.bear.panda.service;

import com.github.pagehelper.PageInfo;
import edu.whut.bear.panda.pojo.Login;
import edu.whut.bear.panda.pojo.Record;
import edu.whut.bear.panda.pojo.Upload;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;



/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 9:14
 */
@Service
public interface RecordService {
    /**
     * Save upload record
     *
     * @param upload Upload
     * @return true - Save successfully
     */
    boolean saveUpload(Upload upload);

    /**
     * Save the operation record
     *
     * @param record Operation record(download book or upload book)
     * @return true - Save successfully
     */
    boolean saveRecord(Record record);

    /**
     * Get user's record page data
     *
     * @param userId  Id of user
     * @param type    Operation record type(download or upload book)
     * @param pageNum The page number
     * @return Record page data or null
     */
    PageInfo<Record> getRecordPageData(@Param("userId") Integer userId, @Param("type") Integer type, Integer pageNum);

    /**
     * Save the log of the user
     *
     * @param login Login log
     * @return true - Save successfully
     */
    boolean saveLoginLog(Login login);

    /**
     * Get the user's login page data
     *
     * @param userId Id of user
     * @return Login page data or null
     */
    PageInfo<Login> getLoginPageData(Integer userId, Integer pageNum);
}
