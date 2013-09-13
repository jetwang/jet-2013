package jetwang.framework.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Jet
 */
public class TimeUtils {

    private static SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateTimePattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Map<Integer, String> buildTimeOptionsMap(int max) {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (int i = 0; i < max; i++) {
            map.put(i, StringUtils.paddingLeft(String.valueOf(i), '0', 2));
        }
        return map;
    }

    public static List<String> buildTimeOptions(int max) {
        List<String> hours = new ArrayList<String>();
        for (int i = 0; i < max; i++) {
            hours.add(StringUtils.paddingLeft(String.valueOf(i), '0', 2));
        }
        return hours;
    }

    public static String formatDateTime(Date date) {
        return dateTimePattern.format(date);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return datePattern.format(date);
    }

    public static Date parseDate(String date) {
        try {
            return datePattern.parse(date);
        } catch (Exception ex) {
            return null;
        }
    }


    public static String buildMessageNamesuffix(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return "_" + sdf.format(date);
    }

    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    public static Date addHour(Date d, int hour) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.HOUR, hour);
        return now.getTime();
    }
}