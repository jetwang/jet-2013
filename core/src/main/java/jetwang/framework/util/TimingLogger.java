package jetwang.framework.util;

import org.apache.log4j.Logger;

import java.util.Date;

public class TimingLogger {
    private static final Logger LOGGER = Logger.getLogger(TimingLogger.class);
    private static ThreadLocal<Date> startTime = new ThreadLocal<Date>();
    private static final String TIME_TOKEN = "#time#";

    public static void log(String message) {
        try {
            Date endTime = new Date();
            if (startTime.get() != null) {
                long usage = endTime.getTime() - startTime.get().getTime();
                LOGGER.info(getLoggingMessage(message, usage));
            }
            startTime.set(endTime);
        } catch (Exception e) {
            LOGGER.warn("error when log: "+e.getMessage());
        }
    }

    private static String getLoggingMessage(String message, long usage) {
        if (StringUtils.hasText(message)) {
            message = message.replaceAll(TIME_TOKEN, usage + " ms");
        }
        return message;
    }
}
