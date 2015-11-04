package com.example.jdbc;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tada
 */
@Dependent
public class JdbcCdiRequiresNewTestDao implements Serializable {

    @Resource(lookup = "jdbc/sandbox")
    private DataSource dataSource;

    public List<TestEntity> findAll() {
        List<TestEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM test_entity ORDER BY id";
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TestEntity testEntity = new TestEntity();
                testEntity.setId(rs.getInt("id"));
                testEntity.setThrown(rs.getString("thrown"));
                testEntity.setRollbackOn(rs.getString("rollbackon"));
                testEntity.setDontRollbackOn(rs.getString("dontrollbackon"));
                testEntity.setExpected(rs.getString("expected"));
                list.add(testEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public int deleteAll() throws Exception {
        String sql = "DELETE FROM test_entity";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int rows = ps.executeUpdate();
            return rows;
        }
    }

    private Connection getNonAutoCommitConnection() throws Exception {
        Connection con = dataSource.getConnection();
        con.setAutoCommit(false);
        return con;
    }

    private int insert(TestEntity testEntity) throws Exception {
        String sql = "INSERT INTO test_entity(id, thrown, rollbackon, dontrollbackon, expected) VALUES(?, ?, ?, ?, ?)";
        try (Connection con = this.getNonAutoCommitConnection();
               PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, testEntity.getId());
            ps.setString(2, testEntity.getThrown());
            ps.setString(3, testEntity.getRollbackOn());
            ps.setString(4, testEntity.getDontRollbackOn());
            ps.setString(5, testEntity.getExpected());
            int rows = ps.executeUpdate();
            return rows;
        }
    }


    /** ************************************************************************
     * 01. rollbackOn={} dontRollbackOn={}
     * @throws NullPointerException
     * *************************************************************************
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW,
            rollbackOn = {},
            dontRollbackOn = {})
    public void insert01RollbackOnNoDontRollbackonNoThrowsNPE(TestEntity testEntity) throws Exception {
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
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
        this.insert(testEntity);
        throw new IOException();
    }
}