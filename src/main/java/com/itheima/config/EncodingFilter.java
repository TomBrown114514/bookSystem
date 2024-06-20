package com.itheima.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 字符编码过滤器，用于统一设置请求和响应的字符编码为UTF-8。
 * 通过@WebFilter注解将该过滤器绑定到所有的URL上，确保字符编码的一致性，避免乱码问题。
 */
@WebFilter(filterName = "encodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {
    /**
     * 过滤器初始化方法，该方法在过滤器实例化后立即调用。
     *
     * @param filterConfig 过滤器配置对象，提供初始化过滤器所需的信息。
     * @throws ServletException 如果初始化过程中出现异常。
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 该方法留空，不进行任何操作。
    }

    /**
     * 过滤器的核心方法，用于处理请求和响应的字符编码设置。
     * @param servletRequest 请求对象，用于读取客户端发送的请求。
     * @param servletResponse 响应对象，用于向客户端发送响应。
     * @param filterChain 过滤器链对象，用于将请求传递给下一个过滤器或目标 servlet。
     * @throws IOException 如果处理请求或响应时发生I/O错误。
     * @throws ServletException 如果处理请求或响应时发生servlet相关异常。
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 设置请求的字符编码为UTF-8
        servletRequest.setCharacterEncoding("UTF-8");
        // 设置响应的字符编码为UTF-8
        servletResponse.setCharacterEncoding("UTF-8");
        // 继续处理请求，将请求传递给下一个过滤器或目标servlet
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 过滤器销毁方法，该方法在过滤器实例被销毁前调用。
     * 可用于释放过滤器持有的资源。
     */
    @Override
    public void destroy() {
        // 该方法留空，不进行任何操作。
    }
}

