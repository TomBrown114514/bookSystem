package com.itheima.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

public class MyBatisConfig {
    @Bean
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("value", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(
            @Autowired DataSource dataSource,
            @Autowired PageInterceptor pageInterceptor
    ) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Interceptor[] plugins = {pageInterceptor};
        sqlSessionFactoryBean.setPlugins(plugins);
        return sqlSessionFactoryBean;
    }
}
