package com.itheima.interceptor;

import com.itheima.domain.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ResourcesInterceptor extends HandlerInterceptorAdapter {
    private List<String> ignoreUrl;
    public ResourcesInterceptor(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getAttribute("USER_SESSION");
        String url = request.getRequestURI();
        if (user != null) {
            return true;
        }
        if (url.contains("login")) {
            return true;
        }
        request.setAttribute("msg", "您还没有登录，请先登录");
        request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        return false;
    }
}
