package com.itheima.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis的配置类，用于配置MyBatis的相关组件。
 */
public class MyBatisConfig {

    /**
     * 配置PageInterceptor，用于实现分页功能。
     *
     * @return 返回配置好的PageInterceptor实例。
     */
    @Bean
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("value", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    /**
     * 配置SqlSessionFactoryBean，用于创建SqlSessionFactory。
     *
     * @param dataSource      数据源，用于连接数据库。
     * @param pageInterceptor 分页插件，用于实现分页功能。
     * @return 返回配置好的SqlSessionFactoryBean实例。
     */
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

    /**
     * 配置MapperScannerConfigurer，用于扫描Mapper接口。
     *
     * @return 返回配置好的MapperScannerConfigurer实例。
     */
    public MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.itheima.mapper");
        return mapperScannerConfigurer;
    }
}

