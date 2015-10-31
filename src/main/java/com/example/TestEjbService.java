package com.example;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * Created by tada on 2015/10/31.
 */
@Stateless
public class TestEjbService {
    @Inject
    private TestEjbDao testEjbDao;

    public List<TestEntity> selectAll() {
        return testEjbDao.selectAll();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertRequiresNewThrowNPE(TestEntity testEntity) throws Exception {
        testEjbDao.insert(testEntity);
        throw new NullPointerException();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertRequiresNewThrowIOE(TestEntity testEntity) throws Exception {
        testEjbDao.insert(testEntity);
        throw new IOException();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void insertRequiredThrowNPE(TestEntity testEntity) throws Exception {
        testEjbDao.insert(testEntity);
        throw new NullPointerException();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void insertRequiredThrowIOE(TestEntity testEntity) throws Exception {
        testEjbDao.insert(testEntity);
        throw new IOException();
    }

}
