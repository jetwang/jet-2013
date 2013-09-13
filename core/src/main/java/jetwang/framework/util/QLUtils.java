package jetwang.framework.util;

import jetwang.framework.util.CollectionUtils.KeyPropertyGetter;

import java.util.List;

public class QLUtils {
    public static String replaceInToken(String tokenName, List ids, String hql) {
        return hql.replace(tokenName, "(" + CollectionUtils.toDelimitedString(ids, ",") + ")");
    }

    public static String replaceInStringToken(String tokenName, List<String> ids, String hql) {
        return hql.replace(tokenName, "(" + CollectionUtils.toDelimitedString(ids, new KeyPropertyGetter<String>() {
            public Object get(String item) {
                return "'" + item + "'";
            }
        }, ",") + ")");
    }

    public static String generateQuetionToken(int columnCount) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < columnCount; i++) {
            builder.append("?");
            if (i != columnCount - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
