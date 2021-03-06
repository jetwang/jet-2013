package com.wind.restapp.knight.dao;


import com.wind.restapp.common.framework.db.Criteria;
import com.wind.restapp.common.framework.db.DataAccess;
import com.wind.restapp.common.framework.db.Page;
import com.wind.restapp.common.framework.util.BeanUtils;
import com.wind.restapp.common.framework.util.StringUtils;
import com.wind.restapp.common.framework.util.UUIDUtils;
import com.wind.restapp.knight.domain.Knight;
import com.wind.restapp.knight.form.KnightForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jet
 */
@Component
public class KnightDao {
    private static final String SELECT_FROM_KNIGHT = "from Knight ";
    @Autowired
    protected DataAccess dataAccess;

    public List<Knight> findKnights(String keyword, Page page) {
        Criteria<Knight> criteria = Criteria.of(Knight.class);
        if (StringUtils.hasText(keyword)) {
            criteria.like("name", keyword);
        }
        return dataAccess.findByCriteria(criteria, page);
    }

    @Transactional
    public Knight updateOrSaveFromForm(KnightForm knightForm) {
        long number = knightForm.getNumber();
        if (number == 0) {
            knightForm.setKnightId(UUIDUtils.uuid());
            Knight knight = new Knight();
            BeanUtils.copyProperties(knightForm, knight);
            dataAccess.save(knight);
            return knight;
        } else {
            Knight knight = dataAccess.load(Knight.class, number);
            BeanUtils.copyProperties(knightForm, knight);
            dataAccess.update(knight);
            return knight;
        }
    }

    public Knight getKnightByNumber(long number) {
        return dataAccess.loadByUniqueProperty("number", number, Knight.class);
    }

    public Knight getOtherKnightWithSameName(String knightId, String name) {
        Criteria<Knight> criteria = Criteria.of(Knight.class);
        if (StringUtils.hasText(knightId)) {
            criteria.ne("knightId", knightId);
        }
        criteria.eq("name", name);
        return dataAccess.findFirstResultByCriteria(criteria);
    }

    public List<Knight> loadAll() {
        return dataAccess.findAll(Knight.class);
    }

    public Knight load(long number) {
        return dataAccess.load(Knight.class, number);
    }

    public void delete(String knightId) {
        dataAccess.updateByHQL("delete from Knight where knightId= ?", knightId);
    }
}