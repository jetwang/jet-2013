package jetwang.framework.util;

import java.text.MessageFormat;

/**
 * @author Jet
 */
public class MessageFormatException extends Exception {
    public MessageFormatException(String pattern, Object... arguments) {
        super(MessageFormat.format(pattern, (Object[]) arguments));
    }

    public MessageFormatException(String pattern, Throwable cause, Object... arguments) {
        super(MessageFormat.format(pattern, (Object[]) arguments), cause);
    }
}
