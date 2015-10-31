package com.example;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

import java.io.Serializable;

/**
 * Created by tada on 2015/10/31.
 */
@Entity(naming = NamingType.LOWER_CASE)
@Table(name = "test_entity")
public class TestEntity implements Serializable {
    @Id
    public Integer id;
    public String thrown;
    public String rollbackOn;
    public String dontRollbackOn;
    public String expected;

    public TestEntity() {
    }

    public TestEntity(Integer id, String thrown, String rollbackOn, String dontRollbackOn, String expected) {
        this.id = id;
        this.thrown = thrown;
        this.rollbackOn = rollbackOn;
        this.dontRollbackOn = dontRollbackOn;
        this.expected = expected;
    }
}