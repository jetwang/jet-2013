package com.wind.restapp.common.framework.db;

import com.wind.restapp.common.framework.util.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import javax.sql.DataSource;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Jet
 */
public class DataAccess {
    @SuppressWarnings("unused")
    private static final Pattern SELECT_FROM_PATTERN = Pattern.compile("^select .* from (.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern ORDER_BY_PATTERN = Pattern.compile("(.*) order by ^(select)*", Pattern.CASE_INSENSITIVE);
    private HibernateTemplate hibernateTemplate;
    private JdbcTemplate jdbcTemplate;

    public <T> T load(Class<T> clazz, Serializable id) {
        return hibernateTemplate.get(clazz, id);
    }

    public void save(Object entity) {
        hibernateTemplate.save(entity);
    }

    public void saveOrUpdateAll(final Collection entities) {
        hibernateTemplate.saveOrUpdateAll(entities);
    }

    public void saveAll(final Collection<?> entities) {
        hibernateTemplate.executeWithNativeSession(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException {
                int i = 1;
                int jdbcBatchSize = 50;
                for (Object entity : entities) {
                    session.save(entity);
                    if (i % jdbcBatchSize == 0) {
                        session.flush();
                        session.clear();
                    }
                }
                return null;
            }
        });
    }

    public void delete(Object entity) {
        hibernateTemplate.delete(entity);
    }

    public <T> void deleteAll(Class<T> clazz) {
        hibernateTemplate.bulkUpdate("delete " + clazz.getName());
    }

    public <T> void deleteAll(Collection<T> entities) {
        hibernateTemplate.deleteAll(entities);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(final com.wind.restapp.common.framework.db.Criteria<T> criteria) {
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.createCriteria(session);
                return hibernateCriteria.list();
            }
        });
    }

    public <T> T findFirstResultByCriteria(final com.wind.restapp.common.framework.db.Criteria<T> criteria) {
        List<T> list = findByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByHQL(String hql, Object... params) {
        return hibernateTemplate.find(hql, params);
    }


    public void update(Object entity) {
        hibernateTemplate.update(entity);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        return hibernateTemplate.loadAll(clazz);
    }


    @SuppressWarnings("unchecked")
    public <T> T loadByUniqueProperty(String propertyName, Object value, Class<T> clazz) {
        com.wind.restapp.common.framework.db.Criteria criteria = com.wind.restapp.common.framework.db.Criteria.of(clazz);
        criteria.eq(propertyName, value);
        List<T> list = this.findByCriteria(criteria);
        if (list == null || list.isEmpty()) return null;
        return list.get(0);
    }

    public <T> void delete(Serializable id, Class<T> poClass) {
        Entity po = (Entity) hibernateTemplate.load(poClass, id);
        hibernateTemplate.delete(po);
    }

    public <T> List<T> selectAll(Class<T> cls) {
        return hibernateTemplate.loadAll(cls);
    }

    public <T> List<T> findAll(final Class<T> cls, final Page page) {
        return findByCriteria(new com.wind.restapp.common.framework.db.Criteria<T>(cls), page);
    }


    @SuppressWarnings("unchecked")
    public <T> List<T> findByHQL(final String hqlstr, final Page page) {
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                List ls;
                Query query = session.createQuery(hqlstr);
                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();
                int rownum = scrollableResults.getRowNumber();
                page.setRowCount(rownum + 1);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getStartRowNumber());
                query.setCacheable(true);
                ls = query.list();
                if (ls == null)
                    ls = new ArrayList(0);
                return ls;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> List<T> findByHQL(final String hqlstr, final Page page, final Object... parameters) {
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                List ls;
                String hql = hqlstr;
                if (page.hasOrder()) {
                    hql += " order by " + page.getOrderField() + " " + (page.isAsc() ? "asc" : "desc");
                }
                Query query = session.createQuery(hql);
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i, parameters[i]);
                }
                if (page.isCalculateRowCount()) {
                    ScrollableResults scrollableResults = query.scroll();
                    scrollableResults.last();
                    int rownum = scrollableResults.getRowNumber();
                    page.setRowCount(rownum + 1);
                }
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getStartRowNumber());
                ls = query.list();
                if (ls == null)
                    ls = new ArrayList(0);
                return ls;
            }
        });
    }

    public <T> List<T> findBySQL(final String sql, final Object... parameters) {
        return findEntitiesBySQL(sql, null, parameters);
    }

    public <T> T findUniqueBySQL(final String sql, final Object... parameters) {
        List<T> list = findBySQL(sql, parameters);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return (T) list.get(0);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findEntitiesBySQL(final String sql, final Class<T> mappedClass, final Object... parameters) {
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                if (mappedClass != null) {
                    query.addEntity(mappedClass);
                }
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                return query.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findEntitiesBySQL(final String sql, final Page page, final Class<T> mappedClass, final Object... parameters) {
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                BigInteger count = findUniqueBySQL(getTotalCountStatement(sql), parameters);
                SQLQuery query = session.createSQLQuery(sql);
                if (mappedClass != null) {
                    query.addEntity(mappedClass);
                }
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                page.setRowCount(count.intValue());
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getStartRowNumber());
                return query.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List findBySQL(final String sql, final Page page, final Object... parameters) {
        return (List) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();
                int rownum = scrollableResults.getRowNumber();
                page.setRowCount(rownum + 1);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getStartRowNumber());
                return query.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public int updateBySQL(final String sql, final Object... parameters) {
        return (Integer) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                return query.executeUpdate();
            }
        });
    }

    public int updateByHQL(final String hql, final Object... parameters) {
        return hibernateTemplate.bulkUpdate(hql, parameters);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(final Criteria criteria) {
        return (List<T>) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                return criteria.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(final com.wind.restapp.common.framework.db.Criteria<T> criteria, final Page page) {
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                if (page.hasOrder()) {
                    criteria.orderBy(page.getOrderField(), page.isAsc());
                }
                org.hibernate.Criteria countCriteria = criteria.createCriteria(session);
                countCriteria.setProjection(Projections.rowCount());
                long totalCount = (Long) countCriteria.uniqueResult();
                page.setRowCount((int) totalCount);
                org.hibernate.Criteria hibernateCriteria = criteria.createCriteria(session);
                hibernateCriteria.setFirstResult((page.getPageNumber() - 1) * page.getPageSize());
                hibernateCriteria.setMaxResults(page.getPageSize());
                return (List<T>) hibernateCriteria.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> T findUniqueByHQL(String hql, Object... parameters) {
        List list = findByHQL(hql, parameters);
        if (list != null && list.size() > 0)
            return (T) list.get(0);
        return null;
    }

    String getTotalCountStatement(String ql) {
        Matcher orderMatcher = ORDER_BY_PATTERN.matcher(ql.trim());
        if (orderMatcher.matches()) {
            if (ql.charAt(ql.length() - 1) == ')')
                ql = orderMatcher.group(1) + ")";
            else
                ql = orderMatcher.group(1);
        }
        Matcher matcher = SELECT_FROM_PATTERN.matcher(ql.trim());
        if (matcher.matches()) {
            return "select count(0) from (" + ql + ") temp";
        } else
            throw new DatabaseException("invalid SQL: " + ql);
    }

    public final void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
