package jetwang.framework.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Jet
 */
public class BeanFactory {
    private ApplicationContext context;
    private AutowireCapableBeanFactory beanFactory;

    public void autowire(Object bean) {
        beanFactory.autowireBean(bean);
    }

    public Object getBean(String beanId) {
        return beanFactory.getBean(beanId);
    }

    public <T> Collection<T> allBeans(Class<T> clazz) {
        Set<T> list = new LinkedHashSet<T>();
        list.addAll(context.getBeansOfType(clazz).values());
        ApplicationContext parent = context.getParent();
        while (parent != null) {
            list.addAll(parent.getBeansOfType(clazz).values());
            parent = parent.getParent();
        }
        return list;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        beanFactory = context.getAutowireCapableBeanFactory();
        this.context = context;
    }
}
