package com.johnson.grab.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 13-9-10
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {

    /**
     * Assert whether src is an empty string.
     * Null or 0-length string or full blank string are empty string.
     *
     * @param src
     * @return
     */
    public static boolean isEmpty(String src) {
        if (src == null) {
            return true;
        }
        return src.trim().length() == 0;
    }

    /**
     * First letter of src transform to uppercase
     *      For example, word 'firstLetter' will transform to 'FirstLetter'
     * @param src
     *      src
     * @return
     */
    public static String firstLetterCapital(String src) {
        if (src.length() == 1) {
            return src.toUpperCase();
        }
        return src.substring(0, 1).toUpperCase() + src.substring(1);
    }

    /**
     * Judge whether an array is empty.
     * An empty array is a null or 0-length array.
     *
     * @param array
     * @return
     */
    public static boolean isEmptyArray(String[] array) {
        return array==null || array.length==0;
    }

    /**
     * Combine a string array to a string, and different strings separated by separator.
     * @param array
     * @param separator
     * @return
     */
    public static String arrayToString(String[] array, String separator) {
        if (isEmptyArray(array)) {
            return "";
        }
        if (array.length == 1) {
            return array[0];
        }
        StringBuilder result = new StringBuilder();
        for (String str : array) {
            result.append(str).append(separator);
        }
        return result.substring(0, result.length() - separator.length());
    }

    public static <T> String arrayToString(T[] ts) {
        return Arrays.asList(ts).toString();
    }

    /**
     * Extract keys from src by pattern.
     * Only extract the contents in parentheses.
     * @param src
     * @param pattern
     * @return
     */
    public static String extractByPattern(String src, Pattern pattern) {
        if (pattern == null || StringUtil.isEmpty(src)) {
            return src;
        }
        Matcher matcher = pattern.matcher(src);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            int count = matcher.groupCount();
            for (int i=1; i<=count; i++) {
                if (!StringUtil.isEmpty(matcher.group(i))) {
                    result.append(matcher.group(i));
                }
            }
        }
        return result.toString();
    }

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
}
