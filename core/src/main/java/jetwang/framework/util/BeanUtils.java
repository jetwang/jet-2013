package jetwang.framework.util;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author Jet
 */
public class BeanUtils {
    private static final String RESOURCE_PATTERN = "/**/*.class";
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BeanUtils.class);
    private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        assert false : "cannot create new instance for: " + clazz;
        return null;
    }

    public static void copyProperties(Object source, Object target, String... ignoredProperties) {
        try {
            org.springframework.beans.BeanUtils.copyProperties(source, target, ignoredProperties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deepCopyProperties(Object target, Object resource) {
        PropertyUtilsBean propertyUtils = BeanUtilsBean.getInstance().getPropertyUtils();
        PropertyDescriptor[] srcDescriptors = propertyUtils.getPropertyDescriptors(resource);
        for (PropertyDescriptor origDescriptor : srcDescriptors) {
            String name = origDescriptor.getName();
            if ("class".equals(name)) {
                continue; // No point in trying to set an object's class
            }
            if (propertyUtils.isReadable(resource, name) && propertyUtils.isWriteable(target, name)) {
                try {
                    Object value = propertyUtils.getSimpleProperty(resource, name);
                    propertyUtils.setProperty(target, name, deepClone(value));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deepClone(T object) {
        if (object == null) {
            return null;
        }
        if (object.getClass().isPrimitive() || object instanceof String) {
            return object;
        }
        String xml = XStream.xml.toXML(object);
        return (T) XStream.xml.fromXML(xml);
    }

    public static Object newInstance(String clazz) {
        try {
            return newInstance(BeanUtils.class.getClassLoader().loadClass(clazz));
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        assert false : "cannot load the class: " + clazz;
        return null;
    }

    public static boolean equals(Object object1, Object object2) {
        return object1 == object2 || object1 != null && object1.equals(object2);
    }

    //for example  class name:"com.base.class"  file name: "file.xml"
    //return "/com/base/file.xml"

    public static String getURLFromClassName(String className, String fileName) {
        className = className.replaceAll("\\.", "/");
        String filePath = className.substring(0, className.lastIndexOf("/") + 1) + fileName;
        return "/" + filePath;
    }

    public static Method getMethod(Class<?> clazz, String methodName) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (StringUtils.equals(method.getName(), methodName)) {
                return method;
            }
        }
        return null;
    }

    public static Class[] getAllClassesByPackageName(String packageName) {
        try {
            ArrayList<Class> classes = new ArrayList<Class>();
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(packageName) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader reader = readerFactory.getMetadataReader(resource);
                    String className = reader.getClassMetadata().getClassName();
                    classes.add(resourcePatternResolver.getClassLoader().loadClass(className));
                }
            }
            return classes.toArray(new Class[classes.size()]);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
