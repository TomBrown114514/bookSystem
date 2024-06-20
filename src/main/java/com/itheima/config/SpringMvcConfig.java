package com.itheima.config;

import com.itheima.interceptor.ResourcesInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Spring MVC的配置类，通过实现WebMvcConfigurer接口来定制Spring MVC的行为。
 */
@Configuration
@ComponentScan({"com.itheima.controller"})
@PropertySource("classpath:ingoreUrl.properties")
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

    /**
     * 配置JSP视图解析器。
     *
     * @param registry 视图解析器的注册表。
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/admin/", ".jsp");
    }

    /**
     * 添加拦截器到拦截器注册表中，用于拦截和处理特定的请求。
     *
     * @param registry 拦截器注册表，用于管理拦截器的配置。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resourcesInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/img/**");
    }
}

