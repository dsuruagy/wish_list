package com.dsuruagy.wishlist.service;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * As suggested in https://stackoverflow.com/questions/27652689/spring-test-dbunit-warning
 */
@Configuration
public class DBUnitConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection() {
        DatabaseConfigBean bean = new DatabaseConfigBean();
        bean.setDatatypeFactory(new HsqldbDataTypeFactory());

        DatabaseDataSourceConnectionFactoryBean dbConnectionFactory = new DatabaseDataSourceConnectionFactoryBean(dataSource);
        dbConnectionFactory.setDatabaseConfig(bean);
        return dbConnectionFactory;
    }
}
