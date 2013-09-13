package jetwang.framework.db;

import jetwang.framework.util.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jet
 */
public class Criteria<T> {

    Class<T> clazz;
    List<Criterion> criterions = new LinkedList<Criterion>();
    List<Order> orders = new LinkedList<Order>();
    List<CritieriaAlias> alias = new ArrayList<CritieriaAlias>();
    Integer maxResult;
    Integer firstResult;
    boolean cacheable = false;

    public static <T> Criteria<T> of(Class<T> clazz) {
        return new Criteria<T>(clazz);
    }

    public static <T> Criteria<T> of(Class<T> clazz, boolean cacheable) {
        return new Criteria<T>(clazz, cacheable);
    }

    Criteria(Class<T> clazz) {
        this.clazz = clazz;
    }

    Criteria(Class<T> clazz, boolean cacheable) {
        this.clazz = clazz;
        this.cacheable = cacheable;
    }

    public Criteria<T> eq(String property, String value) {
        criterions.add(Restrictions.eq(property, value));
        return this;
    }

    public Criteria<T> ge(String string, Object value) {
        this.criterions.add(Restrictions.ge(string, value));
        return this;
    }

    public Criteria<T> gt(String string, Object value) {
        this.criterions.add(Restrictions.gt(string, value));
        return this;
    }

    //##

    public Criteria<T> le(String string, Object value) {
        this.criterions.add(Restrictions.le(string, value));
        return this;
    }

    public Criteria<T> lt(String string, Object value) {
        this.criterions.add(Restrictions.lt(string, value));
        return this;
    }

    public Criteria<T> sqlRestriction(String sql, Object value, Type type) {
        this.criterions.add(Restrictions.sqlRestriction(sql, value, type));
        return this;
    }
    //##

    public Criteria<T> isNull(String property) {
        criterions.add(Restrictions.isNull(property));
        return this;
    }

    public Criteria<T> isNotNull(String property) {
        criterions.add(Restrictions.isNotNull(property));
        return this;
    }

    public Criteria<T> alias(String associationPath, String alias) {
        CritieriaAlias criteriaAlias = new CritieriaAlias(associationPath, alias, org.hibernate.Criteria.INNER_JOIN);
        this.alias.add(criteriaAlias);
        return this;
    }

    public Criteria<T> alias(String associationPath, String alias, int joinType) {
        CritieriaAlias criteriaAlias = new CritieriaAlias(associationPath, alias, joinType);
        this.alias.add(criteriaAlias);
        return this;
    }

    public Criteria<T> al(String property) {
        criterions.add(Restrictions.isNull(property));
        return this;
    }

    public Criteria<T> eq(String property, Object value) {
        criterions.add(Restrictions.eq(property, value));
        return this;
    }

    public Criteria<T> ne(String property, String value) {
        criterions.add(Restrictions.ne(property, value));
        return this;
    }

    public Criteria<T> ge(String property, String value) {
        criterions.add(Restrictions.ge(property, value));
        return this;
    }

    public Criteria<T> ne(String property, Object value) {
        criterions.add(Restrictions.ne(property, value));
        return this;
    }

    public Criteria<T> in(String property, Object[] values) {
        criterions.add(Restrictions.in(property, values));
        return this;
    }

    public Criteria<T> notIn(String property, Object[] values) {
        criterions.add(Restrictions.not(Restrictions.in(property, values)));
        return this;
    }

    public Criteria<T> in(String property, Collection values) {
        criterions.add(Restrictions.in(property, values));
        return this;
    }

    public Criteria<T> like(String property, String value) {
        return this.like(property, value, MatchMode.ANYWHERE,true);
    }
    
    public Criteria<T> like(String property, String value,boolean caseSensitive) {
        return this.like(property, value, MatchMode.ANYWHERE,caseSensitive);
    }

    public Criteria<T> like(String property, String value, MatchMode matchMode) {
        return this.like(property,value,matchMode,true);
    }
    
    public Criteria<T> like(String property, String value, MatchMode matchMode, boolean caseSensitive) {
        if (StringUtils.hasText(value)) {
            if(caseSensitive){
                criterions.add(Restrictions.like(property, value, matchMode));
            }else{
                criterions.add(Restrictions.like(property, value, matchMode).ignoreCase());
            }
        }
        return this;
    }

    public Criteria<T> between(String property, Number minValue, Number maxValue) {
        criterions.add(Restrictions.between(property, minValue, maxValue));
        return this;
    }

    public Criteria<T> orderBy(String property, boolean ascending) {
        if (ascending) {
            orders.add(Order.asc(property));
        } else {
            orders.add(Order.desc(property));
        }
        return this;
    }

    public Criteria<T> fetch(int maxResult) {
        this.maxResult = maxResult;
        return this;
    }

    public Criteria<T> startFrom(int firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    org.hibernate.Criteria createCriteria(Session session) {
        org.hibernate.Criteria criteria = session.createCriteria(clazz);
        criteria.setCacheable(cacheable);
        for (CritieriaAlias critieriaAlias : alias) {
            criteria.createAlias(critieriaAlias.getAliasPath(), critieriaAlias.getAliasName(), critieriaAlias.getJoinType());
        }
        for (Criterion criterion : criterions) {
            criteria.add(criterion);
        }
        for (Order order : orders) {
            criteria.addOrder(order);
        }
        if (firstResult != null) {
            criteria.setFirstResult(firstResult);
        }
        if (maxResult != null) {
            criteria.setMaxResults(maxResult);
        }
        return criteria;
    }
}
