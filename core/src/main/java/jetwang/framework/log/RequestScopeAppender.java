package jetwang.framework.log;

import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class RequestScopeAppender extends WriterAppender {

    public static final String REQUEST_SCOPE_LOGGING_INFO = "_REQUEST_SCOPE_LOGGING_INFO";

    /**
     * Constructs an unconfigured appender.
     */
    public RequestScopeAppender() {
    }

    /**
     * Creates a configured appender.
     *
     * @param layout layout, may not be null.
     */
    public RequestScopeAppender(final Layout layout) {
        setLayout(layout);
        activateOptions();
    }

    /**
     * Creates a configured appender.
     *
     * @param layout    layout, may not be null.
     * @param targetStr target, either "System.err" or "System.out".
     */
    public RequestScopeAppender(final Layout layout, final String targetStr) {
        setLayout(layout);
        activateOptions();
    }

    /**
     * Prepares the appender for use.
     */
    public void activateOptions() {
        setWriter(new BufferedWriter(new ThreadLocalStringWriter()));
        super.activateOptions();
    }

    public static StringBuilder builder() {
        StringBuilder stringBuilder = (StringBuilder) RequestContextHolder.getRequestAttributes().getAttribute(REQUEST_SCOPE_LOGGING_INFO, RequestAttributes.SCOPE_REQUEST);
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder();
            RequestContextHolder.getRequestAttributes().setAttribute(REQUEST_SCOPE_LOGGING_INFO, stringBuilder, RequestAttributes.SCOPE_REQUEST);
        }
        return stringBuilder;
    }

    private static class ThreadLocalStringWriter extends Writer {
        public void write(char[] cbuf, int off, int len) throws IOException {
            builder().append(cbuf, off, len);
        }

        public void flush() throws IOException {
        }

        public void close() throws IOException {
        }
    }
}
