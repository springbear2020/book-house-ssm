package edu.whut.bear.panda.util;

import java.util.Random;

/**
 * @author Spring-_-Bear
 * @datetime 2022/4/27 22:02
 */
public class NumberUtils {
    /**
     * Randomly generate digital string in length
     *
     * @return Digit string
     */
    public static String generateCodeInLengthRandomly(int len) {
        StringBuilder builder = new StringBuilder();
        for (int j = 1; j <= len; j++) {
            int randomNum = new Random().nextInt(10);
            builder.append("1793506824".charAt(randomNum));
        }
        return builder.toString();
    }

    /**
     * Convert object into an integer, if covert failed, return the default value
     *
     * @param obj          Object
     * @param defaultValue Default value
     * @return An int number value
     */
    public static int objectToInteger(Object obj, int defaultValue) {
        try {
            return Integer.parseInt(String.valueOf(obj));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
