package com.example.doma;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 * Created by tada on 2015/10/31.
 */
@Dependent
public class TestCdiRequiresNewService {
    @Inject
    private TestCdiDao testCdiDao;

    public List<TestEntity> selectAll() {
        return testCdiDao.selectAll();
    }

//    @Transactional(Transactional.TxType.REQUIRES_NEW)
//    public void deleteAll() {
//        testCdiDao.batchDelete();
//    }

    /** ************************************************************************
     * 01. rollbackOn={} dontRollbackOn={}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {},
            dontRollbackOn = {})
    public void insert01RollbackOnNoDontRollbackonNoThrowsNPE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 02. rollbackOn={} dontRollbackOn={}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {},
            dontRollbackOn = {})
    public void insert02RollbackOnNoDontRollbackonNoThrowsIOE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new IOException();
    }

    /** ************************************************************************
     * 03. rollbackOn={} dontRollbackOn={NPE}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {},
            dontRollbackOn = {NullPointerException.class})
    public void insert03RollbackOnNoDontRollbackonNPEThrowsNPE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 04. rollbackOn={} dontRollbackOn={NPE}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {},
            dontRollbackOn = {NullPointerException.class})
    public void insert04RollbackOnNoDontRollbackonNPEThrowsIOE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new IOException();
    }

    /** ************************************************************************
     * 05. rollbackOn={} dontRollbackOn={IOE}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {},
            dontRollbackOn = {IOException.class})
    public void insert05RollbackOnNoDontRollbackonIOEThrowsNPE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 06. rollbackOn={} dontRollbackOn={IOE}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {},
            dontRollbackOn = {IOException.class})
    public void insert06RollbackOnNoDontRollbackonIOEThrowsIOE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new IOException();
    }

    /** ************************************************************************
     * 07. rollbackOn={NPE} dontRollbackOn={}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {NullPointerException.class},
            dontRollbackOn = {})
    public void insert07RollbackOnNPEDontRollbackonNoThrowsNPE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 08. rollbackOn={NPE} dontRollbackOn={}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {NullPointerException.class},
            dontRollbackOn = {})
    public void insert08RollbackOnNPEDontRollbackonNoThrowsIOE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new IOException();
    }

    /** ************************************************************************
     * 09. rollbackOn={IOE} dontRollbackOn={}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {IOException.class},
            dontRollbackOn = {})
    public void insert09RollbackOnIOEDontRollbackonNoThrowsNPE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 10. rollbackOn={NPE} dontRollbackOn={}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {IOException.class},
            dontRollbackOn = {})
    public void insert10RollbackOnIOEDontRollbackonNoThrowsIOE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new IOException();
    }

    /** ************************************************************************
     * 11. rollbackOn={NPE} dontRollbackOn={IOE}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {NullPointerException.class},
            dontRollbackOn = {IOException.class})
    public void insert11RollbackOnNPEDontRollbackonIOEThrowsNPE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 12. rollbackOn={NPE} dontRollbackOn={IOE}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {NullPointerException.class},
            dontRollbackOn = {IOException.class})
    public void insert12RollbackOnNPEDontRollbackonIOEThrowsIOE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new IOException();
    }

    /** ************************************************************************
     * 13. rollbackOn={IOE} dontRollbackOn={NPE}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {IOException.class},
            dontRollbackOn = {NullPointerException.class})
    public void insert13RollbackOnIOEDontRollbackonNPEThrowsNPE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 14. rollbackOn={IOE} dontRollbackOn={NPE}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {IOException.class},
            dontRollbackOn = {NullPointerException.class})
    public void insert14RollbackOnIOEDontRollbackonNPEThrowsIOE(TestEntity testEntity) throws Exception {
        testCdiDao.insert(testEntity);
        throw new IOException();
    }
}