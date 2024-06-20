package com.itheima.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import javax.sql.DataSource;

/**
 * JDBC配置类，用于配置数据源。
 * 使用@PropertySource注解引入jdbc.properties文件，通过@Value注解注入配置文件中的属性值。
 */
@PropertySource("classpath:jdbc.properties")
public class JdbcConfig {
    /**
     * 数据库驱动类名，从jdbc.properties文件中读取。
     */
    @Value("${jdbc.driverClassName}")
    private String driver;
    /**
     * 数据库连接URL，从jdbc.properties文件中读取。
     */
    @Value("${jdbc.url}")
    private String url;
    /**
     * 数据库用户名，从jdbc.properties文件中读取。
     */
    @Value("${jdbc.username}")
    private String userName;
    /**
     * 数据库密码，从jdbc.properties文件中读取。
     */
    @Value("${jdbc.password}")
    private String password;

    /**
     * 创建并配置Druid数据源。
     * 使用@Bean注解将该方法返回的DataSource对象注册为Spring Bean，命名为dataSource。
     *
     * @return 配置好的DruidDataSource实例。
     */
    @Bean("dataSource")
    public DataSource getDataSource(){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(userName);
        ds.setPassword(password);
        return ds;
    }
}


