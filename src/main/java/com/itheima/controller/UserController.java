package com.itheima.controller;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        try {
            User u = userService.login(user);
            if (u != null) {
                request.getSession().setAttribute("USER_SESSION", u);
                return "redirect:/admin/main.jsp";
            }
            request.setAttribute("msg", "用户名或密码错误");
            return "forward:/admin/login.jsp";
        } catch (Exception e) {
            e.printStackTrace(System.err);
            request.setAttribute("msg", "系统错误");
            return "forward:/admin/login.jsp";
        }
    }

    @RequestMapping("/toMainPage")
    public String toMainPage() {
        return "main";
    }
}
