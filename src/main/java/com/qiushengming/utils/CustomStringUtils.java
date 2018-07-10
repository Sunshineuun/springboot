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
     *
     * @param s String
     * @return true or false
     */
    public static boolean isChinese(String s) {
        return s.matches("^[\\u4e00-\\u9fa5]*");
    }

    /***
     * 驼峰命名转为下划线命名
     *
     * @param para 驼峰命名的字符串
     */
    public static String humpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        //定位
        int temp = 0;
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toUpperCase();
    }

    /***
     * 下划线命名转为驼峰命名
     *
     * @param para 下划线命名的字符串
     */
    public static String underlineToHump(String para) {
        StringBuilder sb = new StringBuilder();
        String a[] = para.split("_");
        for (String s : a) {
            if (sb.length() == 0) {
                sb.append(s.toLowerCase());
            } else {
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
    }
}
