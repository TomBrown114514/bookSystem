package com.itheima.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Spring配置类，用于定义和配置应用程序的组件和行为。
 *
 * @Configuration 表明该类是一个配置类，用于替代传统的XML配置。
 * @Import 引入其他配置类，如MyBatisConfig和JdbcConfig，以整合多个配置。
 * @ComponentScan 使用指定的包名自动扫描并注册组件。
 * @EnableTransactionManagement 启用Spring的事务管理功能。
 */
@Configuration
@Import({MyBatisConfig.class, JdbcConfig.class})
@ComponentScan("com.itheima.service")
@EnableTransactionManagement
public class SpringConfig {

    /**
     * 定义一个DataSourceTransactionManager bean，用于管理数据库事务。
     *
     * @return 返回一个配置好的DataSourceTransactionManager实例。
     * @Autowired 自动注入DataSource，该DataSource通常由其他配置（如JdbcConfig）定义。
     */
    @Bean("transactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(
            @Autowired DataSource dataSource
    ) {
        DataSourceTransactionManager dataSourceTransactionManager =
                new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}

