package jetwang.framework.log;

import org.springframework.stereotype.Component;

@Component
public class Log {
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Log.class);

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public void info(Object message) {
        logger.info(message);
    }

    public void debug(Object message) {
        logger.debug(message);
    }

    public void error(Object message) {
        logger.error(message);
    }

    public void error(Object message, Throwable t) {
        logger.error(message, t);
    }

    public void warn(Object message) {
        logger.warn(message);
    }

    public void warn(Object message, Throwable t) {
        logger.warn(message, t);
    }
}
