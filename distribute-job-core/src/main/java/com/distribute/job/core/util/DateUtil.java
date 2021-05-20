package com.distribute.job.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author renwq
 * @date 2021/5/20 11:40
 */
public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final ThreadLocal<Map<String, DateFormat>> dateFormatThreadLocal = new ThreadLocal<>();

    private static DateFormat getDateFormat(String pattern) {
        if (null == pattern || pattern.trim().length() == 0) {
            throw new IllegalArgumentException("pattern can not be empty.");
        }
        Map<String, DateFormat> dateFormatMap = dateFormatThreadLocal.get();
        if (null != dateFormatMap && dateFormatMap.containsKey(pattern)) {
            return dateFormatMap.get(pattern);
        }
        synchronized (dateFormatThreadLocal) {
            if (null == dateFormatMap) {
                dateFormatMap = new HashMap<>();
            }
            dateFormatMap.put(pattern, new SimpleDateFormat(pattern));
            dateFormatThreadLocal.set(dateFormatMap);
        }
        return dateFormatMap.get(pattern);
    }

    public static String format(Date date, String pattern) {
        return getDateFormat(pattern).format(date);
    }

    public static String formatDate(Date date) {
        return format(date, DATE_FORMAT);
    }

    public static String formatDateTime(Date date) {
        return format(date, DATETIME_FORMAT);
    }

    public static Date parse(String dateStr, String pattern) {
        try {
            return getDateFormat(pattern).parse(dateStr);
        } catch (Exception e) {
            LOGGER.warn("日期转换失败");
            return null;
        }
    }

    public static Date parseDate(String dateStr) {
        return parse(dateStr, DATE_FORMAT);
    }

    public static Date parseDateTime(String dateStr) {
        return parse(dateStr, DATETIME_FORMAT);
    }

    private static Date add(final Date date, final int calendarField, final int amount) {
        if (null == date) {
            return null;
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONDAY, amount);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }
}
