package com.example.jpa;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author tada
 */
@Dependent
public class JpaCdiRequiredTestDao implements Serializable {
    @PersistenceContext(unitName = "sandboxPU")
    private EntityManager em;

    public List<TestEntity> findAll() {
        return em.createQuery("SELECT t FROM TestEntity t ORDER BY t.id", TestEntity.class)
                .getResultList();
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public void deleteAll() {
        em.createQuery("DELETE FROM TestEntity t").executeUpdate();
    }

    /** ************************************************************************
     * 01. rollbackOn={} dontRollbackOn={}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {},
            dontRollbackOn = {})
    public void insert01RollbackOnNoDontRollbackonNoThrowsNPE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 02. rollbackOn={} dontRollbackOn={}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {},
            dontRollbackOn = {})
    public void insert02RollbackOnNoDontRollbackonNoThrowsIOE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new IOException();
    }

    /** ************************************************************************
     * 03. rollbackOn={} dontRollbackOn={NPE}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {},
            dontRollbackOn = {NullPointerException.class})
    public void insert03RollbackOnNoDontRollbackonNPEThrowsNPE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 04. rollbackOn={} dontRollbackOn={NPE}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {},
            dontRollbackOn = {NullPointerException.class})
    public void insert04RollbackOnNoDontRollbackonNPEThrowsIOE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new IOException();
    }

    /** ************************************************************************
     * 05. rollbackOn={} dontRollbackOn={IOE}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {},
            dontRollbackOn = {IOException.class})
    public void insert05RollbackOnNoDontRollbackonIOEThrowsNPE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 06. rollbackOn={} dontRollbackOn={IOE}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {},
            dontRollbackOn = {IOException.class})
    public void insert06RollbackOnNoDontRollbackonIOEThrowsIOE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new IOException();
    }

    /** ************************************************************************
     * 07. rollbackOn={NPE} dontRollbackOn={}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {NullPointerException.class},
            dontRollbackOn = {})
    public void insert07RollbackOnNPEDontRollbackonNoThrowsNPE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 08. rollbackOn={NPE} dontRollbackOn={}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {NullPointerException.class},
            dontRollbackOn = {})
    public void insert08RollbackOnNPEDontRollbackonNoThrowsIOE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new IOException();
    }

    /** ************************************************************************
     * 09. rollbackOn={IOE} dontRollbackOn={}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {IOException.class},
            dontRollbackOn = {})
    public void insert09RollbackOnIOEDontRollbackonNoThrowsNPE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 10. rollbackOn={NPE} dontRollbackOn={}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {IOException.class},
            dontRollbackOn = {})
    public void insert10RollbackOnIOEDontRollbackonNoThrowsIOE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new IOException();
    }

    /** ************************************************************************
     * 11. rollbackOn={NPE} dontRollbackOn={IOE}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {NullPointerException.class},
            dontRollbackOn = {IOException.class})
    public void insert11RollbackOnNPEDontRollbackonIOEThrowsNPE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 12. rollbackOn={NPE} dontRollbackOn={IOE}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {NullPointerException.class},
            dontRollbackOn = {IOException.class})
    public void insert12RollbackOnNPEDontRollbackonIOEThrowsIOE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new IOException();
    }

    /** ************************************************************************
     * 13. rollbackOn={IOE} dontRollbackOn={NPE}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {IOException.class},
            dontRollbackOn = {NullPointerException.class})
    public void insert13RollbackOnIOEDontRollbackonNPEThrowsNPE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new NullPointerException();
    }

    /** ************************************************************************
     * 14. rollbackOn={IOE} dontRollbackOn={NPE}
     * @throws IOException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRED,
            rollbackOn = {IOException.class},
            dontRollbackOn = {NullPointerException.class})
    public void insert14RollbackOnIOEDontRollbackonNPEThrowsIOE(TestEntity testEntity) throws Exception {
        em.persist(testEntity);
        em.flush();
        throw new IOException();
    }
}