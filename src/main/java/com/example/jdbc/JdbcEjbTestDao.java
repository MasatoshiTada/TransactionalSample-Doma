package com.example.jdbc;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
@Stateless
public class JdbcEjbTestDao implements Serializable {

//    @Resource(lookup = "java:/jdbc/sandbox") // WildFly用
    @Resource(lookup = "jdbc/sandbox") // Payara用
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

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteAll() throws Exception {
        String sql = "DELETE FROM test_entity";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int rows = ps.executeUpdate();
            return rows;
        }
    }

    private int insert(TestEntity testEntity) throws Exception {
        String sql = "INSERT INTO test_entity(id, thrown, rollbackon, dontrollbackon, expected) VALUES(?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
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
     * @throws NullPointerException
     * *************************************************************************
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void insertRequiredThrowsNPE(TestEntity testEntity) throws Exception {
        this.insert(testEntity);
        throw new NullPointerException();
    }

    /** ************************************************************************
     * @throws IOException
     * *************************************************************************
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void insertRequiredThrowsIOE(TestEntity testEntity) throws Exception {
        this.insert(testEntity);
        throw new IOException();
    }

    /** ************************************************************************
     * @throws NullPointerException
     * *************************************************************************
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertRequiresNewThrowsNPE(TestEntity testEntity) throws Exception {
        this.insert(testEntity);
        throw new NullPointerException();
    }

    /** ************************************************************************
     * @throws IOException
     * *************************************************************************
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void insertRequiresNewThrowsIOE(TestEntity testEntity) throws Exception {
        this.insert(testEntity);
        throw new IOException();
    }

}