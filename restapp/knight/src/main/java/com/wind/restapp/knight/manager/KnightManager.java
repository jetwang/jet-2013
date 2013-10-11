package com.wind.restapp.knight.manager;


import com.wind.restapp.common.framework.util.StringUtils;
import com.wind.restapp.knight.domain.Knight;
import com.wind.restapp.knight.dao.KnightDao;
import com.wind.restapp.knight.form.KnightForm;
import com.wind.restapp.knight.form.KnightStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;

/**
 * @author Jet
 */
@Component
public class KnightManager {
    @Autowired
    KnightDao knightDao;

    public boolean validate(KnightForm knightForm, Errors errors) {
        if (StringUtils.isEmpty(knightForm.getName())) {
            errors.rejectValue("name", "please.enter.knight.name");
        } else {
            if (knightDao.getOtherKnightWithSameName(knightForm.getKnightId(), knightForm.getName()) != null) {
                errors.rejectValue("name", "please.enter.other.knight.name");
            }
        }
        if (StringUtils.isEmpty(knightForm.getKnightId())) {
            if (KnightStatus.Dead.getId().equals(knightForm.getStatusId())) {
                errors.rejectValue("status", "why.do.you.choose.dead");
            }
        }
        return !errors.hasErrors();
    }

    public List<Knight> allKnights() {
        return knightDao.loadAll();
    }

    @Transactional
    public void saveOrUpdate(KnightForm knightForm, Errors errors) {
        if (validate(knightForm, errors)) {
            knightDao.updateOrSaveFromForm(knightForm);
        }
    }
}