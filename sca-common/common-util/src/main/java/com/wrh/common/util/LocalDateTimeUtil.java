package com.wrh.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/13 15:09
 * @describe LocalDateTimeUtil 工具类
 */
public final class LocalDateTimeUtil {

    /**
     * @description : Date对象转LocalDate对象
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * @description : Date对象转LocalDateTime对象
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @description : LocalDate对象转Date对象
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @description : 字符串转Date
     */
    public static Date parseDate(String str, String pattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        if (pattern.contains("HH") || pattern.contains("mm") || pattern.contains("ss")) {
            return localDateTimeToDate(LocalDateTime.parse(str, df));
        }
        return localDateToDate(LocalDate.parse(str, df));
    }

    /**
     * @description : LocalDateTime对象转Date对象
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @description : Date转字符串
     */
    public static String format(Date date, String pattern) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(df);
    }

    /**
     * @description : 获取指定格式时间
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String fmt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);
        return localDateTime.format(formatter);
    }

    /**
     * @description : 根据时间字符串获取日期时间对象
     */
    public static LocalDateTime stringToLocalDateTime(String timeStr, String fmt) {
        Date date = parseDate(timeStr, fmt);
        return dateToLocalDateTime(date);
    }

    /**
     * @description : 北京时转世界时
     */
    public static LocalDateTime bjToUtcLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.minusHours(8);
    }

    /**
     * @description : 世界时转北京时
     */
    public static LocalDateTime utcToBjLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.plusHours(8);
    }

    /**
     * @description : 更改时间字符串的时间格式
     */
    public static String changeTimeFmt(String time, String oldFmt, String newFmt) {
        LocalDateTime localDateTime = stringToLocalDateTime(time, oldFmt);
        return localDateTimeToString(localDateTime, newFmt);
    }

    /**
     * @description : 判断是否在一个时间段内
     */
    public static boolean isInTimeRange(LocalDateTime time, LocalDateTime start, LocalDateTime end) {
        if (time == null) return false;
        return ((start.isBefore(time) || start.isEqual(time)) && (end.isAfter(time) || end.isEqual(time)));
    }

}
