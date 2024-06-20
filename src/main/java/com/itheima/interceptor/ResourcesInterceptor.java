package com.itheima.interceptor;

import com.itheima.domain.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资源拦截器，用于拦截对特定资源的访问请求。
 * 通过检查用户会话是否存在来决定是否允许访问，如果用户未登录，则重定向到登录页面。
 */
public class ResourcesInterceptor extends HandlerInterceptorAdapter {
    // 忽略拦截的URL列表
    private List<String> ignoreUrl;

    /**
     * 构造函数，初始化忽略拦截的URL列表。
     *
     * @param ignoreUrl 忽略拦截的URL列表
     */
    public ResourcesInterceptor(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    /**
     * 在请求处理之前进行预处理。
     * 主要用于检查用户是否已登录，未登录则重定向到登录页面。
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @param handler  将要处理的处理器对象
     * @return 如果允许继续处理返回true，否则返回false
     * @throws Exception 如果在处理过程中发生异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求中获取用户会话
        User user = (User) request.getAttribute("USER_SESSION");
        // 获取请求的URL
        String url = request.getRequestURI();

        // 如果用户会话存在，允许访问
        if (user != null) {
            return true;
        }
        // 如果请求的URL包含"login"，允许访问
        if (url.contains("login")) {
            return true;
        }
        // 如果用户会话不存在且请求的不是登录页面，则设置提示信息并重定向到登录页面
        request.setAttribute("msg", "您还没有登录，请先登录");
        request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        return false;
    }

    public List<String> getIgnoreUrl() {
        return ignoreUrl;
    }
}

