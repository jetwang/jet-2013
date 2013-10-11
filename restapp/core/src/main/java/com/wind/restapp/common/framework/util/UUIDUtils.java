package com.wind.restapp.common.framework.util;

import java.util.UUID;

/**
 * @author Jet
 */
public class UUIDUtils {

    private static long b = 1180000000000L;
    private static long seedUniquifier = System.currentTimeMillis() - b;
    private static final int DEFAULT_RADIX = 36;

    private static String getUniqueId(int length, int radix) {
        seedUniquifier++;
        String uniqueId = Long.toString(seedUniquifier, radix);

        int initialLength = uniqueId.length();
        int leftLength = initialLength - length;
        if (leftLength > 0) {
            uniqueId = uniqueId.substring(leftLength, initialLength);
        } else if (leftLength < 0) {
            StringBuilder buffer = new StringBuilder();
            while (leftLength < 0) {
                buffer.append('0');
                leftLength++;
            }
            buffer.append(uniqueId);
            uniqueId = buffer.toString();
        }
        return uniqueId;
    }

    public static String getUniqueId(int length) {
        return getUniqueId(length, DEFAULT_RADIX);
    }

    public static String getMediumUniqueId() {
        return uuid().substring(20) + getUniqueId(8);
    }

    public static String uuid() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }
}
