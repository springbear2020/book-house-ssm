package edu.whut.bear.panda.util;

import javax.swing.*;
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
     * Randomly generate a random number with a specified upper limit
     *
     * @param bound Bound
     * @return Integer number
     */
    public static int generateOneNumberInBoundRandomly(int bound) {
        Random random = new Random();
        int number = random.nextInt(bound);
        return number == 0 ? number + 1 : number;
    }
}
