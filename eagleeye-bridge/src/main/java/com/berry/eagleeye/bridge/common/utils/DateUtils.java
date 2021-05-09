package com.berry.eagleeye.bridge.common.utils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/8/29 11:18
 * fileName：DateUtils
 * Use：
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_TIME_START_PATTERN = "yyyy-MM-dd 00:00:00";
    private static final String DATE_TIME_END_PATTERN = "yyyy-MM-dd 23:59:59";
    private static final String DATE_TIME_PATTERN_STR = "HHmmssSSS";

    public static String getTodayStartTime() {
        return DateTime.now().toString(DATE_TIME_START_PATTERN);
    }

    public static String getTodayEndTime() {
        return DateTime.now().toString(DATE_TIME_END_PATTERN);
    }

    public static String getNowDateString() {
        return DateTime.now().toString(DATE_PATTERN);
    }

    public static String getDateTimeString() {
        return DateTime.now().toString(DATE_TIME_PATTERN);
    }

    public static String getNowTimeString() {
        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_TIME_PATTERN_STR);
        return myFormat.format(new Date());
    }

    public static String formatDateByPattern(Date date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
    /***
     * convert Date to cron ,eg.  "0 07 10 15 1 ? 2020"
     * @param date  : 时间点
     * @return corn str
     */
    public static String getCron(Date date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }
}
