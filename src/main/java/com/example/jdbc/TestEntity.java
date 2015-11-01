package com.example.jdbc;


import java.io.Serializable;

/**
 * Created by tada on 2015/10/31.
 */
public class TestEntity implements Serializable {
    private Integer id;
    private String thrown;
    private String rollbackOn;
    private String dontRollbackOn;
    private String expected;

    public TestEntity() {
    }

    public TestEntity(Integer id, String thrown, String rollbackOn, String dontRollbackOn, String expected) {
        this.setId(id);
        this.setThrown(thrown);
        this.setRollbackOn(rollbackOn);
        this.setDontRollbackOn(dontRollbackOn);
        this.setExpected(expected);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThrown() {
        return thrown;
    }

    public void setThrown(String thrown) {
        this.thrown = thrown;
    }

    public String getRollbackOn() {
        return rollbackOn;
    }

    public void setRollbackOn(String rollbackOn) {
        this.rollbackOn = rollbackOn;
    }

    public String getDontRollbackOn() {
        return dontRollbackOn;
    }

    public void setDontRollbackOn(String dontRollbackOn) {
        this.dontRollbackOn = dontRollbackOn;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }
}