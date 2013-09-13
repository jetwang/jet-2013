package jetwang.framework.util;

/**
 * @author Jet
 */
public class EnumUtils {
    public static <T extends Enum> T toEnum(Class<T> enumType, String value, T defaultValue) {
        try {
            return (T) Enum.valueOf(enumType, value);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }
}
