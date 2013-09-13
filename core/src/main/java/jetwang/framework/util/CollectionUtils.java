package jetwang.framework.util;

import java.io.File;
import java.util.*;

public class CollectionUtils {
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static String listToString(List<String> items, String separator) {
        if (isEmpty(items)) return "";
        String result = "";
        int size = items.size();
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                result += items.get(i).trim() + separator;
            } else {
                result += items.get(i).trim();
            }
        }
        return result;
    }

    public static <T> List<T> setToList(Set<T> items) {
        List<T> results = new ArrayList<T>();
        for (T item : items) {
            results.add(item);
        }
        return results;
    }

    public static <T> Set<T> listToSet(List<T> items) {
        Set<T> results = new HashSet<T>();
        for (T item : items) {
            results.add(item);
        }
        return results;
    }

    public static <T> List<T> findAll(List<T> items, Matcher<T> matcher) {
        List<T> results = new ArrayList<T>();
        for (T item : items) {
            if (matcher.match(item)) results.add(item);
        }
        return results;
    }

    public static <T> T findFirst(List<T> items, Matcher<T> matcher) {
        for (T item : items) {
            if (matcher.match(item)) return item;
        }
        return null;
    }

    public static <T> boolean allMatch(Collection<T> collection, Matcher<T> matcher) {
        for (T item : collection) {
            if (!matcher.match(item)) return false;
        }
        return true;
    }

    public static <T> boolean has(Collection<T> collection, Matcher<T> matcher) {
        for (T item : collection) {
            if (matcher.match(item)) return true;
        }
        return false;
    }

    public static <T> List<T> makeList(T... objects) {
        List<T> list = new ArrayList<T>();
        list.addAll(Arrays.asList(objects));
        return list;
    }

    public static <K, V, T> Map<K, V> makeMap(Collection<T> collection, Putter<T, K, V> putter) {
        Map<K, V> map = new HashMap<K, V>();
        for (T t : collection) {
            putter.put(t, map);
        }
        return map;
    }

    public static <K, V, T> Map<K, V> makeMap(T[] collection, Putter<T, K, V> putter) {
        Map<K, V> map = new HashMap<K, V>();
        for (T t : collection) {
            putter.put(t, map);
        }
        return map;
    }

    public static <T> T[] makeArray(T... objects) {
        return objects;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<T> objects) {
        return (T[]) objects.toArray(new File[objects.size()]);
    }

    public static <T> void addAll(Collection<T> collection, T[] array) {
        collection.addAll(Arrays.asList(array));
    }

    public static boolean contains(Object[] items, Object expected) {
        for (Object item : items) {
            if (BeanUtils.equals(item, expected)) return true;
        }
        return false;
    }

    public static boolean equal(Object[] array1, Object[] array2) {
        if (array1 == null) {
            return array2 == null;
        }
        if (array1.length != array2.length) return false;
        for (int i = 0; i < array1.length; i++) {
            if (!array1[i].equals(array2[i])) return false;
        }
        return true;
    }

    public static int indexOf(String[] strings, String s) {
        for (int i = 0; i < strings.length; i++) {
            if (StringUtils.equals(strings[i], s))
                return i;
        }
        return -1;
    }

    public static <T> boolean isEmpty(Collection<T> items) {
        return items == null || items.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> items) {
        return !isEmpty(items);
    }

    public static List<String> split(String text, String delimiterValue) {
        List<String> resultItems = new ArrayList<String>();
        if (StringUtils.isEmpty(text))
            return resultItems;
        String[] originalItems = text.split(delimiterValue);
        for (String value : originalItems) {
            if (StringUtils.hasText(value))
                resultItems.add(value.trim());
        }
        return resultItems;
    }

    public static List<Integer> splitAsInt(String text, String delimiterValue) {
        List<String> strings = split(text, delimiterValue);
        List<Integer> integers = new ArrayList<Integer>();
        for (String string : strings) {
            try {
                integers.add(Integer.parseInt(string));
            } catch (NumberFormatException e) {
                //ignore 
            }
        }
        return integers;
    }

    public static <T> String toDelimitedString(Collection<T> items, KeyPropertyGetter<T> getter, String delimiterValue) {
        if (CollectionUtils.isEmpty(items))
            return "";
        StringBuilder builder = new StringBuilder();
        for (T item : items) {
            builder.append(getter.get(item)).append(delimiterValue);
        }
        return builder.substring(0, builder.lastIndexOf(delimiterValue));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String toDelimitedString(Collection items, String delimiterValue) {
        if (CollectionUtils.isEmpty(items))
            return "";
        StringBuilder builder = new StringBuilder();
        for (Object item : items) {
            builder.append(item == null ? "" : item).append(delimiterValue);
        }
        return builder.substring(0, builder.lastIndexOf(delimiterValue));
    }

    public static interface Matcher<T> {
        boolean match(T item);
    }

    public static interface Putter<T, K, V> {
        void put(T object, Map<K, V> map);
    }

    public static interface KeyPropertyGetter<T> {
        Object get(T item);
    }

    @SuppressWarnings("unchecked")
    public static <K, T> Map<K, T> sortMapByKey(Map<K, T> originalMap, Comparator<? super K>... c) {
        Map<K, T> sortedMap = new LinkedHashMap<K, T>();
        K[] keys = (K[]) originalMap.keySet().toArray();
        if (c.length == 0) {
            Arrays.sort(keys);
        } else {
            Arrays.sort(keys, c[0]);
        }
        for (int i = 0; i < keys.length; i++) {
            sortedMap.put(keys[i], originalMap.get(keys[i]));
        }
        return sortedMap;
    }

    @SuppressWarnings("unchecked")
    public static <K, T> Map<K, T> sortMapByValue(final Map<K, T> originalMap, final Comparator<? super K>... c) {
        Map<K, T> sortedMap = new LinkedHashMap<K, T>();
        K[] keys = (K[]) originalMap.keySet().toArray();
        if (c.length == 0) {
            Arrays.sort(keys, new Comparator<K>() {
                public int compare(Object o1, Object o2) {
                    T v1 = originalMap.get(o1);
                    T v2 = originalMap.get(o2);
                    if (v1 == null) {
                        return (v2 == null) ? 0 : 1;
                    } else if (v1 instanceof String && StringUtils.isLong((String) v1)
                            && v2 instanceof String && StringUtils.isLong((String) v2)) {
                        Long l1 = Long.valueOf((String) v1);
                        Long l2 = Long.valueOf((String) v2);
                        return (l1 - l2) > 0 ? 1 : (l1 - l2) == 0 ? 0 : -1;
                    } else if (v1 instanceof Comparable) {
                        return ((Comparable<T>) v1).compareTo(v2);
                    } else {
                        return 0;
                    }
                }
            });
        } else {
            Arrays.sort(keys, c[0]);
        }
        for (K key : keys) {
            sortedMap.put(key, originalMap.get(key));
        }
        return sortedMap;
    }

}
