package com.wind.restapp.knight.dao;

import jetwang.framework.db.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = {"/spring.xml", "/spring-test-knight.xml"})
public class KnightDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private KnightDao knightDao;

    @Test
    public void testFindKnights() throws Exception {
        knightDao.findKnights("", new Page());
    }
}
