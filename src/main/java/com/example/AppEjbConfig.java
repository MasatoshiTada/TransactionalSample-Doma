package com.example;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.PostgresDialect;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

/**
 * Created by tada on 2015/10/31.
 */
@Singleton
//@ApplicationScoped
public class AppEjbConfig implements Config {

    @Resource(lookup = "jdbc/sandbox")
    private DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Dialect getDialect() {
        return new PostgresDialect();
    }
}
