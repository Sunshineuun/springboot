package com.qiushengming.configuration.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * Mybatis 事务管理配置
 * @author MinMin
 */
//@Configuration
//@EnableTransactionManagement
public class MybatisTransactionConfig
    implements TransactionManagementConfigurer {

    private final DataSource dataSource;


    @Autowired
    public MybatisTransactionConfig(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
