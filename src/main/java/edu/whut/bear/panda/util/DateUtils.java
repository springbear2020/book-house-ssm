package edu.whut.bear.panda.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 21:44
 */
public class DateUtils {
    /**
     * java.util.Date format to Chinese datetime style like 2022-04-22 23:02
     *
     * @param date java.util.Date
     * @return Chinese style datetime like 2022-04-22 23:02
     */
    public static String dateIntoDatetime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * java.util.Date format to file name format like 202204222300
     *
     * @param date java.util.Date
     * @return Date format string like 202204222300
     */
    public static String dateIntoFileName(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date);
    }
}
