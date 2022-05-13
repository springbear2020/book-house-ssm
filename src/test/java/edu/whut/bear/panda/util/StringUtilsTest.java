package edu.whut.bear.panda.util;

import org.junit.Test;


/**
 * @author Spring-_-Bear
 * @datetime 2022/5/8 21:19
 */
public class StringUtilsTest {
    @Test
    public void trimAllBlank() {
        String s = StringUtils.trimAllBlank(" fad \n \t dfa dfa ge  dfa");
        System.out.println(s);
    }

    @Test
    public void getContentAfterDomain() {
        System.out.println(StringUtils.getContentAfterDomain("http://rbdyxwoif.hn-bkt.clouddn.com/background/20220509172702-1.png"));
    }
}