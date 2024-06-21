package com.itheima.config;

import com.itheima.interceptor.ResourcesInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * Spring MVC的配置类，通过实现WebMvcConfigurer接口来定制Spring MVC的行为。
 */
@Configuration
@PropertySource("classpath:ingoreUrl.properties")
//等同于<context:component-scan base-package="com.itheima.controller"/>
@ComponentScan({"com.itheima.controller"})
/*@Import({MyWebMvcConfig.class})*/
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {
    /**
     * 从属性文件中读取需要忽略的URL，用于配置ResourcesInterceptor。
     */
    @Value("#{'${ignoreUrl}'.split(',')}")
    private List<String> ingoreUrl;

    /**
     * 定义一个资源拦截器bean。
     *
     * @return ResourcesInterceptor实例，用于拦截和处理特定的资源请求。
     */
    @Bean
    public ResourcesInterceptor resourcesInterceptor() {
        return new ResourcesInterceptor(ingoreUrl);
    }

    /**
     * 配置默认的Servlet处理，使Spring MVC能够处理静态资源。
     *
     * @param configurer 用于配置默认Servlet处理的接口。
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/admin/", "jsp");
    }
}

