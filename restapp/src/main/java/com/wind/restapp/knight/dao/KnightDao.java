package com.wind.restapp.knight.dao;


import jetwang.framework.db.Criteria;
import jetwang.framework.db.Page;
import jetwang.framework.db.Repository;
import jetwang.framework.util.BeanUtils;
import jetwang.framework.util.StringUtils;
import jetwang.framework.util.UUIDUtils;
import com.wind.restapp.knight.domain.Knight;
import com.wind.restapp.knight.form.KnightForm;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jet
 */
@Component
public class KnightDao extends Repository<Knight> {
    private static final String SELECT_FROM_KNIGHT = "from Knight ";

    public List<Knight> findKnights(String keyword, Page page) {
        Criteria<Knight> criteria = Criteria.of(Knight.class);
        if (StringUtils.hasText(keyword)) {
            criteria.like("name", keyword);
        }
        return dataAccess.findByCriteria(criteria, page);
    }

    @Transactional
    public void updateOrSaveFromForm(KnightForm knightForm) {
        long number = knightForm.getNumber();
        if (number == 0) {
            knightForm.setKnightId(UUIDUtils.uuid());
            saveFromForm(knightForm);
        } else {
            Knight object = load(number);
            BeanUtils.copyProperties(knightForm, object);
            update(object);
        }
    }

    public Knight getKnightByNumber(long number) {
        return loadByUniqueProperty("number", number);
    }

    public Knight getOtherKnightWithSameName(String knightId, String name) {
        Criteria<Knight> criteria = Criteria.of(Knight.class);
        if (StringUtils.hasText(knightId)) {
            criteria.ne("knightId", knightId);
        }
        criteria.eq("name", name);
        return dataAccess.findFirstResultByCriteria(criteria);
    }
}