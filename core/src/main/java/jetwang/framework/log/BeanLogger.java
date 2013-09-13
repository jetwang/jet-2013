package jetwang.framework.log;

import jetwang.framework.util.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BeanLogger {
    private ThreadLocal<Integer> invokingLevel = new ThreadLocal<Integer>();
    @Autowired
    private Log log;

    public Object log(ProceedingJoinPoint call) throws Throwable {
        Integer currentLevel = invokingLevel.get();
        if (currentLevel == null) {
            currentLevel = 0;
        }
        long start = System.currentTimeMillis();
        String message = getLogEntry(currentLevel, call, "{");
        log.info(message);
        invokingLevel.set(currentLevel + 1);
        Object proceed = call.proceed();
        invokingLevel.set(currentLevel);
        message = getLogEntry(currentLevel, call, "}");
        log.info(message + ", " + (System.currentTimeMillis() - start) + "ms cost");
        return proceed;
    }

    private String getLogEntry(Integer currentLevel, ProceedingJoinPoint call, String prefix) {
        String message = buildMessage(call);
        String blankString = "";
        for (int i = 0; i < currentLevel; i++) {
            blankString += "....";
        }
        return prefix + blankString + message;
    }

    private String buildMessage(ProceedingJoinPoint call) {
        String className = call.getTarget().getClass().getSimpleName();
        String methodName = call.getSignature().getName();
        String argsText = CollectionUtils.toDelimitedString(CollectionUtils.makeList(call.getArgs()), new CollectionUtils.KeyPropertyGetter<Object>() {
            @Override
            public Object get(Object item) {
                if (item == null) {
                    return "null";
                }
                if (item instanceof String) {
                    return "'" + item + "'";
                } else if (item instanceof Collection) {
                    return "[list]";
                }
                return ObjectUtils.toString(item);
            }
        }, ", ");
        return String.format("%s.%s(%s)", className, methodName, argsText);
    }
}
