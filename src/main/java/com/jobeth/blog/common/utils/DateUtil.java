package com.jobeth.blog.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author Administrator
 */
public class DateUtil {
    /**
     * 获得当前日期
     *
     * @return 当前日期
     */
    public static Date getNow() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    /**
     * 日期转换为字符串 格式自定义
     *
     * @param date date
     * @param formatStr formatStr
     * @return dateString
     */
    public static String dateString(Date date, String formatStr) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date date
     * @return dateString
     */
    public static String dateString(Date date) {
        return dateString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将Date转换为时间戳
     *
     * @param date date
     * @return long
     */
    public static long getTime(Date date) {
        return date.getTime() / 1000;
    }


    /**
     * 将时间戳转换为Date
     *
     * @param times times
     * @return Date
     */
    public static Date getDate(String times) {
        long time = Long.parseLong(times);
        return new Date(time * 1000);
    }
}
