package jetwang.framework.db;

import jetwang.framework.util.BeanUtils;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

public abstract class Repository<T extends Entity> {
    private IdentifierGenerator identifierGenerator;
    protected DataAccess dataAccess;
    private Class<T> entityClass;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAll(Collection<T> entities) {
        dataAccess.saveAll(entities);
    }

    public T load(Serializable id) {
        return dataAccess.load(entityClass(), id);
    }

    @Transactional
    public void save(T entity) {
        dataAccess.save(entity);
    }

    @Transactional
    public void saveOrUpdate(T entity) {
        dataAccess.getHibernateTemplate().saveOrUpdate(entity);
    }

    @Transactional
    public void saveOrUpdateAll(final Collection<T> entities) {
        dataAccess.saveOrUpdateAll(entities);
    }

    @Transactional
    public void delete(T entity) {
        dataAccess.delete(entity);
    }

    @Transactional
    public void deleteById(Serializable id) {
        dataAccess.delete(id, entityClass());
    }

    public void deleteAll(Collection<T> entities) {
        dataAccess.deleteAll(entities);
    }

    protected T loadByUniqueProperty(String propertyName, Object value) {
        return (T) dataAccess.loadByUniqueProperty(propertyName, value, entityClass());
    }

    public List<T> loadAll() {
        return dataAccess.findAll(entityClass());
    }

    public List<T> loadAll(Page page) {
        return dataAccess.findAll(entityClass(), page);
    }

    @Transactional
    public T saveFromForm(Object form) {
        T object = BeanUtils.newInstance(entityClass());
        BeanUtils.copyProperties(form, object);
        save(object);
        return object;
    }

    public void loadEntityIntoForm(Serializable id, Object form) {
        T entity = load(id);
        if (entity != null) {
            BeanUtils.copyProperties(entity, form);
        }
    }

    @Transactional
    public void update(T entity) {
        dataAccess.update(entity);
    }

    @Transactional
    public List<T> findByHQL(String hql, Object... params) {
        return dataAccess.findByHQL(hql, params);
    }

    @SuppressWarnings("unchecked")
    private Class<T> entityClass() {
        if (entityClass == null) {
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityClass;
    }

    public void setDataAccess(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
}
