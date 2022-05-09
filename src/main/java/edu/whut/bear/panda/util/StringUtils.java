package edu.whut.bear.panda.util;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/8 21:17
 */
public class StringUtils {
    /**
     * Trim all blank characters in the string, including \t,\n...
     *
     * @param srcString Source string
     * @return String after trim blank
     */
    public static String trimAllBlank(String srcString) {
        return srcString.replaceAll("\\s*", "");
    }

    public static String getContentAfterDomain(String url) {
        for(int i = 0; i < 3; i++){
            url = url.substring(url.indexOf("/")+1 );
        }
        return url;
    }
}
