package com.qiushengming.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    private static String FORMAT1 = "yyyy-MM-dd HH:mm";
    private static SimpleDateFormat SIPLE_DATE_FORMAT = new SimpleDateFormat(FORMAT1);

    public static String dateToString(Date date) {
        return SIPLE_DATE_FORMAT.format(date);
    }
}
