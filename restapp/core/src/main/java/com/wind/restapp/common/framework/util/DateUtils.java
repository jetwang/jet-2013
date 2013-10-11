package com.wind.restapp.common.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Jet
 */
public class DateUtils {

    public static final String[] SUPPORTED_FORMATS = {"yyyy-MM-dd", "MM/dd/yyyy HH:mm:ss", "MM-dd-yyyy HH:mm:ss", "MM-dd-yyyy hh:mm:ss a", "MM-dd-yyyy", "MM-dd-yy", "MM/dd/yyyy", "MM.dd.yyyy", "MM.dd.yy", "MMddyyyy", "MMddyy"};
    public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date date(int year, int month, int day) {
        return date(year, month, day, 0, 0, 0);
    }

    public static Date date(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @param date,2010-03-02 00:00:00
     * @param hour
     * @param minute
     * @param second
     * @return :2010-03-02 13:52:22
     */
    public static Date dateTime(Date date, int hour, int minute, int second, int millisecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, Convert.getInt(hour));
        cal.set(Calendar.MINUTE, Convert.getInt(minute));
        cal.set(Calendar.SECOND, Convert.getInt(second));
        cal.set(Calendar.MILLISECOND, Convert.getInt(millisecond));
        return cal.getTime();
    }

    public static Date dateTime(Date date, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, Convert.getInt(hour));
        cal.set(Calendar.MINUTE, Convert.getInt(minute));
        cal.set(Calendar.SECOND, Convert.getInt(second));
        return cal.getTime();
    }

    /**
     * @param s
     * @return
     */
    public static Date tryToConvertStringToDate(String s) {
        return null;
    }

    public static Date add(Date date, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, value);
        return calendar.getTime();
    }

    public static Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }

    public static int age(Date dateOfBirth, Date targetDate) {
        Calendar birth = calendar(dateOfBirth);
        Calendar target = calendar(targetDate);
        int age = target.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if ((birth.get(Calendar.MONTH) > target.get(Calendar.MONTH)) || (birth.get(Calendar.MONTH) == target.get(Calendar.MONTH) && birth.get(Calendar.DAY_OF_MONTH) > target.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }
        return age;
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatShortDate(Date date) {
        return SHORT_DATE_FORMAT.format(date);
    }

    public static String formatFullDate(Date date) {
        return FULL_DATE_FORMAT.format(date);
    }

    public static Date toFullDate(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        try {
            return FULL_DATE_FORMAT.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date toDate(String string) {
        if (string == null) {
            return null;
        }
        string = string.trim();
        SimpleDateFormat sdf = null;
        Date one = null;
        for (String format : SUPPORTED_FORMATS) {
            sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            try {
                one = sdf.parse(string);
                return one;
            } catch (ParseException e) {
            }
        }
        return null;
    }

    public static Date convertToNextMidNight(Date date) {
        return date == null ? null : DateUtils.dateTime(date, 24, 0, 0);
    }

    public static Date getOneDayStart(Date date) {
        return date == null ? null : DateUtils.dateTime(date, 0, 0, 0);
    }

    public static Date getOneDayEnd(Date date) {
        return date == null ? null : DateUtils.dateTime(date, 23, 59, 59);
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        return calendar(date1).get(Calendar.MONTH) == calendar(date2).get(Calendar.MONTH);
    }

    public static Date getNextDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date getLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static boolean isBetweenStartAndEndDate(Date now, Date startDate, Date endDate) {
        return (startDate.equals(now) || startDate.before(now)) && (endDate.equals(now) || endDate.after(now));
    }

    public static boolean isInEachPeriod(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        return (startDate1.after(startDate2) && startDate1.before(endDate2))
                || (endDate1.after(startDate2) && endDate1.before(endDate2))
                || (endDate1.equals(endDate2) && startDate1.equals(startDate2))
                || (endDate2.after(startDate1) && endDate2.before(endDate1))
                || (startDate2.after(startDate1) && startDate2.before(endDate1));
    }
}
