package com.qiushengming.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

  private static String FORMAT1 = "yyyyMMddHHmm";
  private static SimpleDateFormat SIPLE_DATE_FORMAT = new SimpleDateFormat(FORMAT1);

  public static String dateToString(Date date) {
    return SIPLE_DATE_FORMAT.format(date);
  }

  public static String dateToDayString(Date date) {
    return SIPLE_DATE_FORMAT.format(date).substring(0, 8);
  }

  public static void main(String[] args) {
    System.out.println(dateToDayString(new Date()));
  }
}
