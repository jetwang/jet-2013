package com.wind.restapp.knight.manager;

import com.wind.restapp.knight.dao.KnightDao;
import com.wind.restapp.knight.domain.Knight;
import com.wind.restapp.knight.form.KnightForm;
import com.wind.restapp.knight.form.KnightStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.*;

public class KnightManagerTest {
    @Test
    public void testSaveOrUpdateWhenValidatePassed() throws Exception {
        KnightManager knightManager = new KnightManager() {
            @Override
            public boolean validate(KnightForm knightForm, Errors errors) {
                return true;
            }
        };
        KnightDao knightDao = mock(KnightDao.class);
        knightManager.knightDao = knightDao;
        KnightForm knightForm = new KnightForm();
        Errors errors = mock(Errors.class);
        knightManager.saveOrUpdate(knightForm, errors);
        verify(knightDao).updateOrSaveFromForm(knightForm);
    }

    @Test
    public void testSaveOrUpdateWhenValidateFail() throws Exception {
        KnightManager knightManager = new KnightManager() {
            @Override
            public boolean validate(KnightForm knightForm, Errors errors) {
                return false;
            }
        };
        KnightDao knightDao = mock(KnightDao.class);
        knightManager.knightDao = knightDao;
        KnightForm knightForm = new KnightForm();
        Errors errors = mock(Errors.class);
        knightManager.saveOrUpdate(knightForm, errors);
        verifyNoMoreInteractions(knightDao);
    }

    @Test
    public void testValidate() throws Exception {
        KnightManager knightManager = new KnightManager();
        KnightDao knightDao = mock(KnightDao.class);
        knightManager.knightDao = knightDao;
        //test when name is empty
        KnightForm knightForm = new KnightForm();
        knightForm.setKnightId("knightId");
        knightForm.setStatusId(KnightStatus.Dead.getId());
        Errors errors = new BeanPropertyBindingResult(knightForm, "form");
        Assert.assertFalse(knightManager.validate(knightForm, errors));
        verifyNoMoreInteractions(knightDao);
        reset(knightDao);


        //test when name isn't empty but occupy
        when(knightDao.getOtherKnightWithSameName("knightId", "name")).thenReturn(new Knight());
        knightForm = new KnightForm();
        knightForm.setKnightId("knightId");
        knightForm.setName("name");
        knightForm.setStatusId(KnightStatus.Dead.getId());
        errors = new BeanPropertyBindingResult(knightForm, "form");
        Assert.assertFalse(knightManager.validate(knightForm, errors));
        verify(knightDao).getOtherKnightWithSameName("knightId", "name");
        verifyNoMoreInteractions(knightDao);
        reset(knightDao);

        //test when name isn't empty or occupied
        when(knightDao.getOtherKnightWithSameName("knightId", "name not occupied")).thenReturn(null);
        knightForm = new KnightForm();
        knightForm.setKnightId("knightId");
        knightForm.setName("name not occupied");
        knightForm.setStatusId(KnightStatus.Dead.getId());
        errors = new BeanPropertyBindingResult(knightForm, "form");
        Assert.assertTrue(knightManager.validate(knightForm, errors));
        verify(knightDao).getOtherKnightWithSameName("knightId", "name not occupied");
        verifyNoMoreInteractions(knightDao);
        reset(knightDao);
    }
}
