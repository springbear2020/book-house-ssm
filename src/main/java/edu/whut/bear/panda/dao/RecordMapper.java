package edu.whut.bear.panda.dao;

import edu.whut.bear.panda.pojo.Record;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/9 22:34
 */
@Repository
public interface RecordMapper {
    /**
     * Save the operation record
     *
     * @param record Operation record(download book or upload book)
     * @return 1 - Save successfully
     */
    int saveRecord(Record record);

    /**
     * Get all record of user's
     *
     * @param userId Id of user
     * @param type Operation record type(download or upload book)
     * @return Record list or null
     */
    List<Record> getUserRecord(@Param("userId") Integer userId,@Param("type")Integer type);
}
