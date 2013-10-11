package com.wind.restapp.common.framework.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Jet
 */
public class ExceptionUtils {
    public static String stackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer, true));
        return writer.toString();
    }
}
