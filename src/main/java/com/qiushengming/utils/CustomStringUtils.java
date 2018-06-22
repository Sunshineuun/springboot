package com.qiushengming.utils;

/**
 * @author qiushengming
 * @date 2018/6/22
 */
public class CustomStringUtils {
    /**
     * 将s第一个字符转为大写
     *
     * @param s string
     * @return string
     */
    public static String captureName(String s) {
        if (isChinese(s)) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 将s第一个字符转为大写
     *
     * @param s string
     * @return string
     */
    public static String captureName1(String s) {
        if (isChinese(s)) {
            return s;
        }
        char[] c = s.toCharArray();
        c[0] -= 32;
        return String.valueOf(c);
    }

    /**
     * 是否是英文
     *
     * @param s String
     * @return true or false
     */

    public static boolean isEnglish(String s) {
        return s.matches("^[a-zA-Z0-9]*");
    }

    /**
     * 是否为中文
     * @param s String
     * @return true or false
     */
    public static boolean isChinese(String s) {
        return s.matches("^[\\u4e00-\\u9fa5]*");
    }

    public static void main(String[] args) {
    }
}
